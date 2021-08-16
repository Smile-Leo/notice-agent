package com.lead.agent;

import okhttp3.OkHttpClient;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Lzw
 * @date 2021/8/12.
 */
public class Robot {

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static Field levelint = null;
    private static String name = null;

    public static void preLog(Object localFQCN, Object marker, Object level, Object msg, Object params, Object t) {

        try {
            if (Objects.isNull(levelint)) {
                levelint = NoticeTransformer.loader.loadClass("ch.qos.logback.classic.Level").getDeclaredField("levelInt");
            }
            int anInt = levelint.getInt(level);
            if (!Objects.equals(40000, anInt)) {
                return;
            }
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }

        if (name == null) {
            name = System.getProperty("appName");
            if (name == null) {
                name = ManagementFactory.getRuntimeMXBean().getName();
                name = name.substring(0, name.indexOf("@"));
            }
        }
        String stack = "";
        if (t instanceof Throwable) {

            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            ((Throwable) t).printStackTrace(printWriter);
            printWriter.flush();
            stringWriter.flush();
            String s = stringWriter.toString();
            stack = s.substring(0, Math.min(s.length(), 3800));
        }

        WecomRobot.send(name, msg.toString(), stack);

    }


}
