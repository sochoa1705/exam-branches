package com.banquito.core.branches.service;
import com.banquito.core.branches.exception.CRUDException;
import com.banquito.core.branches.model.Branch;
import com.banquito.core.branches.repository.BranchRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BranchServiceTest {
    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

    @Test
    public void BranchService_lookByCode_returnBranch() {
        String code = "ABC123";
        Branch expectedBranch = new Branch();
        expectedBranch.setCode(code);
        when(branchRepository.findByCode(code)).thenReturn(expectedBranch);

        Branch result = branchService.lookByCode(code);


        assertEquals(expectedBranch, result);
        verify(branchRepository, times(1)).findByCode(code);

    }

    @Test
    public void BranchService_lookByCode_returnNull() {
        String code = "ABC123";
        when(branchRepository.findByCode(code)).thenReturn(null);

        Branch result = branchService.lookByCode(code);

        assertEquals(null, result);
        verify(branchRepository, times(1)).findByCode(code);
    }

    @Test
    public void BranchService_CreateBranch_Void() throws CRUDException {
        Branch branch = new Branch();
        branch.setCode("ABC123");

        branchService.create(branch);

        verify(branchRepository, times(1)).save(branch);
    }

    @Test(expected = CRUDException.class)
    public void BranchService_CreateBranch_ReturnException() throws CRUDException {
        Branch branch = new Branch();
        branch.setCode("XYZ789");

        when(branchRepository.save(branch)).thenThrow(new RuntimeException("Some error occurred."));

        branchService.create(branch);
    }

    @Test
    public void BranchService_UpdateBranch_ExpectedVoid() throws CRUDException {

        String code = "ABC123";
        Branch existingBranch = new Branch();
        existingBranch.setCode(code);

        Branch updatedBranch = new Branch();
        updatedBranch.setCode(code);
        updatedBranch.setName("Updated Name");

        when(branchRepository.findByCode(code)).thenReturn(existingBranch);


        branchService.update(code, updatedBranch);


        assertEquals(updatedBranch.getName(), existingBranch.getName());
        verify(branchRepository, times(1)).findByCode(code);
        verify(branchRepository, times(1)).save(existingBranch);
    }

    @Test(expected = CRUDException.class)
    public void BranchService_Update_BranchNotFound() throws CRUDException {

        String code = "XYZ9";
        Branch branchToUpdate = new Branch();
        branchToUpdate.setCode(code);

        when(branchRepository.findByCode(code)).thenReturn(null);

        branchService.update(code, branchToUpdate);
    }

    @Test(expected = CRUDException.class)
    public void BranchService_UpdateBranch_ReturnException() throws CRUDException {

        String code = "DEF456";
        Branch existingBranch = new Branch();
        existingBranch.setCode(code);

        when(branchRepository.findByCode(code)).thenReturn(existingBranch);
        when(branchRepository.save(existingBranch)).thenThrow(new RuntimeException("Some error occurred."));

        Branch updatedBranch = new Branch();
        updatedBranch.setCode(code);
        updatedBranch.setName("Updated Name");

        branchService.update(code, updatedBranch);
    }

    @Test
    public void testGetAllBranches() {
        // Arrange
        List<Branch> expectedBranches = new ArrayList<>();
        expectedBranches.add(new Branch());
        expectedBranches.add(new Branch());
        expectedBranches.add(new Branch());

        when(branchRepository.findAll()).thenReturn(expectedBranches);

        // Act
        List<Branch> result = branchService.getAll();

        // Assert
        assertEquals(expectedBranches, result);
        verify(branchRepository, times(1)).findAll();
    }

    @Test
    public void BranchService_GetAllBranches_EmptyList() {
        List<Branch> expectedBranches = new ArrayList<>();

        when(branchRepository.findAll()).thenReturn(expectedBranches);


        List<Branch> result = branchService.getAll();

        assertEquals(expectedBranches, result);
        verify(branchRepository, times(1)).findAll();
    }

}
