package org.drift.post;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jiakui_zeng
 * @date 2024/12/20 17:11
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {"org.drift.post", "org.drift.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.drift.common.feign")
@MapperScan("org.drift.post.mapper")
public class PostApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class);
    }
}