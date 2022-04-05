package com.sse.g4.proj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.sse.g4.proj.dao")
@SpringBootApplication
public class ProjMgntSuitApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(ProjMgntSuitApplication.class, args);
    }
}
