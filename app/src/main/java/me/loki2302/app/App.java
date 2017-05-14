package me.loki2302.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(me.loki2302.be.BackEndApp.class)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
