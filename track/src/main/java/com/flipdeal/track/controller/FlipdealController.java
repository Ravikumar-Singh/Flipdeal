package com.flipdeal.track.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.flipdeal.track.be.Login;

@RestController
@RequestMapping(value="/flipdeal")
public class FlipdealController {

	@Autowired
	Login login;
	
	@RequestMapping("/dumpAllData")
	public @ResponseBody String dumpData() {
		login.dumpDataInProductTable("A");
		return "Done";
	}
	
	@RequestMapping("/getPramotionA")
	public @ResponseBody String getSetAoffers() {
		return login.prepareSetAoffers().toString();
	}
	@RequestMapping("/getPramotionB")
	public @ResponseBody String getPramotionB() {
		return login.getPramotionB();
	}
}
