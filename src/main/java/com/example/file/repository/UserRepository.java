package com.example.file.repository;

import com.example.file.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<Users, Long>, QuerydslPredicateExecutor<Users> {

}
