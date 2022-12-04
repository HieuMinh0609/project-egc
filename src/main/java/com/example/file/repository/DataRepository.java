package com.example.file.repository;

import com.example.file.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface DataRepository extends JpaRepository<Data, Long>, QuerydslPredicateExecutor<Data> {

    List<Data> findAllByUserId(Long userId);

}
