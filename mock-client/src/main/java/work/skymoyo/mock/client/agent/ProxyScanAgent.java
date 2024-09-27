package work.skymoyo.mock.client.agent;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import work.skymoyo.mock.common.spi.Spi;

import java.io.*;
import java.lang.reflect.Field;
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
                    .collect(Collectors.groupingBy(s -> s.split("#")[0], LinkedHashMap::new, Collectors.toList()))
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
                    }, (o, n) -> n, LinkedHashMap::new))
                    .entrySet()
                    .stream()
                    .map(entry -> {
                        String className = entry.getKey();
                        List<String> methodList = entry.getValue();
                        try {
                            log.debug("处理class:{}", className);

                            CtClass ctClass = pool.get(className);
                            if (ctClass.isFrozen()) {
                                ctClass.defrost();
                            }

                            if (ctClass.isInterface()) {
                                boolean anyMatch = Arrays.stream(ctClass.getAnnotations())
                                        .anyMatch(a -> Objects.equals(a.toString(), "@org.springframework.stereotype.Repository"));
                                if (anyMatch) {
                                    this.createAnnotationMethodProxy(className, methodList, ctClass);
                                } else {
                                    this.createServiceMethodProxy(pool, className, methodList, ctClass);
                                }
                                return Collections.singletonList(ctClass);
                            } else {
                                this.createMethodProxy(className, methodList, ctClass);
                                List<CtClass> classList = Arrays.stream(ctClass.getInterfaces()).collect(Collectors.toList());
                                classList.add(ctClass);
                                return classList;
                            }
                        } catch (Exception e) {
                            log.warn("处理class:{} 异常:{}", className, e.getMessage(), e);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .distinct()
                    .forEach(c -> {
                        try {
                            c.writeFile("D:\\agentClass");
                            c.toClass();
                        } catch (Exception e) {
                            log.warn("加载class:{} 异常:{}", c, e.getMessage(), e);
                        }
                    });
        } catch (Exception e) {
            log.warn("自定义代理异常:{}", e.getMessage(), e);
        }
    }


    /**
     * 生成实现
     *
     * @param className
     * @param methodList
     * @param ctClass
     */
    private void createServiceMethodProxy(ClassPool pool, String className, List<String> methodList, CtClass ctClass) {

        this.createAnnotationMethodProxy(className, methodList, ctClass);

        boolean needCreateImpl = true;
        try {

            Class poolClass = pool.getClass();
            Field classes = poolClass.getDeclaredField("classes");
            classes.setAccessible(true);
            Hashtable hashtable = (Hashtable) classes.get(pool);
            Set set = hashtable.keySet();
            for (Object next : set) {
                boolean assignableFrom = Objects.equals(next.getClass().getName(), className);
//                System.out.println(next + ":" + assignableFrom);
                if (assignableFrom) {
                    System.out.println("成功");
                    try {
                        CtClass implCtClass = pool.get(next.getClass().getName());
                        this.createAnnotationMethodProxy(className, methodList, implCtClass);
                        implCtClass.toClass();
                        needCreateImpl = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (needCreateImpl) {
                //todo createImpl

            }

            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
//

    }

    /**
     * 给类添加注解
     *
     * @param className
     * @param methodList
     * @param ctClass
     */
    private void createAnnotationMethodProxy(String className, List<String> methodList, CtClass ctClass) {

        ClassFile classFile = ctClass.getClassFile();
        ConstPool constPool = classFile.getConstPool();
        AnnotationsAttribute methodAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation methodAnnot = new Annotation("work.skymoyo.mock.client.annotation.AopProxyAnnotation", constPool);
        methodAttr.addAnnotation(methodAnnot);

        if (methodList.isEmpty()) {
            Arrays.stream(ctClass.getDeclaredMethods())
                    .forEach(method -> {
                        try {
                            String methodKey = className + "#" + method.getName();
                            method.getMethodInfo().addAttribute(methodAttr);
                            log.debug("添加注解·处理方法:{},参数列表：{}", methodKey, Arrays.stream(method.getParameterTypes()).map(CtClass::getName).toArray());
                        } catch (Exception e) {
                            log.warn("添加注解·处理方法:{} 异常:{}", className + "#" + method.getName(), e.getMessage(), e);
                        }
                    });
        } else {

            methodList.forEach(methodName -> {
                String methodKey = className + "#" + methodName;
                try {
                    CtMethod[] methodArrays = ctClass.getDeclaredMethods(methodName);
                    for (CtMethod method : methodArrays) {
                        method.getMethodInfo().addAttribute(methodAttr);
                        log.debug("添加注解·处理方法:{},参数列表：{}", methodKey, Arrays.stream(method.getParameterTypes()).map(CtClass::getName).toArray());
                    }
                } catch (Exception e) {
                    log.warn("添加注解·处理方法:{} 异常:{}", className + "#" + methodName, e.getMessage(), e);
                }
            });
        }
    }

    /**
     * 创建方法代理
     *
     * @param className
     * @param methodList
     * @param ctClass
     */
    private void createMethodProxy(String className, List<String> methodList, CtClass ctClass) {
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
    }

    private void createMethod(String clazzName, CtMethod method) {
        String methodKey = clazzName + "#" + method.getName();
        try {
            log.debug("处理方法:{},参数列表：{}", methodKey, Arrays.stream(method.getParameterTypes()).map(CtClass::getName).toArray());

            String returnType = method.getReturnType().getName();

            String coding = new StringBuilder().append("try{")
                    .append("if(work.skymoyo.mock.client.utils.MockContextUtil.isEnableMock()){")
                    .append(" java.lang.reflect.Method method  = work.skymoyo.mock.client.utils.BeanMockUtil.getMethod($class,\"")
                    .append(method.getName())
                    .append("\",$sig);")
                    .append(" Object obj  =  work.skymoyo.mock.client.utils.MethodMockUtil.proxyInvoker( method ,  $args);")
                    .append(Objects.equals(returnType, "void") ? " return;" : " return (" + returnType + ") obj;")
                    .append("  }")
                    .append("} catch (Throwable ignored){")
                    .append("}")
                    .toString();

            method.insertBefore(coding);

        } catch (Exception e) {
            log.warn("处理方法:{} 异常:{}", methodKey, e.getMessage(), e);
        }
    }


}
