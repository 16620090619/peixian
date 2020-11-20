package com.peixian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author TonyStark
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.peixian.system.mapper"})
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run( SystemApplication.class);
    }
}
