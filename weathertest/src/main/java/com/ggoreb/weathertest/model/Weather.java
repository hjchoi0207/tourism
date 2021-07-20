package com.ggoreb.weathertest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Weather {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int weatherId;
	String popCategory;
	String skyCategory;
	String popCategoryValue;
	String skyCategoryValue;
	Integer nx;
	Integer ny;
	Integer areacode;
}
