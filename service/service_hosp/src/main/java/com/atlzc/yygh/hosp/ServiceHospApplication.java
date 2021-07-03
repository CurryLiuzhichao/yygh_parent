package com.atlzc.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.atlzc.yygh.hosp.mapper")
@ComponentScan(basePackages = "com.atlzc")
@EnableDiscoveryClient
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class, args);
    }
}

