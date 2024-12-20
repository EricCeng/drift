package org.drift.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jiakui_zeng
 * @date 2024/12/20 17:11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PostApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostApplication.class);
    }
}