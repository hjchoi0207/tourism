package com.ggoreb.weathertest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Covid {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	String area;
	String incdec;
	String total;
	String isol;
	String isolClear;
	String overflow;
	String local;

}
