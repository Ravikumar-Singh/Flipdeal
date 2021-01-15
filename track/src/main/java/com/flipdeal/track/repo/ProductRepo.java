package com.flipdeal.track.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.flipdeal.track.model.Product;

@Repository
public interface ProductRepo extends CrudRepository<Product, String>{
	
	Product findByProduct(String product);
	Product findById(int id);
	Product findByProductAndCategory(String product,String category);
}
