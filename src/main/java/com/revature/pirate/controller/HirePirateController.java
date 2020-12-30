package com.revature.pirate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.revature.pirate.model.Pirate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hire")
@Slf4j
public class HirePirateController {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	DiscoveryClient discoveryClient;
	
	//these are just boilerplate to get you a client that you can use to access AWS SNS
	private static AWSCredentialsProvider credentialProvider = new AWSStaticCredentialsProvider(
			new BasicAWSCredentials(System.getenv("ACCESS_KEY"), System.getenv("SECRET_ACCESS_KEY")));
	private static AmazonSNSClient snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard()
			.withCredentials(credentialProvider).withRegion(Regions.US_EAST_2).build();
	private final String TOPIC_ARN = System.getenv("TOPIC_ARN");
	
	//this endpoint is used to validate that the pirate exists
	@GetMapping("/validate/pirate/{id}")
	public Pirate getPirate(@PathVariable int id) {
		String uri = this.discoveryClient.getInstances("pirate-server").get(0).getUri().toString();
		//the line below makes a request to the PirateServer
		Pirate pirate = restTemplate.getForObject( uri+"/pirate/"+id, Pirate.class);
		log.info("GET request made to /pirate/"+id);
		log.info("Pirate received:" + pirate);
		return pirate;
	}
	
	//this endpoint is used to request the processing the hiring of a pirate
	//this endpoint should publish to SQS trhough AWS SNS
	@GetMapping("/pirate/{id}")
	public void requestHireProcessing(@PathVariable int id) {
		log.info("Sending in request to hire pirate with id:" + id);
		snsClient.publish(TOPIC_ARN,"The message id you wanted was:"+id);
	}
}
