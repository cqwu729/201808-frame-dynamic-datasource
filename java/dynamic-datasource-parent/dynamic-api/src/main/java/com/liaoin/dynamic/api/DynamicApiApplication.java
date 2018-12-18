package com.liaoin.dynamic.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan("com.liaoin.dynamic")
@MapperScan("com.liaoin.dynamic.mybatis")
public class DynamicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicApiApplication.class, args);
    }

}

