package com.example.demo;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long>{

	
	@Query("SELECT a.Address FROM Accounts a where a.id = :id")
	String findAddress(@Param("id") Long id);
	
	@Query("SELECT a.Fname FROM Accounts a where a.id = :id")
	String findFile(@Param("id") Long id);
	
	

}