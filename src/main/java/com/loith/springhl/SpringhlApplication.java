package com.loith.springhl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// @EnableJpaRepositories(basePackages = "com")
@EnableScheduling
public class SpringhlApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringhlApplication.class, args);
  }
}
