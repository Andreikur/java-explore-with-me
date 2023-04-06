package ru.practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(scanBasePackages = "ru.practicum.explore-with-me")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}