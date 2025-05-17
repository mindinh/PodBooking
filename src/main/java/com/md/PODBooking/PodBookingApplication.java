package com.md.PODBooking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "POD Booking REST API Documentation",
				description = "API Docs for a personal space booking application"
		)
)
public class PodBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PodBookingApplication.class, args);
	}

}
