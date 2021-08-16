package com.lead.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author Lzw
 * @date 2021/8/12.
 */
public class ErrorNoticeAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println(" JAVA Agent Notice - Lzw ");

        instrumentation.addTransformer(new NoticeTransformer());
    }
}
