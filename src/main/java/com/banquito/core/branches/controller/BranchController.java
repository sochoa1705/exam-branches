package com.banquito.core.branches.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.branches.controller.dto.BranchRQRS;
import com.banquito.core.branches.controller.mapper.BranchMapper;
import com.banquito.core.branches.exception.CRUDException;
import com.banquito.core.branches.model.Branch;
import com.banquito.core.branches.service.BranchService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/branches")
@Slf4j
public class BranchController {
    
    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<BranchRQRS>> obtainAll() {
        log.info("Going to return all branches");
        List<Branch> branches = this.branchService.getAll();
        log.info("Returning {} branches", branches.size());
        return ResponseEntity.ok(BranchMapper.mapToList(branches)); 
    }

    @GetMapping("/{code}")
    public ResponseEntity<BranchRQRS> obtainByCode(@PathVariable(name = "code") String code) {
        log.info("Going to find branch by code: {}", code);
        Branch branch = this.branchService.lookByCode(code);
        if (branch!=null) {
            return ResponseEntity.ok(BranchMapper.mapToBranchRQRS(branch));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody BranchRQRS branch) {
        try {
            log.info("Going to create a branch with info: {}", branch);
            this.branchService.create(BranchMapper.mapToBranch(branch));
            return ResponseEntity.ok().build();
        } catch (CRUDException e) {
            log.error("Error at create branch: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{code}")
    public  ResponseEntity<BranchRQRS> update(@PathVariable(name="code") String code, @RequestBody BranchRQRS branch) {
        try {
            this.branchService.update(code, BranchMapper.mapToBranch(branch));
            return ResponseEntity.ok(BranchMapper.mapToBranchRQRS(this.branchService.lookByCode(code)));
        } catch (CRUDException e){
            log.error("Error at update branch: {}", e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).build();
        }
    }
    
}
