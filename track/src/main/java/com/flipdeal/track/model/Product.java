package com.flipdeal.track.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "product")
@JsonIgnoreProperties(ignoreUnknown = true)

public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int id;
	
	@Column(name = "product")
	public String product;
	
	@Column(name = "category")
	public String category;
	
	@Column(name = "price")
	public Double price;
	
	@Column(name = "origin")
	public String origin;
	
	@Column(name = "rating")
	public Double rating;
	
	@Column(name = "inventory")
	public int inventory;
	
	@Column(name = "arrival")
	public String  arrival;
	
	@Column(name = "currency")
	public String  currency;
}
