package com.onetwo.userservice;

import com.onetwo.userservice.common.GlobalStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

    public static void main(String[] args) {
        String active = System.getProperty(GlobalStatus.SPRING_PROFILES_ACTIVE);
        if (active == null) {
            System.setProperty(GlobalStatus.SPRING_PROFILES_ACTIVE, GlobalStatus.SPRING_PROFILES_ACTIVE_DEFAULT);
        }

        System.setProperty(
                GlobalStatus.SPRING_PROFILES_ACTIVE,
                System.getProperty(GlobalStatus.SPRING_PROFILES_ACTIVE, GlobalStatus.SPRING_PROFILES_ACTIVE_DEFAULT)
        );
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
