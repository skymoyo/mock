package work.skymoyo.mock.client.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import work.skymoyo.mock.common.spi.Spi;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Spi
@Slf4j
public class ProxyScanAgent implements Agent {

    private static String SCAN_PATH;

    static {
        SCAN_PATH = Optional.ofNullable(System.getProperty("mock.proxy.file"))
                .filter(StringUtils::hasLength)
                .orElse("classpath:MockAgent");
    }

    private static List<String> DEF = new ArrayList<>(0);

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
                    .distinct()
                    .collect(Collectors.groupingBy(s -> s.split("#")[0]))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                        String key = e.getKey();
                        List<String> values = e.getValue();

                        List<String> methodKeys = values.stream()
                                .map(m -> m.replace(key + "#", ""))
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
                                            this.createMethod(className, method);
                                        });
                            } else {
                                methodList.forEach(methodName -> {
                                    try {
                                        CtMethod[] methodArrays = ctClass.getDeclaredMethods(methodName);
                                        for (CtMethod method : methodArrays) {
                                            this.createMethod(className, method);
                                        }
                                    } catch (Exception e) {
                                        log.warn("处理方法:{} 异常:{}", className + "#" + methodName, e.getMessage(), e);
                                    }
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

    private void createMethod(String clazzName, CtMethod method) {
        String methodKey = clazzName + "#" + method.getName();
        try {
            log.debug("处理方法:{},参数列表：{}", methodKey, Arrays.stream(method.getParameterTypes()).map(CtClass::getName).toArray());

            String returnType = method.getReturnType().getName();

            method.insertBefore("try{"
                    + "if(work.skymoyo.mock.client.utils.MockContextUtil.isEnableMock()){"
                    + " java.lang.reflect.Type type = work.skymoyo.mock.client.utils.BeanMockUtil.getReturnType(work.skymoyo.mock.client.utils.BeanMockUtil.getMethod($class,\"" + method.getName() + "\",$sig));"
//                    + " java.lang.reflect.Type type = $class.getMethod(\"" + method.getName() + "\",$sig).getGenericReturnType();"
                    + " Object obj  =  work.skymoyo.mock.client.utils.MethodMockUtil.proxyInvoker(\"" + methodKey + "\", type, $args);"
                    + " return (" + returnType + ") obj;"
                    + "  }"
                    + "} catch (Throwable ignored){"
                    + "}");

        } catch (Exception e) {
            log.warn("处理方法:{} 异常:{}", methodKey, e.getMessage(), e);
        }
    }


}
