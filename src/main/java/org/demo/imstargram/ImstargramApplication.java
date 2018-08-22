package org.demo.imstargram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@Configuration
@ComponentScan("org.demo.imstargram")
public class ImstargramApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImstargramApplication.class, args);
	}
}
