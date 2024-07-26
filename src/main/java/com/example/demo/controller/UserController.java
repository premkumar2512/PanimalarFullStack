package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.ai.GETAIAnswers;
import com.example.demo.entity.LoginResponse;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@CrossOrigin(origins = "http://localhost/")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		if (user.getFirstname().length() < 3 || user.getFirstname().length() > 15
				|| !containsOnlyAlphabets(user.getFirstname())) {
			return ResponseEntity.badRequest().body(new Response("error", "First name must be 3 to 15 alphabets."));
		}
		if (user.getLastname().length() < 3 || user.getLastname().length() > 15
				|| !containsOnlyAlphabets(user.getLastname())) {
			return ResponseEntity.badRequest().body(new Response("error", "Last name must be 3 to 15 alphabets."));
		}
		if (user.getMobile().length() != 10 || !containsOnlyDigits(user.getMobile())) {
			return ResponseEntity.badRequest().body(new Response("error", "Mobile number must be 10 digits."));
		}
		if (!user.getEmail().contains("@") || !user.getEmail().contains(".com")) {
			return ResponseEntity.badRequest().body(new Response("error", "Invalid email format."));
		}
		if (!isValidPassword(user.getPassword())) {
			return ResponseEntity.badRequest().body(new Response("error",
					"Password must be at least 8 characters, contain one uppercase, one lowercase, one digit, and one special character."));
		}
		if (!user.getPassword().equals(user.getConfirmpassword())) {
			return ResponseEntity.badRequest().body(new Response("error", "Passwords do not match."));
		}
		if (user.getAge() < 0) {
			return ResponseEntity.badRequest().body(new Response("error", "Age must be a positive number."));
		}
		if (user.getCollege().length() < 1) {
			return ResponseEntity.badRequest().body(new Response("error", "College name should not be null"));
		}
		if (user.getDepartment().length() < 1) {
			return ResponseEntity.badRequest().body(new Response("error", "Departname should not be null"));
		}
		if (user.getCity().length() < 1) {
			return ResponseEntity.badRequest().body(new Response("error", "City name should not be null"));
		}
		if (user.getHobbies().length() < 1) {
			return ResponseEntity.badRequest().body(new Response("error", "Hobbies should not be null"));
		}

		String about = "Username is " + user.getFirstname() + ", age is " + user.getAge() + ", from city "
				+ user.getCity() + " and studied in " + user.getCollege() + " college in " + user.getDepartment()
				+ " dept and hobbies are " + user.getHobbies();

		user.setAbout(GETAIAnswers.chatGPT(about));

		userRepository.save(user);
		return ResponseEntity.ok(new Response("success", "User registered successfully"));
	}

	@CrossOrigin(origins = "http://localhost/")
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) {
		
		User foundUser = userRepository.findLatestByEmail(user.getEmail());
		if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
			return ResponseEntity.ok(new LoginResponse("success", "Login successful", foundUser));
		}
		return ResponseEntity.badRequest().body(new Response("error", "Invalid login credentials"));
	}

	private boolean containsOnlyAlphabets(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	private boolean containsOnlyDigits(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	private boolean isValidPassword(String password) {
		boolean hasUppercase = false;
		boolean hasLowercase = false;
		boolean hasDigit = false;
		boolean hasSpecial = false;

		if (password.length() < 8) {
			return false;
		}

		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				hasUppercase = true;
			} else if (Character.isLowerCase(c)) {
				hasLowercase = true;
			} else if (Character.isDigit(c)) {
				hasDigit = true;
			} else {
				hasSpecial = true;
			}
		}

		return hasUppercase && hasLowercase && hasDigit && hasSpecial;
	}
}
