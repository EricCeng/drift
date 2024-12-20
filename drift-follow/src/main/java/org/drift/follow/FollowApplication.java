package org.drift.follow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jiakui_zeng
 * @date 2024/12/20 17:13
 */
@SpringBootApplication
@EnableDiscoveryClient
public class FollowApplication {
    public static void main(String[] args) {
        SpringApplication.run(FollowApplication.class);
    }
}