package com.peixian;

import com.peixian.util.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author TonyStark
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.peixian.goods.mapper")
public class GoodsApplication {

    @Value("${workerId}")
    private long workerId;
    @Value("${dataCenterId}")
    private long dataCentId;

    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(workerId,dataCentId);
    }
}
