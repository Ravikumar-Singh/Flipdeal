package com.flipdeal.track.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Restwebservices {
	
	Logger logger=Logger.getLogger(Restwebservices.class); 

	public String getData(String api)
	{
		String token=null;
		CloseableHttpClient client=null;
		try
		{
		HttpGet get = new HttpGet(api);
		client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);
		HttpEntity responseEntity = response.getEntity();
		 return  EntityUtils.toString(responseEntity, "UTF-8");
		}
		catch(Exception e)
		{
			logger.error("exception while getting token "+e);
		}
		
		finally
		{
			if (client != null){
				try {
					 client.close();
				} catch (IOException e) {
					logger.error("exception while  closing resources "+e);
				
				}
	        }
		}
		
		return token;


	}
	
	}
