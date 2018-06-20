package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import jnr.constants.platform.darwin.LangInfo;
import rx.Subscription;

@RestController
public class democontroller {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	String home() {
		return "Hello World!";

	}

	@Autowired
	Demoservice demoservice;

	@RequestMapping(value = "/check", method = RequestMethod.GET)
	String getVersion() throws IOException {
		return demoservice.getClientVersion();
	}

	// @Autowired
	 //Web3j web3j;

	private Web3j web3j = Web3j.build(new HttpService("http://206.189.229.33:8545"));

	@RequestMapping(value = "/check1", method = RequestMethod.GET)
	public BigInteger getbal() throws InterruptedException, ExecutionException {
		EthGetBalance ethGetBalance = web3j
				.ethGetBalance("0xec7a8FD9BC25fc3d064de3Cd2D0503d258649a06", DefaultBlockParameterName.LATEST)
				.sendAsync().get();
		System.out.print("hello!!!!!!!!!!!");
		BigInteger wei = ethGetBalance.getBalance();
		return wei;

	}

	final static Logger log = LoggerFactory.getLogger(democontroller.class);

	@RequestMapping(value = "/check3", method = RequestMethod.GET)
	public void showblocks() {

		Subscription subscription = web3j.blockObservable(false).subscribe(block -> {

			log.info("Sweet, block number \n" + block.getBlock().getNumber() + " has just been created\n");
		});

	}

	@Autowired
	TransactionRepository repository;

	@RequestMapping(value = "/check4", method = RequestMethod.GET)
	public void getpending() {
		Subscription subscription = web3j.pendingTransactionObservable().subscribe(tx -> {
			log.info("\nThe pending transaction\n from:  " + tx.getFrom() + "\nto:  " + tx.getTo()
					+ "\nTransactionHash:  " + tx.getHash());
			repository.save(new Transactions(tx.getFrom(), tx.getTo(), tx.getHash()));

		});

	}

