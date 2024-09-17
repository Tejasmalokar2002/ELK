package com.HotelService.HotelServcie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HotelServcieApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelServcieApplication.class, args);
	}

}
