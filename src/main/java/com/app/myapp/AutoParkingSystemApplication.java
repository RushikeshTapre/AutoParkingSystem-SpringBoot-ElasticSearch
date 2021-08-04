package com.app.myapp;

import com.app.myapp.util.SpringBootUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AutoParkingSystemApplication {

	public static void main(String[] args) {
		//SpringBootUtil.setRandomPort(5000, 9000);
		SpringApplication.run(AutoParkingSystemApplication.class, args);
	}

}
