package com.jjlapi.jjlapiinterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= DataSourceAutoConfiguration.class )
public class JjlapiInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JjlapiInterfaceApplication.class, args);
	}

}
