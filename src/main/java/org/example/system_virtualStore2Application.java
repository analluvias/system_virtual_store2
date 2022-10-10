package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@SpringBootApplication
@EnableSwagger2
public class system_virtualStore2Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        //chamando aqui a inicalização da aplicação
        SpringApplication.run(system_virtualStore2Application.class, args);
    }

}
