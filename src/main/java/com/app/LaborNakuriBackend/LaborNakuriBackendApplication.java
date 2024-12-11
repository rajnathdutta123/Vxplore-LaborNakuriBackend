package com.app.LaborNakuriBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LaborNakuriBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaborNakuriBackendApplication.class, args);
	}

}
