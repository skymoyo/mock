package work.skymoyo.mock.client.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import work.skymoyo.mock.common.spi.Spi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Spi
@Slf4j
public class ProxyScanAgent implements Agent {

    private static String scanPath = "classpath:MockAgent";


    private static List<String> DEF = new ArrayList<>();

    @Override
    public void proxy(ClassPool pool) {

        try (InputStream inputStream = new FileInputStream(ResourceUtils.getFile(scanPath));
             InputStreamReader isr = new InputStreamReader(inputStream, UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            // 根据类分组
            br.lines()
                    .filter(c -> !c.startsWith("#"))
                    .filter(c -> !StringUtils.isEmpty(c))
                    .filter(c -> c.trim().length() > 0)
                    .collect(Collectors.groupingBy(s -> s.split("#")[0]))
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                        String key = e.getKey();
                        List<String> values = e.getValue();

                        List<String> methodKeys = values.stream()
                                .map(m -> m.replace(key, "")
                                        .replace("#", ""))
                                .filter(m -> !StringUtils.isEmpty(m))
                                .collect(Collectors.toList());

                        if (values.size() == methodKeys.size()) {
                            return methodKeys;
                        }

                        //表示具有 未设置方法的类
                        return DEF;
                    }))
                    .forEach((k, v) -> {
                        log.debug("处理class:{}", k);
                        try {
                            CtClass ctClass = pool.get(k);
                            if (v.isEmpty()) {
                                Arrays.stream(ctClass.getDeclaredMethods())
                                        .forEach(method -> {
                                            String methodKey = k + "#" + method.getName();
                                            log.debug("处理 {}", methodKey);
                                            try {
                                                method.setBody("{"
                                                        + "return work.skymoyo.mock.client.utils.MethodMockUtil.ProxyInvoker(\"" + methodKey + "\", $type, $args);"
                                                        + "}");
                                            } catch (Exception ignored) {
                                            }
                                        });

                            } else {
                                v.forEach(m -> {
                                    String methodKey = k + "#" + m;
                                    log.debug("处理 {}", methodKey);
                                    try {
                                        CtMethod method = ctClass.getDeclaredMethod(m);
                                        method.setBody("{"
                                                + "return work.skymoyo.mock.client.utils.MethodMockUtil.ProxyInvoker(\"" + methodKey + "\", $type, $args);"
                                                + "}");
                                    } catch (Exception ignored) {
                                    }
                                });

                            }


                            ctClass.toClass();

                        } catch (Exception ignored) {

                        }
                    });
        } catch (Exception e) {
            log.warn("自定义代理异常:{}", e.getMessage(), e);
        }


    }


}
