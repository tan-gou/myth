package com.github.myth.demo.dubbo.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
@MapperScan("com.github.myth.demo.dubbo.inventory.mapper")
public class DubboInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboInventoryApplication.class, args);
    }
}
