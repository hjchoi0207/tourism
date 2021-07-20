package com.ggoreb.weathertest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ggoreb.weathertest.model.AirCondition;

public interface AirConditionRepository extends JpaRepository<AirCondition, Long> {

}
