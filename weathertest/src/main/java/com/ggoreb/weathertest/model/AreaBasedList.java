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
public class AreaBasedList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	Integer areacode;
	String addr1;
	String title;
	String mapx;
	String mapy;
	String firstimage;
	String tel;
	String readcount;
	@ManyToOne
	@JoinColumn(name = "weatherId")
	Weather weather;
}
