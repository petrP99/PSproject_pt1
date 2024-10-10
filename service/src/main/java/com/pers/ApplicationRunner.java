package com.pers;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
@EnableAspectJAutoProxy
public class ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
//        long maxMemory = Runtime.getRuntime().maxMemory();
//        long allocatedMemory = Runtime.getRuntime().totalMemory();
//        long freeMemory = Runtime.getRuntime().freeMemory();
//
//        System.out.println("Max Memory: " + maxMemory / (1024 * 1024) + " MB");
//        System.out.println("Allocated Memory: " + allocatedMemory / (1024 * 1024) + " MB");
//        System.out.println("Free Memory: " + freeMemory / (1024 * 1024) + " MB");
    }
}

