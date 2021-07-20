package com.ggoreb.weathertest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class AirCondition {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	Integer stationCode;
	Integer airConditionValue;
}
