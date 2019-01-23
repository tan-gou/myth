package com.github.myth.demo.dubbo.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
@MapperScan("com.github.myth.demo.dubbo.account.mapper")
public class DubboAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboAccountApplication.class, args);
    }
}
