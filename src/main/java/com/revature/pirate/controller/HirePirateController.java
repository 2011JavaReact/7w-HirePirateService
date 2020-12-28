package com.revature.pirate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.revature.pirate.model.Pirate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hire")
@Slf4j
public class HirePirateController {
	@Autowired
	RestTemplate restTemplate;
	
	//method that uses rest template to get something....
	@GetMapping("/pirate/{id}")
	public Pirate getPirate(@PathVariable int id) {
		Pirate pirate = restTemplate.getForObject("http://localhost:8082/pirate/"+id, Pirate.class);
		log.info("GET request made to /pirate/"+id);
		log.info("Pirate received:" + pirate);
		return pirate;
	}
}
