package com.pipiolo.sort;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SortApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SortApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}