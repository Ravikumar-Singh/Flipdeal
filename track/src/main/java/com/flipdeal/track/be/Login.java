package com.flipdeal.track.be;

import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipdeal.track.model.Product;
import com.flipdeal.track.repo.ProductRepo;
import com.flipdeal.track.services.Restwebservices;

@Component
public class Login {
	
	@Autowired
	Restwebservices servie;
	
	@Autowired
	ProductRepo productRepo;
	
	
	public void dumpDataInProductTable(String set)
	{
		String response=servie.getData("https://api.jsonbin.io/b/5d31a1c4536bb970455172ca/latest");
		JSONArray allData=new JSONArray(response);
		String currencyDataResponse=servie.getData("https://api.exchangeratesapi.io/latest");
		JSONObject currencyData=new JSONObject(currencyDataResponse).getJSONObject("rates");
		ObjectMapper mapper=new ObjectMapper();
		for(int i=0;i<allData.length();i++)
		{
			try
			{
			JSONObject productDetail = allData.getJSONObject(i);
			Product product = productRepo.findByProductAndCategory(productDetail.getString("product"),
					productDetail.getString("category"));
			if (product == null) {
				product=mapper.readValue(productDetail.toString(), Product.class);
				product.price = cunvertCurrency(productDetail.getString("currency"), productDetail.get("price").toString(),currencyData);
				product.currency="INR";
				
				productRepo.save(product);
			} else {
				product.price = 
						cunvertCurrency(productDetail.getString("currency"), productDetail.get("price").toString(),currencyData);
				product.origin = productDetail.getString("origin");
				product.product = productDetail.getString("product");
				
				product.category = productDetail.getString("category");
				product.rating=Double.parseDouble(productDetail.get("rating").toString());
				product.inventory=(int) productDetail.get("inventory");
				product.arrival=getArrival("arrival", productDetail);
				product.currency="INR";
				productRepo.save(product);
			}
			}
			catch (Exception e) {
				System.out.println(e+" while dumping data");
				e.printStackTrace();
			}

		}
		
	}
	
	public String getArrival(String key, JSONObject productDetail)
	{
		if(productDetail.has(key))
		{
			return productDetail.get(key).toString();
		}
		return "";
	}
	
	public double cunvertCurrency(String currency,String price,JSONObject currentcyMapping)
	{
		if(currency.equalsIgnoreCase("INR"))
		{
			return Double.parseDouble(price);
		}
		else
		{
			
			String currencyValue=currentcyMapping.get(currency).toString();
			return (Double.parseDouble(price)/Double.parseDouble(currencyValue))*Double.parseDouble(currentcyMapping.get("INR").toString());
		}
		
	}
	
