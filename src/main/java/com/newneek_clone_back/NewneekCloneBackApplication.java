package com.newneek_clone_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing//time알아서 변경
public class NewneekCloneBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewneekCloneBackApplication.class, args);
    }

}
