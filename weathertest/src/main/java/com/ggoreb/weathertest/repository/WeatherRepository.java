package com.ggoreb.weathertest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ggoreb.weathertest.model.Weather;

public interface WeatherRepository extends JpaRepository<Weather, Long>{
	//
	Weather findByAreacode(Integer areacode);
}
