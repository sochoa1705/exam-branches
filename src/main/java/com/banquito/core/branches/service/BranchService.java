package com.banquito.core.branches.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.branches.exception.CRUDException;
import com.banquito.core.branches.model.Branch;
import com.banquito.core.branches.repository.BranchRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BranchService {

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public Branch lookById(String id) throws CRUDException {
        log.info("Looking branch with id: {}", id);
        Optional<Branch> branchOpt = this.branchRepository.findById(id);
        if (!branchOpt.isPresent()) {
            throw new CRUDException(404, "Branch with id: {"+id+"} does not exist");
        }
        return branchOpt.get();
    }

    public Branch lookByCode(String code) {
        log.info("looking branch with code {}", code);
        Branch branch = this.branchRepository.findByCode(code);
        log.debug("Branch info for code {} -> {}", code, branch);
        return branch;
    }

    public List<Branch> getAll() {
        log.info("Going to return all braches");
        return this.branchRepository.findAll();
    }

    public void create(Branch branch) throws CRUDException {
        try {
            log.info("Creating branch with code: {}", branch.getCode());
            log.debug("Creating branch with the following info: {}", branch);
            this.branchRepository.save(branch);
        } catch (Exception e) {
            log.error("Error in branch creation: {}, with data: {}", e.getMessage(), branch);
            throw new CRUDException(510, "Branch cannot be created, error:" + e.getMessage(), e);
        }
    }

    public void update(String code, Branch branch) throws CRUDException {
        
        try {
            log.info("Going to update branch with code: {} ", code);
            log.debug("Going to update branch with code: {} with the following data {}", code, branch);
            Branch branchTmp = this.branchRepository.findByCode(code);
            if (branchTmp==null) {
                throw new CRUDException(404, "Branch with code: {"+code+"} does not exist");
            }
            branchTmp.setName(branch.getName());
            this.branchRepository.save(branchTmp);
            log.debug("Branch with id: {} and code: {} has been updated with the following info {}", branchTmp.getId(), branchTmp.getCode(), branchTmp);
        } catch (Exception e) {
            log.error("Error when try to update branch: {}, with the following info: {}", e.getMessage(), branch);
            throw new CRUDException(520, "Branch cannot be updated, error:" + e.getMessage(), e);
        }
    }
}
