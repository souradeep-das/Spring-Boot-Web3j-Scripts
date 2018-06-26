package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

@Service
public class Demoservice {
 
	
	//@Autowired
	//Web3j web3j;
	
	Web3j web3j= Web3j.build(new HttpService("http://206.189.229.33:8545"));
	
    public String getClientVersion() throws IOException {

        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();

        return web3ClientVersion.getWeb3ClientVersion();

    }
}
