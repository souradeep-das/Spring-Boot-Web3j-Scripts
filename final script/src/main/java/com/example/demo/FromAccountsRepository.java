package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FromAccountsRepository extends JpaRepository<FromAccounts, Long>{
	
	@Query("SELECT a.Address FROM FromAccounts a where a.id = :id")
	String findAddress(@Param("id") Long id);
	
	@Query("SELECT a.Private FROM FromAccounts a where a.id = :id")
	String findPrivate(@Param("id") Long id);
	

	 List<FromAccounts> findAll() ;
}
