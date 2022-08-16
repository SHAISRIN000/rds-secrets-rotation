package com.luanvv.aws.rdssecrets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class RdssecretsApplication {
@Autowired
private static Environment env;

	public static void main(String[] args) {
		//System.out.println("Environment Variable"+env.getProperty("secret"));
		SpringApplication.run(RdssecretsApplication.class, args);
	}

}
