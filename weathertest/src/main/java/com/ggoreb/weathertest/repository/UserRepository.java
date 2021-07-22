package com.ggoreb.weathertest.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ggoreb.weathertest.model.User;
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmailAndPwd(String email, String pwd);
}