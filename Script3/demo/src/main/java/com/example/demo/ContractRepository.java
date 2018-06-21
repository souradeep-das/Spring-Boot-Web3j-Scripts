package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Long>{
	
	@Query("SELECT a.Address from Contracts a ")
	ArrayList<String> findaddress();
}
