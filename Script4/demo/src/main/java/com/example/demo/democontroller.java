package com.example.demo;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

@RestController
public class democontroller {

	final static Logger log = LoggerFactory.getLogger(democontroller.class);

	@Autowired
	FromAccountsRepository fromaccountsrepository;

	@Autowired
	ToAccountsRepository toaccountsrepository;

	long fromAccountscount = 0;
	// Random r2 = new Random();

	
//	@Autowired
//	Web3j web3j;
	private Web3j web3j = Web3j.build(new HttpService("http://178.128.32.195:8546"));
	//private Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));


	private String senderPrivKey = "8def6abc00c429f80b6b8911d62e97b53a2da2f86e537bedd56c508b09d576f2";
	Credentials credentialOfSender = Credentials.create(senderPrivKey);

	/*
	 * @RequestMapping(value = "/check", method = RequestMethod.GET) String
	 * getVersion() throws IOException { return demoservice.getClientVersion(); }
	 */

	@RequestMapping(value = "/fromAddress", method = RequestMethod.GET)
	public void getCredentials() {

		try {
			log.info("################################ getCredentials");

			for (long u = 0; u < 200; u++) {
				byte[] reci = Hash.sha3(UUID.randomUUID().toString().getBytes());
				ECKeyPair keys = ECKeyPair.create(reci);
				String address = Keys.getAddress(keys).toString();
				BigInteger publicKey = keys.getPublicKey();
				String publicKeyHex = Numeric.toHexStringWithPrefix(publicKey);
				BigInteger privateKey = keys.getPrivateKey();
				String privateKeyHex = Numeric.toHexStringWithPrefix(privateKey);
				String publicKeystring = publicKey.toString();

				Credentials credentials = Credentials.create(privateKeyHex);
				log.info("address: '" + address + "'\n" + credentials.getAddress());
				log.info("public key address :" + publicKey);
				fromaccountsrepository
						.save(new FromAccounts(u + 1, credentials.getAddress(), publicKeyHex, privateKeyHex));

			}
		} catch (Exception e) {
			log.info("Error" + e);
		}

	}

	@RequestMapping(value = "/toAddress", method = RequestMethod.GET)
	public void getCredentials100() {
		try {
			log.info("################################ getCredentials");

			for (long u = 0; u < 100; u++) {
				byte[] reci = Hash.sha3(UUID.randomUUID().toString().getBytes());
				ECKeyPair keys = ECKeyPair.create(reci);
				String address = Keys.getAddress(keys).toString();
				BigInteger publicKey = keys.getPublicKey();
				String publicKeyHex = Numeric.toHexStringWithPrefix(publicKey);
				BigInteger privateKey = keys.getPrivateKey();
				String privateKeyHex = Numeric.toHexStringWithPrefix(privateKey);
				String publicKeystring = publicKey.toString();

				Credentials credentials = Credentials.create(privateKeyHex);
				log.info("address: '" + address + "'\n" + credentials.getAddress());
				log.info("public key address :" + publicKey);
				toaccountsrepository
						.save(new ToAccounts(u + 1, credentials.getAddress(), publicKeystring, publicKeyHex));

			}

		} catch (Exception e) {
			log.info("Error" + e);
		}

	}

