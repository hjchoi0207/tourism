package com.ggoreb.weathertest.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

import lombok.Data;
@Entity
@Data
@Transactional
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String title;
 String content;
String userId;
}