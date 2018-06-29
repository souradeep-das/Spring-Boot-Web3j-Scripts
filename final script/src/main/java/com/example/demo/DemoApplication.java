package com.example.demo;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

	final static Logger log = LoggerFactory.getLogger(DemoApplication.class);
	@Autowired
	democontroller democontroller;

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try{// democontroller.getCredentials();
		// democontroller.getCredentials100();
		// democontroller.initialfunding();
		democontroller.deployContractwithtrans();
		democontroller.runalternate();
		}
		catch(Exception e)
		{
			log.error("Exception in run" + e);
		}
	}

}
