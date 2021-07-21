package com.ggoreb.weathertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ggoreb.weathertest.model.Air;

public interface AirRepository extends JpaRepository<Air, Long>{

}
