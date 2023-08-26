package com.banquito.core.branches.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchRQRS {
    
    private String id;
    private String code;
    private String name;

}
