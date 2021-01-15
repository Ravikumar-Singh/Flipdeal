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
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "type")
	public String type;
	
	@Column(name = "cost")
	public Double cost;
	
	@Column(name = "country")
	public String country;
	
	@Column(name = "productId")
	public String  productId;
	
	
	@Column(name = "rating")
	public Double rating;
	
	@Column(name = "inventory")
	public int inventory;
	
	@Column(name = "status")
	public String  status;
}
