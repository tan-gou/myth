package com.github.myth.demo.springcloud.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.github.myth.demo.springcloud.inventory.mapper")
public class SpringCloudInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudInventoryApplication.class, args);
    }


}
