package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Transactions {
	@Id
	@GeneratedValue
	private Long id;
	private String fromAddress;
	private String toAddress;
	private String transactionHash;
	public Transactions() {
		super();
	}
	public Transactions(String fromAddress, String toAddress, String transactionHash) {
		super();
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.transactionHash = transactionHash;
	}
	public Transactions(Long id, String fromAddress, String toAddress, String transactionHash) {
		super();
		this.id = id;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.transactionHash = transactionHash;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getTransactionHash() {
		return transactionHash;
	}
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
	
}
