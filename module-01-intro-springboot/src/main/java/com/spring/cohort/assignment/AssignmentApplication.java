package com.spring.cohort.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AssignmentApplication {
	@Autowired
	CakeBaker cakeBaker;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AssignmentApplication.class, args);
		AssignmentApplication assignmentApplication = context.getBean(AssignmentApplication.class);
		assignmentApplication.cakeBaker.bakeCake();
	}

}
