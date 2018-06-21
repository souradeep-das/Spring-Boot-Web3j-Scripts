package com.example.demo;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

	@Autowired
	democontroller democontroller;

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// democontroller.getCredentials();
		// democontroller.getCredentials100();
		// democontroller.initialfunding();
	   democontroller.deployContractwithtrans();
		//democontroller.sendEthersbetween();
		democontroller.setvaluecontract();
	}

}
