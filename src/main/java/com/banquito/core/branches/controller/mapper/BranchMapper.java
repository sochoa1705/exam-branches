package com.banquito.core.branches.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import com.banquito.core.branches.controller.dto.BranchRQRS;
import com.banquito.core.branches.model.Branch;

public class BranchMapper {
    
    public static BranchRQRS mapToBranchRQRS(Branch branch) {
        return BranchRQRS.builder()
            .id(branch.getId())
            .code(branch.getCode())
            .name(branch.getName()).build();
    }

    public static List<BranchRQRS> mapToList(List<Branch> branches) {
        List<BranchRQRS> branchesRQRS = new ArrayList<>();
        if (branches!=null) {
            for (Branch branch : branches) {
                branchesRQRS.add(mapToBranchRQRS(branch));
            }
        }
        return branchesRQRS;
    }

    public static Branch mapToBranch(BranchRQRS branchRQRS) {
        Branch branch = new Branch();
        branch.setId(branchRQRS.getId());
        branch.setCode(branchRQRS.getCode());
        branch.setName(branchRQRS.getName());
        return branch;
    }
}
