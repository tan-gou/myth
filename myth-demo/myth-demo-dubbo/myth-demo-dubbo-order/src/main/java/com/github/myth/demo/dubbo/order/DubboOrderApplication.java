package com.github.myth.demo.dubbo.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
@MapperScan("com.github.myth.demo.dubbo.order.mapper")
public class DubboOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboOrderApplication.class, args);
    }
}