	// @RequestMapping(value="/initialfunding", method =RequestMethod.GET)
	@Scheduled(cron = "0 0 */6 ? * *")
	public void initialfunding() throws InterruptedException, ExecutionException {
		try {
			for (long t = 0; t < 200;) {
				String addr = fromaccountsrepository.findAddress(t  + 1);

				EthGetTransactionCount ethGetTransactionCount;
				ethGetTransactionCount = web3j.ethGetTransactionCount("0xec7a8FD9BC25fc3d064de3Cd2D0503d258649a06",
						DefaultBlockParameterName.LATEST).sendAsync().get();
				BigInteger nonce1 = ethGetTransactionCount.getTransactionCount();
				RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce1, BigInteger.valueOf(100),
						BigInteger.valueOf(2100000), addr, BigInteger.valueOf(1000000000000000000L));

				byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentialOfSender);
				String hexValue = Numeric.toHexString(signedMessage);
				EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
				String transactionHash = ethSendTransaction.getTransactionHash();
				if (transactionHash != null) {
					log.info("Initial Funding- Transaction Hash - " + transactionHash);
					t++;
				}
			}
		} catch (Exception e) {
			log.info("Error" + e);
		}
	}

	public void sendEthers() throws InterruptedException, ExecutionException {
		try {

			String addr = fromaccountsrepository.findAddress((fromAccountscount % 200) + 1);

			EthGetTransactionCount ethGetTransactionCount;
			ethGetTransactionCount = web3j.ethGetTransactionCount("0xec7a8FD9BC25fc3d064de3Cd2D0503d258649a06",
					DefaultBlockParameterName.LATEST).sendAsync().get();
			BigInteger nonce1 = ethGetTransactionCount.getTransactionCount();
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce1, BigInteger.valueOf(100),
					BigInteger.valueOf(2100000), addr, BigInteger.valueOf(1000000000000000000L));

			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentialOfSender);
			String hexValue = Numeric.toHexString(signedMessage);
			EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
			String transactionHash = ethSendTransaction.getTransactionHash();
			if (transactionHash != null) {
				log.info("Primary Funding- Transaction Hash - " + transactionHash);
				fromAccountscount++;
			}

		} catch (Exception e) {
			log.info("Error" + e);
		}
	}

	long b = 0;

	// @RequestMapping(value = "/trading", method = RequestMethod.GET)
	// @Scheduled(fixedDelay=1000)
	public void sendEthersbetween() throws Exception {

		
		Random r = new Random();
		while (true) {
			try {
				Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
				
				System.out.print(web3ClientVersion.getWeb3ClientVersion());
				
//				String toAdress = toaccountsrepository.findAddress((b % 100) + 1); // 100
				String fromAdress = fromaccountsrepository.findPrivate((b % 200) + 1); // 200

				
				byte[] reci = Hash.sha3(UUID.randomUUID().toString().getBytes());
				ECKeyPair keys = ECKeyPair.create(reci);
				String address = Keys.getAddress(keys).toString();
				BigInteger publicKey = keys.getPublicKey();
				String publicKeyHex = Numeric.toHexStringWithPrefix(publicKey);
				BigInteger privateKey = keys.getPrivateKey();
				String privateKeyHex = Numeric.toHexStringWithPrefix(privateKey);
				String publicKeystring = publicKey.toString();
				Credentials credentials = Credentials.create(privateKeyHex);
				String toAdress = credentials.getAddress();
	            log.info("Account Created" + credentials.getAddress());
				long amt=4000+r.nextInt(6000);
				Credentials credentialOfRoot = Credentials.create(fromAdress);
				CompletableFuture<TransactionReceipt> transactionReceipt = Transfer
						.sendFunds(web3j, credentialOfRoot, toAdress, BigDecimal.valueOf(amt), Convert.Unit.WEI)
						.sendAsync();
				log.info("Secondary Funding- Transaction Hash - " + b +"\n value :"+amt);
				TimeUnit.SECONDS.sleep(1);
				// TimeUnit.SECONDS.sleep(60);
				b++;
				/*if (b % 2 == 0) {
					TimeUnit.SECONDS.sleep(2);
				}*/
			} catch (Exception e) {
				log.error("Exception in sendEthersbetween  :" + e);
			}

		}
	}
	
	
	@RequestMapping(value = "/web3", method = RequestMethod.GET)
	void testweb3() throws IOException {
		try{
			Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
		
		System.out.print(web3ClientVersion.getWeb3ClientVersion());
		}
		catch(Exception e)
		{
			log.info("The error: "+e);
		}
		
		
	}
	


}
