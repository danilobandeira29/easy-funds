package com.github.danilobandeira29.easy_funds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootApplication
@EntityScan(basePackages = "com.github.danilobandeira29.easy_funds.repositories")
public class EasyFundsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyFundsApplication.class, args);
	}

}
