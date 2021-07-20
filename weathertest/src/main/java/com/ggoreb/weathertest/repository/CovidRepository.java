package com.ggoreb.weathertest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ggoreb.weathertest.model.Covid;

public interface CovidRepository extends JpaRepository<Covid, Long> {
	Covid findByArea(String area);
}
