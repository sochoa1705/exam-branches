package com.banquito.core.branches.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.banquito.core.branches.model.Branch;

public interface BranchRepository extends MongoRepository<Branch, String>{
    Branch findByCode(String code);
}

