package com.ggoreb.weathertest.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ggoreb.weathertest.model.User;
import com.ggoreb.weathertest.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute User user) {
		System.out.println(user);
		userRepository.save(user);
		return "redirect:/board";
	}

	@Autowired
	HttpSession session;

	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}

	@PostMapping("/signin")
	public String signinPost(@ModelAttribute User user, HttpSession session) {
		Optional<User> opt = userRepository.findByEmailAndPwd(user.getEmail(), user.getPwd());
		if (opt.isPresent()) {
			session.setAttribute("user", opt.get());
		}
		return "redirect:/board";
	}

	@GetMapping("/signout")
	public String signout() {
		session.invalidate();
		return "redirect:/";
	}
}