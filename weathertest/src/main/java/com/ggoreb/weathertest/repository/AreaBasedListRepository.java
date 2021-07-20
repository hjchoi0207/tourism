package com.ggoreb.weathertest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ggoreb.weathertest.model.AreaBasedList;


public interface AreaBasedListRepository extends JpaRepository<AreaBasedList, Long>{
	List<AreaBasedList> findAllByAreacode(Integer areacode);
}
