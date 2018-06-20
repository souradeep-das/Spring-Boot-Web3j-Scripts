package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Accountall2Repository extends JpaRepository<Accountall2, Long>{
	
	@Query("SELECT a.Address FROM Accountall2 a where a.id = :id")
	String findAddress(@Param("id") Long id);
}
