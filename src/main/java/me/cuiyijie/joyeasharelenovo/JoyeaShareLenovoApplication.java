package me.cuiyijie.joyeasharelenovo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("me.cuiyijie.joyeasharelenovo.dao")
@SpringBootApplication
public class JoyeaShareLenovoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoyeaShareLenovoApplication.class, args);
    }

}
