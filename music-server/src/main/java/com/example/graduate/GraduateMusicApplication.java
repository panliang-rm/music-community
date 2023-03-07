package com.example.graduate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.graduate.dao")
public class GraduateMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraduateMusicApplication.class, args);
    }

}

