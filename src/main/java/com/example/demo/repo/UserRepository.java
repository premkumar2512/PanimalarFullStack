package com.example.demo.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
	User findByEmail(String email);
	
    @Query("SELECT u FROM User u WHERE u.email = :email ORDER BY u.id DESC")
    User findLatestByEmail(@Param("email") String email);
}
