package com.Smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Smart.entites.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.email = :email")
	public User getUserDetails( String email) ;
		
	

}
