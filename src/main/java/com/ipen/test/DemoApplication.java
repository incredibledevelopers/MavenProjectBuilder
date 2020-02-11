package com.ipen.test;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws MavenInvocationException {
		SpringApplication.run(DemoApplication.class, args);
	}

}
