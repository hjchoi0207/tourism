package com.ggoreb.weathertest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
 
@Data
@Entity
public class AreaTemp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Integer areacode;
	String addr1;
	String title;
	
}
