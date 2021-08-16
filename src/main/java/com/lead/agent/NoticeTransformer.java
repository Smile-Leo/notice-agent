package com.lead.agent;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author Lzw
 * @date 2021/8/12.
 */
public class NoticeTransformer implements ClassFileTransformer {

    public static final String BOOT_LOADER_LAUNCHED_URLCLASS_LOADER = "org/springframework/boot/loader/LaunchedURLClassLoader";
    public static ClassLoader loader;

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        /**
         * 找到Springboot类加载器LaunchedURLClassLoader
         * 让类加载器去调用修改类字节方法
         */
        if (className.equalsIgnoreCase(BOOT_LOADER_LAUNCHED_URLCLASS_LOADER)) {
            try {
                CtClass ctClass = ClassPool.getDefault().get("org.springframework.boot.loader.LaunchedURLClassLoader");
                CtConstructor[] ctConstructors = ctClass.getDeclaredConstructors();
                ctConstructors[0].insertAfter("com.lead.agent.NoticeTransformer.modify(this);");
                return ctClass.toBytecode();
            } catch (NotFoundException | CannotCompileException | IOException e) {
                e.printStackTrace();
            }
        }

        return new byte[0];
    }


    public static void modify(ClassLoader loader) {
        try {
            //保存Springboot类加载器
            NoticeTransformer.loader = loader;

            ClassPool classPool = ClassPool.getDefault();
            //使用Springboot加载器路径
            classPool.appendClassPath(new LoaderClassPath(loader));

            //开始修改
            CtClass ctClass = classPool.get("ch.qos.logback.classic.Logger");

            CtMethod declaredMethod = ctClass.getDeclaredMethod("buildLoggingEventAndAppend");

            int length = declaredMethod.getParameterTypes().length;

            String[] strings = new String[length];
            for (int i = 0; i < length; i++) {
                strings[i] = ("$args[" + i + "]");
            }
            String collect = String.join(",", strings);
            declaredMethod.insertBefore("com.lead.agent.Robot.preLog(" + collect + ");");

            //重新加载类
            ctClass.toClass(loader, ctClass.getClass().getProtectionDomain());


        } catch (NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }
    }

}
