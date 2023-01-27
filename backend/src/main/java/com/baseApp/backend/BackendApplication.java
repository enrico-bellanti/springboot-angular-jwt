package com.baseApp.backend;

import com.baseApp.backend.models.User;
import com.baseApp.backend.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			UserRepository userRepository
	){
		return args -> {
			/*User user = new User();
			user.setFirstName("firstname");
			user.setLastName("lastname");
			user.setEmail("user@mail.io");
			user.setPassword("password");
			user.setPhone("199199199");
			user.setPreferredLang(Locale.ENGLISH);
			userRepository.save(user);

			Optional<User> newUser = userRepository.findById(user.getId());

			System.out.println(newUser);*/
		};
	}

}
