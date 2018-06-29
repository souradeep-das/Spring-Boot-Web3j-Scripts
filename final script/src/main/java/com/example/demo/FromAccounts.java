package com.example.demo;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FromAccounts {
	@Id
	private Long id;
	private String Address;
	private String Public;
	private String Private;
	public FromAccounts() {
		super();
	}
	public FromAccounts(String address, String public1, String private1) {
		super();
		Address = address;
		Public = public1;
		Private = private1;
	}
	public FromAccounts(Long id, String address, String public1, String private1) {
		super();
		this.id = id;
		Address = address;
		Public = public1;
		Private = private1;
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
	public String getPublic() {
		return Public;
	}
	public void setPublic(String public1) {
		Public = public1;
	}
	public String getPrivate() {
		return Private;
	}
	public void setPrivate(String private1) {
		Private = private1;
	}
	
}