	@RequestMapping(value = "/get2", method = RequestMethod.GET)
	public void block() throws InterruptedException {

		Subscription subscription = web3j
				.catchUpToLatestBlockObservable(new DefaultBlockParameterNumber(BigInteger.valueOf(500)), true)
				.subscribe(ethBlock -> {

					EthBlock.Block block = ethBlock.getBlock();
					log.info("\n " + "Number Of the trx: " + block.getTransactions().size() + " in block "
							+ block.getNumber());

				});

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public void createacc() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchProviderException, CipherException, IOException {
		String filePath = "/home/auxesis/ethnet/keystore";
		String fileName = WalletUtils.generateNewWalletFile("a", new File(filePath), false);
		System.out.print(fileName + '\n');
		Credentials credentials = WalletUtils.loadCredentials("a", filePath + "/" + fileName);
		Credentials credentials2 = credentials.create("dsd");
		credentials.getAddress();
		System.out.print(credentials.getAddress());

	}

	@Autowired
	AccountallRepository brepository;

	private String senderPrivKey = "8def6abc00c429f80b6b8911d62e97b53a2da2f86e537bedd56c508b09d576f2";
	Credentials credentialOfSender = Credentials.create(senderPrivKey);

	@RequestMapping(value = "/fromAddress", method = RequestMethod.GET)
	public void getCredentials() {
		System.out.println("################################ getCredentials");

		for (long u = 0; u < 20; u++) {
			byte[] reci = Hash.sha3(UUID.randomUUID().toString().getBytes());
			ECKeyPair keys = ECKeyPair.create(reci);
			String address = Keys.getAddress(keys).toString();
			BigInteger publicKey = keys.getPublicKey();
			String publicKeyHex = Numeric.toHexStringWithPrefix(publicKey);
			BigInteger privateKey = keys.getPrivateKey();
			String privateKeyHex = Numeric.toHexStringWithPrefix(privateKey);
			String publicKeystring = publicKey.toString();

			Credentials credentials = Credentials.create(privateKeyHex);
			System.out.println("address: '" + address + "'\n" + credentials.getAddress());
			System.out.println("private key address :" + publicKeyHex);
			System.out.println("public key address :" + publicKey);
		brepository.save(new Accountall(u + 1, credentials.getAddress(), publicKeyHex,privateKeyHex ));

		}

	}

	@Autowired
	Accountall2Repository crepository;

	@RequestMapping(value = "/toAddress", method = RequestMethod.GET)
	public void getCredentials100() {
		System.out.println("################################ getCredentials");

		for (long u = 0; u < 10; u++) {
			byte[] reci = Hash.sha3(UUID.randomUUID().toString().getBytes());
			ECKeyPair keys = ECKeyPair.create(reci);
			String address = Keys.getAddress(keys).toString();
			BigInteger publicKey = keys.getPublicKey();
			String publicKeyHex = Numeric.toHexStringWithPrefix(publicKey);
			BigInteger privateKey = keys.getPrivateKey();
			String privateKeyHex = Numeric.toHexStringWithPrefix(privateKey);
			String publicKeystring = publicKey.toString();

			Credentials credentials = Credentials.create(privateKeyHex);
			System.out.println("address: '" + address + "'\n" + credentials.getAddress());
			System.out.println("private key address :" + publicKeyHex);
			System.out.println("public key address :" + publicKey);
			crepository.save(new Accountall2(u + 1, credentials.getAddress(), publicKeystring, publicKeyHex));

		}

	}
	
	long lnum=1;
	Random r2=new Random();

	//@RequestMapping(value = "/primaryfunding", method = RequestMethod.GET)
	@Scheduled(fixedRate=3000)
	public void sendEthers() throws InterruptedException, ExecutionException {
		
			String addr = brepository.findAddress((lnum%20)+81);

			EthGetTransactionCount ethGetTransactionCount;
			ethGetTransactionCount = web3j.ethGetTransactionCount("0xec7a8FD9BC25fc3d064de3Cd2D0503d258649a06",
					DefaultBlockParameterName.LATEST).sendAsync().get();
			BigInteger nonce1 = ethGetTransactionCount.getTransactionCount();
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce1, BigInteger.valueOf(100),
					BigInteger.valueOf(2100000), addr, BigInteger.valueOf(500000000000000000L));

			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentialOfSender);
			String hexValue = Numeric.toHexString(signedMessage);
			EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
			String transactionHash = ethSendTransaction.getTransactionHash();
			if (transactionHash != null) {
				System.out.println("Primary Funding- Transaction Hash - " + transactionHash);
				lnum++;
			}

		}
	

	long b = 1;

	//@RequestMapping(value = "/trading", method = RequestMethod.GET)
	@Scheduled(fixedRate=1000)
	public void sendEthersbetween() throws Exception {

		
	
		

			String toAdress = crepository.findAddress((b%10)+1); // 100
			String fromAdress = brepository.findPrivate((b%10)+81); // 200
			
			Credentials credentialOfRoot = Credentials.create(fromAdress);
			TransactionReceipt transactionReceipt = Transfer
					.sendFunds(web3j, credentialOfRoot, toAdress, BigDecimal.valueOf(5000), Convert.Unit.WEI)
					.send();
			System.out.println("Secondary Funding- Transaction Hash - "
					+ transactionReceipt.getTransactionHash());

			b++;

		

	}

	@Autowired
	AccountRepository arepository;

	int y = 0;
	long e = 0;
	long f = 0;

	Credentials credentials;

	@RequestMapping(value = "/createdb10", method = RequestMethod.GET)
	public void createdb10() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchProviderException, CipherException, IOException {
		for (y = 0; y < 10; y++) {
			String filePath = "/home/auxesis/privatenet/keystore";
			String fileName = WalletUtils.generateNewWalletFile("a", new File(filePath), false);
			Credentials credentials = WalletUtils.loadCredentials("a", filePath + "/" + fileName);
			System.out.println(credentials.getAddress());
			arepository.save(new Accounts(credentials.getAddress(), fileName));
		}

		Subscription subscription = web3j.blockObservable(false).subscribe(block -> {

			Random r = new Random();

			BigInteger currentnum2 = block.getBlock().getNumber();
			if (currentnum2.mod(new BigInteger("5")).equals(BigInteger.ZERO)) {

				EthGetTransactionCount ethGetTransactionCount;
				try {
					ethGetTransactionCount = web3j.ethGetTransactionCount("0x84156d0a4889c8e66adc01f64fcf8b0690fa44d0",
							DefaultBlockParameterName.LATEST).sendAsync().get();

					BigInteger nonce1 = ethGetTransactionCount.getTransactionCount();
					String filePath = "/home/auxesis/privatenet/keystore";
					RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce1,
							BigInteger.valueOf(100), BigInteger.valueOf(2100000), arepository.findAddress((f % 10) + 1),
							BigInteger.valueOf(500000000000000L));
					f++;
					byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,
							WalletUtils.loadCredentials("a", filePath
									+ "/UTC--2018-06-11T08-07-21.157276992Z--84156d0a4889c8e66adc01f64fcf8b0690fa44d0"));
					String hexValue = Numeric.toHexString(signedMessage);
					EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
					String transactionHash = ethSendTransaction.getTransactionHash();
					System.out.println("Fueled one of the ten root accounts- Transaction Hash - " + transactionHash);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CipherException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			// BigInteger tempB=arepository.getBalance(Long.valueOf((f-1)%10));
			// arepository.save(new
			// Accounts(arepository.findAddress(Long.valueOf((f-1)%10)),tempB.add(BigInteger.valueOf(500000000000000L)),arepository.findFile(Long.valueOf((f-1)%10))));

			BigInteger currentnum = block.getBlock().getNumber();

			if (flag == 0) {
				num = r.nextInt(5) + 5 + currentnum.intValue();
				flag = 1;
			}
			if (currentnum.equals(BigInteger.valueOf(num))) {
				flag = 0;
				System.out.print("\nBlock number" + block.getBlock().getNumber() + " has just been created\n");

				try {

					int numofacc = r.nextInt(3) + 1;
					String filePath = "/home/auxesis/privatenet/keystore";
					for (j = 0; j < numofacc; j++) {
						String fileName = WalletUtils.generateNewWalletFile("a", new File(filePath), false);
						Credentials credentials = WalletUtils.loadCredentials("a", filePath + "/" + fileName);
						System.out.print("Account Created - " + credentials.getAddress());

						EthGetTransactionCount ethGetTransactionCount = web3j
								.ethGetTransactionCount("0x84156d0a4889c8e66adc01f64fcf8b0690fa44d0",
										DefaultBlockParameterName.LATEST)
								.sendAsync().get();
						BigInteger nonce = ethGetTransactionCount.getTransactionCount();
						nonce.add(BigInteger.valueOf(i));
						String sendadd = credentials.getAddress();

						RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,
								BigInteger.valueOf(1500000), BigInteger.valueOf(2100000), sendadd,
								BigInteger.valueOf(500000000));
						byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,
								WalletUtils.loadCredentials("a", filePath + "/" + arepository.findFile((e % 10) + 1)));
						String hexValue = Numeric.toHexString(signedMessage);
						e++;
						EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
						String transactionHash = ethSendTransaction.getTransactionHash();
						if (transactionHash == null) {
							i++;
						}

						System.out.println(
								"\nEthers Transferred to the new Account - Transaction Hash - " + transactionHash);
					}

				} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
						| CipherException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	/*
	 * @Autowired AccountRepository repository;
	 * 
	 * @Autowired Accounts account;
	 * 
	 * 
	 * long h=0L;
	 * 
	 * @RequestMapping(value = "/createdb", method = RequestMethod.GET) public void
	 * createdbacc() throws InvalidAlgorithmParameterException,
	 * NoSuchAlgorithmException, NoSuchProviderException, CipherException,
	 * IOException, InterruptedException, ExecutionException { for(int y=0;y<10;y++)
	 * { String filePath = "/home/auxesis/ethnet/keystore";
	 * 
	 * String fileName = WalletUtils.generateNewWalletFile("a",new
	 * File(filePath),false); Credentials credentials =
	 * WalletUtils.loadCredentials("a", filePath+"/"+fileName); EthGetBalance
	 * ethGetBalance = web3j .ethGetBalance(credentials.getAddress(),
	 * DefaultBlockParameterName.LATEST) .sendAsync().get(); BigInteger wei =
	 * ethGetBalance.getBalance(); repository.save(new
	 * Accounts(credentials.getAddress(),wei));
	 * 
	 * } Subscription subscription = web3j.blockObservable(false) .subscribe(block
	 * -> { if(block.getBlock().getNumber().mod(new
	 * BigInteger("5")).equals(BigInteger.ZERO)) { EthGetTransactionCount
	 * ethGetTransactionCount; try { ethGetTransactionCount = web3j
	 * .ethGetTransactionCount("0xa86bb9c3cf1b7b82aeae4fb9c51fcac59884d95e",
	 * DefaultBlockParameterName.LATEST) .sendAsync() .get(); BigInteger nonce =
	 * ethGetTransactionCount.getTransactionCount();
	 * nonce.add(BigInteger.valueOf(i)); System.out.print(nonce+"\n");
	 * 
	 * String filePath = "/home/auxesis/ethnet/keystore"; RawTransaction
	 * rawTransaction =
	 * RawTransaction.createEtherTransaction(nonce,BigInteger.valueOf(100),
	 * BigInteger.valueOf(2100000),account.getAccountAddress(),BigInteger.valueOf(
	 * 5000000)); h++; byte[] signedMessage =
	 * TransactionEncoder.signMessage(rawTransaction,WalletUtils.loadCredentials(
	 * "a", filePath+
	 * "/UTC--2018-06-02T06-20-21.979293034Z--a86bb9c3cf1b7b82aeae4fb9c51fcac59884d95e"
	 * )); String hexValue = Numeric.toHexString(signedMessage); EthSendTransaction
	 * ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
	 * String transactionHash = ethSendTransaction.getTransactionHash();
	 * if(transactionHash == null) { i++; } System.out.print(i+"\n");
	 * 
	 * System.out.println(transactionHash); } catch (InterruptedException |
	 * ExecutionException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } catch (CipherException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * } }); }
	 */

	int p = 0;
	int q = 0;

	ArrayList<String> addarr = new ArrayList<String>();
	ArrayList<String> filearr = new ArrayList<String>();

	@RequestMapping(value = "/100", method = RequestMethod.GET)
	public void create100() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchProviderException, CipherException, IOException {

		for (int k = 0; k < 10; k++) {
			String filePath = "/home/auxesis/privatenet/keystore";
			String fileName = WalletUtils.generateNewWalletFile("a", new File(filePath), false);
			System.out.print(fileName + "\n");
			filearr.add(fileName);
			Credentials credentials = WalletUtils.loadCredentials("a", filePath + "/" + fileName);
			addarr.add(credentials.getAddress());
		}

		System.out.println("The Addresses Created");
		for (int o = 0; o < 10; o++)
			System.out.println(addarr.get(o));

		Subscription subscription = web3j.blockObservable(false).subscribe(block -> {

			Random r = new Random();

			BigInteger currentnum2 = block.getBlock().getNumber();
			if (currentnum2.mod(new BigInteger("5")).equals(BigInteger.ZERO)) {

				EthGetTransactionCount ethGetTransactionCount;
				try {
					ethGetTransactionCount = web3j.ethGetTransactionCount("0x84156d0a4889c8e66adc01f64fcf8b0690fa44d0",
							DefaultBlockParameterName.LATEST).sendAsync().get();

					BigInteger nonce1 = ethGetTransactionCount.getTransactionCount();
					String filePath = "/home/auxesis/privatenet/keystore";
					RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce1,
							BigInteger.valueOf(100), BigInteger.valueOf(2100000), addarr.get(q % 10),
							BigInteger.valueOf(500000000000000L));
					q++;
					byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,
							WalletUtils.loadCredentials("a", filePath
									+ "/UTC--2018-06-11T08-07-21.157276992Z--84156d0a4889c8e66adc01f64fcf8b0690fa44d0"));
					String hexValue = Numeric.toHexString(signedMessage);
					EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
					String transactionHash = ethSendTransaction.getTransactionHash();
					System.out.println("Fueled one of the ten root accounts- Transaction Hash - " + transactionHash);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CipherException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			BigInteger currentnum = block.getBlock().getNumber();

			if (flag == 0) {
				num = r.nextInt(5) + 5 + currentnum.intValue();
				flag = 1;
			}
			if (currentnum.equals(BigInteger.valueOf(num))) {
				flag = 0;
				System.out.print("\nBlock number" + block.getBlock().getNumber() + " has just been created\n");

				try {

					int numofacc = r.nextInt(3) + 1;

					String filePath = "/home/auxesis/privatenet/keystore";
					for (j = 0; j < numofacc; j++) {
						String fileName = WalletUtils.generateNewWalletFile("a", new File(filePath), false);
						Credentials credentials = WalletUtils.loadCredentials("a", filePath + "/" + fileName);
						System.out.print("Account Created - " + credentials.getAddress());

						EthGetTransactionCount ethGetTransactionCount = web3j
								.ethGetTransactionCount("0x84156d0a4889c8e66adc01f64fcf8b0690fa44d0",
										DefaultBlockParameterName.LATEST)
								.sendAsync().get();
						BigInteger nonce = ethGetTransactionCount.getTransactionCount();
						nonce.add(BigInteger.valueOf(i));
						String sendadd = credentials.getAddress();

						RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,
								BigInteger.valueOf(1500000), BigInteger.valueOf(2100000), sendadd,
								BigInteger.valueOf(500000000));
						byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,
								WalletUtils.loadCredentials("a", filePath + "/" + filearr.get(p % 10)));
						String hexValue = Numeric.toHexString(signedMessage);
						p++;
						EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
						String transactionHash = ethSendTransaction.getTransactionHash();
						if (transactionHash == null) {
							i++;
						}

						System.out.println(
								"\nEthers Transferred to the new Account - Transaction Hash - " + transactionHash);
					}

				} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
						| CipherException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

	}

	int i = 0;

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public void sendeth() throws IOException, CipherException, InterruptedException, ExecutionException {

		EthGetTransactionCount ethGetTransactionCount = web3j
				.ethGetTransactionCount("0xa86bb9c3cf1b7b82aeae4fb9c51fcac59884d95e", DefaultBlockParameterName.LATEST)
				.sendAsync().get();
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		nonce.add(BigInteger.valueOf(i));
		System.out.print(nonce + "\n");

		String filePath = "/home/auxesis/ethnet/keystore";
		RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, BigInteger.valueOf(100),
				BigInteger.valueOf(2100000), "0x35c800f52dd95352341e52f78572ca17ff5d512f", BigInteger.valueOf(5000000));
		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, WalletUtils.loadCredentials("a",
				filePath + "/UTC--2018-06-02T06-20-21.979293034Z--a86bb9c3cf1b7b82aeae4fb9c51fcac59884d95e"));
		String hexValue = Numeric.toHexString(signedMessage);
		EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
		String transactionHash = ethSendTransaction.getTransactionHash();
		if (transactionHash == null) {
			i++;
		}
		System.out.print(i + "\n");

		System.out.println(transactionHash);

	}

	int flag = 0;
	int num = 0;
	int j;

	@RequestMapping(value = "/script", method = RequestMethod.GET)
	public void runscri() {

		Subscription subscription = web3j.blockObservable(false).subscribe(block -> {

			Random r = new Random();

			BigInteger currentnum = block.getBlock().getNumber();

			if (flag == 0) {
				num = r.nextInt(5) + 5 + currentnum.intValue();
				flag = 1;
			}
			// String randnum = Integer.toString(num);
			if (currentnum.equals(BigInteger.valueOf(num))) {
				flag = 0;
				System.out.print("\nBlock number" + block.getBlock().getNumber() + " has just been created\n");

				try {

					int numofacc = r.nextInt(3) + 1;

					String filePath = "/home/auxesis/ethnet/keystore";
					for (j = 0; j < numofacc; j++) {
						String fileName = WalletUtils.generateNewWalletFile("a", new File(filePath), false);
						Credentials credentials = WalletUtils.loadCredentials("a", filePath + "/" + fileName);
						System.out.print("Account Created - " + credentials.getAddress());

						EthGetTransactionCount ethGetTransactionCount = web3j
								.ethGetTransactionCount("0xa86bb9c3cf1b7b82aeae4fb9c51fcac59884d95e",
										DefaultBlockParameterName.LATEST)
								.sendAsync().get();
						BigInteger nonce = ethGetTransactionCount.getTransactionCount();
						nonce.add(BigInteger.valueOf(i));
						String sendadd = credentials.getAddress();

						RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,
								BigInteger.valueOf(1500000), BigInteger.valueOf(2100000), sendadd,
								BigInteger.valueOf(500000000));
						byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction,
								WalletUtils.loadCredentials("a", filePath
										+ "/UTC--2018-06-02T06-20-21.979293034Z--a86bb9c3cf1b7b82aeae4fb9c51fcac59884d95e"));
						String hexValue = Numeric.toHexString(signedMessage);
						EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
						String transactionHash = ethSendTransaction.getTransactionHash();
						if (transactionHash == null) {
							i++;
						}

						System.out.println("\nEthers Transferred- Transaction Hash - " + transactionHash);
					}

				} catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException
						| CipherException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public void getalltran() throws IOException {
		EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
		String latestBlockNumber = ethBlock.getBlock().getTransactionsRoot();
		System.out.print("latestBlockNumber: " + latestBlockNumber);

	}

}
