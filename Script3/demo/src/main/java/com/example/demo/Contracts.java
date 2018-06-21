package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Contracts {
	@Id
	private Long id;
	String Address;
	public Contracts() {
		super();
	}
	public Contracts(String address) {
		super();
		Address = address;
	}
	public Contracts(Long id, String address) {
		super();
		this.id = id;
		Address = address;
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
	
}
