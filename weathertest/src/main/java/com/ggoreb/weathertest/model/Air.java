package com.ggoreb.weathertest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Air {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String airInfo;
	Integer areacode;
}
