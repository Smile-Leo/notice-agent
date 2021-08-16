package com.lead.agent;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Lzw
 * @date 2021/8/16.
 */
public class WecomRobot {

    private static final String NOTICE_DTO = "{\"msgtype\": \"markdown\",\"markdown\": {\"content\": \"运行异常通知:\n项目:%s\n信息:%s\n <font size=10>\n %s </font>\n \n\"}}";

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    public static final String WECOM_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=XXXXXXX";

    public static void send(String name, String msg, String stack) {
        String contend = String.format(NOTICE_DTO, name, msg, stack);

        Call call = CLIENT.newCall(new Request.Builder()
                .url(WECOM_URL)
                .post(RequestBody.create(MediaType.parse("application/json"), contend))
                .cacheControl(new CacheControl.Builder().noCache().build())
                .build()
        );

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }
}
