package com.crick.business.pharos.job;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * check服务器可访问
 */
@Component
public class PingCheckTask {
    private static OkHttpClient okHttpClient = new OkHttpClient();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ping.service.url}")
    private String serviceUrl;
    @Value("${ping.period:3}")
    private int period;

    private static Map<String, Integer> errorCalculate = new ConcurrentHashMap<>();

    private void resetCount(String url) {
        errorCalculate.put(url, 0);
    }

    private int pushCount(String url) {
        errorCalculate.put(url, errorCalculate.getOrDefault(url, 0) + 1);
        return errorCalculate.get(url);
    }

    @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    public void scheduler() throws IOException {
        Request request = new Request.Builder()
                .url(serviceUrl)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            logger.info("ping check {} success!", serviceUrl);
            resetCount(serviceUrl);
        } else {
            logger.info("ping check {} fail! response:{}", serviceUrl, response);
            int count = pushCount(serviceUrl);
            if (count > period) {
                // alert to admin!
            }
        }
    }
}
