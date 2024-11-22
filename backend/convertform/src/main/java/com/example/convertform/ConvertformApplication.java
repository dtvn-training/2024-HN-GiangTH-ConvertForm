package com.example.convertform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan
public class ConvertformApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(ConvertformApplication.class, args);
	}
}
