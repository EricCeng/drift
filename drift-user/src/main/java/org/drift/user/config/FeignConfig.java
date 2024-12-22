package org.drift.user.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jiakui_Zeng
 * @date 2024/12/21 23:58
 */
@Configuration
public class FeignConfig {
    /**
     * 配置 OpenFeign 是否开启重试机制
     */
    @Bean
    public Retryer myRetryer() {
        // 最大请求次数 3(1 default + 2 重试)，初始间隔时间为 100ms，重试间隔时间为 1s
        // return new Retryer.Default(100, 1, 3);
        return Retryer.NEVER_RETRY;
    }

    /**
     * 配置 OpenFeign 日志打印级别
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
