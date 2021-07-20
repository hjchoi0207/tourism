package com.ggoreb.weathertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ggoreb.weathertest.model.AreaTemp;

public interface AreaTempRepository extends JpaRepository<AreaTemp, Long>{

}
