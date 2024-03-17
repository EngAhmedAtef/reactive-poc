package com.gizasystems.usermanagement;

import com.gizasystems.cssdb.annotation.ImportCssDb;
import com.gizasystems.usermanagement.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ImportCssDb

@SpringBootApplication
public class UserManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}
}
