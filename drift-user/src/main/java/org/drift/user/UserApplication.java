package org.drift.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author jiakui_zeng
 * @date 2024/12/20 17:10
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {"org.drift.user", "org.drift.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.drift.common.feign")
@MapperScan("org.drift.user.mapper")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class);
    }
}