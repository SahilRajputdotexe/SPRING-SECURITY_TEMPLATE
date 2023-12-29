package com.project.authtemp;

import com.project.authtemp.payload.request.RegisterRequest;
import com.project.authtemp.services.AuthenticationService;

import static com.project.authtemp.model.role.Role.ADMIN;
import static com.project.authtemp.model.role.Role.MANAGER;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service) {
		return args -> {
			String adminName = "Admin";
			var admin = RegisterRequest.builder()
					.firstname(adminName)
					.lastname(adminName)
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println(
					"Admin token: " + service.register(admin).getResponseObject().get("accessToken").toString());
			var manager = RegisterRequest.builder()
					.firstname("manager")
					.lastname("manager")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.build();
			System.out.println(
					"Manager token: " + service.register(manager).getResponseObject().get("accessToken").toString());

		};
	}
}
