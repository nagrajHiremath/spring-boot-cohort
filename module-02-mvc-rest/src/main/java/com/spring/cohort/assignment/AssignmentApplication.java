package com.spring.cohort.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.spring.cohort.assignment.repository")
public class AssignmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}
}
