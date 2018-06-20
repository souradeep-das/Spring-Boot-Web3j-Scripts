package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountallRepository extends JpaRepository<Accountall, Long>{
	
	@Query("SELECT a.Address FROM Accountall a where a.id = :id")
	String findAddress(@Param("id") Long id);
	
	@Query("SELECT a.Private FROM Accountall a where a.id = :id")
	String findPrivate(@Param("id") Long id);
	

	 List<Accountall> findAll() ;
}
