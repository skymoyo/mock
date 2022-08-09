package work.skymoyo.mock.client.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import work.skymoyo.mock.common.spi.Spi;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Spi
@Slf4j
public class ProxyScanAgent implements Agent {

    private static final String SCAN_PATH = "classpath:MockAgent";


    private static List<String> DEF = new ArrayList<>();

    @Override
    public void proxy(ClassPool pool) {

        File file;
        try {
            file = ResourceUtils.getFile(SCAN_PATH);
        } catch (FileNotFoundException e) {
            log.warn("{}:文件不存在,忽略执行", SCAN_PATH);
            return;
        }

        if (!file.exists()) {
            return;
        }

        try (InputStream inputStream = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(inputStream, UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            // 根据类分组
            br.lines()
                    .filter(StringUtils::hasLength)
                    .map(String::trim)
                    .filter(c -> !c.startsWith("#"))
                    .collect(Collectors.groupingBy(s -> s.split("#")[0]))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                        String key = e.getKey();
                        List<String> values = e.getValue();

                        List<String> methodKeys = values.stream()
                                .map(m -> m.replace(key, "")
                                        .replace("#", ""))
                                .filter(StringUtils::hasLength)
                                .collect(Collectors.toList());

                        if (values.size() == methodKeys.size()) {
                            return methodKeys;
                        }

                        //表示具有 未设置方法的类
                        return DEF;
                    }))
                    .forEach((className, methodList) -> {
                        log.debug("处理class:{}", className);
                        try {
                            CtClass ctClass = pool.get(className);

                            if (methodList.isEmpty()) {
                                Arrays.stream(ctClass.getDeclaredMethods())
                                        .forEach(method -> {
                                            this.createMethod(ctClass, className, method.getName());
                                        });
                            } else {
                                methodList.forEach(methodName -> {
                                    this.createMethod(ctClass, className, methodName);
                                });
                            }

                            ctClass.toClass();

                        } catch (Exception e) {
                            log.warn("处理class:{} 异常:{}", className, e.getMessage(), e);
                        }
                    });
        } catch (Exception e) {
            log.warn("自定义代理异常:{}", e.getMessage(), e);
        }


    }

    private void createMethod(CtClass ctClass, String clazzName, String methodName) {
        String methodKey = clazzName + "#" + methodName;
        log.debug("处理方法:{}", methodKey);
        try {
            CtMethod method = ctClass.getDeclaredMethod(methodName);

            method.insertBefore("try{"
                    + "if(work.skymoyo.mock.client.utils.MockContextUtil.isEnableMock()){"
                    + "     return work.skymoyo.mock.client.utils.MethodMockUtil.proxyInvoker(\"" + methodKey + "\", $type, $args);"
                    + "  }"
                    + "} catch (Throwable ignored){"
                    + "}");
        } catch (Exception e) {
            log.warn("处理方法:{} 异常:{}", methodKey, e.getMessage(), e);
        }
    }


}
