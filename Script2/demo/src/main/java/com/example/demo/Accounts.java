package com.example.demo;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Accounts {
	@Id
	@GeneratedValue
	private Long id;
	private String Address;
	private String Fname;
	public Accounts() {
		super();
	}
	public Accounts(String address, String fname) {
		super();
		Address = address;
		Fname = fname;
	}
	public Accounts(Long id, String address, String fname) {
		super();
		this.id = id;
		Address = address;
		Fname = fname;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		Fname = fname;
	}
	
}