	public JSONArray prepareSetAoffers() 
	{
		List<Product> allProd=(List<Product>) productRepo.findAll();
		JSONArray allProdWithOffer=new JSONArray();
		ObjectMapper mapper=new ObjectMapper();
		
		for(int i=0;i<allProd.size();i++)
		{
			try
			{
			Double countryDiff=0.0d;
			String countryofferMessage="";
			Double ratingDiff=0.0d;
			String ratingMessage="";
			Double porductDiff=0.0d;
			String productmessage="";
			Double overCostDiff=0.0d;
			String overCostmessage="";
			Product product=allProd.get(i);
			JSONObject prodWithOffer=new JSONObject(mapper.writeValueAsString(product));
			JSONObject offerDetails=new JSONObject();

			if(product.origin.equalsIgnoreCase("Africa"))
			{
				countryDiff=((product.price*7)/100);
				countryofferMessage="7% Discount";
			}
			
			else if(product.rating<=2)
			{
				if (product.rating == 2) {
					ratingDiff = ((product.price * 4) / 100);
					ratingMessage="4% Discount";
				} else {
					ratingDiff = ((product.price * 8) / 100);
					ratingMessage="8% Discount";
				}
			}
			
			
			else if((product.category.equalsIgnoreCase("electronics")||product.category.equalsIgnoreCase("furnishing")) && product.price>=500)
			{
				porductDiff=100.0d;
				productmessage="100rs off";
			}
			else if(product.price>1000 )
			{
				overCostDiff=((product.price * 2) / 100);
				overCostmessage="2% Discount";
				
			}
			else
			{
				
				allProdWithOffer.put(prodWithOffer);
				
				continue;
			}
			double[] numbers = new double[] { countryDiff, ratingDiff, porductDiff,overCostDiff };
		    Arrays.sort(numbers);
		    double biggestDiscout=numbers[3];
		    String BiggestOfferMessage="";
		    if(biggestDiscout==countryDiff)
		    {
		    	BiggestOfferMessage="7% Discount";
		    }
		    else if(biggestDiscout==porductDiff)
		    {
		    	BiggestOfferMessage="100rs off";
		    }
		    
		    else if (biggestDiscout==overCostDiff)
		    {
		    	BiggestOfferMessage="2% Discount";
		    }
		    else
		    {
		    	if(product.rating == 2)
		    	{
		    		BiggestOfferMessage="4% Discount";
		    	}
		    	else
		    	{
		    		BiggestOfferMessage="8% Discount";
		    	}
		    }
		    
			
			offerDetails.put("Amount", product.price-biggestDiscout);
			offerDetails.put("Discount tag", BiggestOfferMessage);
			prodWithOffer.put("discout", offerDetails);
			allProdWithOffer.put(prodWithOffer);
			}
			catch (Exception e) {
				System.out.println(e +"while setting offer A");
				e.printStackTrace();
			}
		}
		return allProdWithOffer;
	}
	
	
	
	public String getPramotionB()
	{
		List<Product> allProd=(List<Product>) productRepo.findAll();
		JSONArray allProdWithOffer=new JSONArray();
		ObjectMapper mapper=new ObjectMapper();
		
		for(int i=0;i<allProd.size();i++)
		{
			try
			{
				String inventorymessage="";
				Double inventoryDiff=0.0d;
				Double overCostDiff=0.0d;
				String overCostmessage="";
				Double statustDiff=0.0d;
				String statusmessage="";
				Product product=allProd.get(i);
				JSONObject prodWithOffer=new JSONObject(mapper.writeValueAsString(product));
				JSONObject offerDetails=new JSONObject();
				
				if(product.inventory>20)
				{
					inventoryDiff=((product.price * 12) / 100);
					inventorymessage="12% discount";
				}
				else if (product.arrival!=null&&!product.arrival.equalsIgnoreCase("null") && product.arrival.equalsIgnoreCase("new"))
				{
					 statustDiff=((product.price * 7) / 100);
					 statusmessage="7% discount";
				}
				else if(product.price>1000 )
				{
					overCostDiff=((product.price * 2) / 100);
					overCostmessage="2% Discount";
					
				}
				else
				{
					allProdWithOffer.put(prodWithOffer);
					continue;
				}
				double[] numbers = new double[] { overCostDiff,statustDiff,inventoryDiff };
			    Arrays.sort(numbers);
			    double biggestDiscout=numbers[2];
			    String BiggestOfferMessage="";
			    
			    if(biggestDiscout==inventoryDiff)
			    {
			    	BiggestOfferMessage="12% Discount";
			    }
			    else if(biggestDiscout==statustDiff)
			    {
			    	BiggestOfferMessage="7% discount";
			    }
			    
			    else if (biggestDiscout==overCostDiff)
			    {
			    	BiggestOfferMessage="2% Discount";
			    }
			    offerDetails.put("Amount", product.price-biggestDiscout);
				offerDetails.put("Discount tag", BiggestOfferMessage);
				prodWithOffer.put("discout", offerDetails);
				allProdWithOffer.put(prodWithOffer);
			   
			}
			catch (Exception e) {
				System.out.println(e +"while setting offer B");
				e.printStackTrace();
			}
		}
		return allProdWithOffer.toString();
		
				
	}
	


}
