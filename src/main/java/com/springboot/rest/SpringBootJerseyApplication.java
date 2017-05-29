package com.springboot.rest;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class from spring boot
 *
 */
@SpringBootApplication
public class SpringBootJerseyApplication {
	private static final Logger logger = Logger.getLogger(SpringBootJerseyApplication.class);
  
	public static void main(String[] args) {
		logger.info("Entering main spring boot class");
		SpringApplication.run(SpringBootJerseyApplication.class, args);
	}
}