package com.flipdeal.track.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flipdeal.track.model.Product;

@Repository
public interface ProductRepo extends CrudRepository<Product, String>{
	
	Product findByName(String name);
	Product findById(int id);
	Product findByProductId(String productId);
	Product findByNameAndType(String name,String type);
}
