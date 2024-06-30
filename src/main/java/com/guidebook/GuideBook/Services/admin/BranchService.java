package com.guidebook.GuideBook.Services.admin;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Repository.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {

    private BranchRepository branchRepository;
    @Autowired
    private StudentService studentService;

    @Autowired
    BranchService(BranchRepository branchRepository){
        this.branchRepository = branchRepository;
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch addBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public List<Branch> addBranchList(List<Branch> branchList){
        return branchRepository.saveAll(branchList);
    }

    public Branch updateBranch(Long branchId, Branch branchDetails) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));

        if(branchDetails.getBranchName() != null){
            branch.setBranchName(branchDetails.getBranchName());
        }
        if(branchDetails.getBranchStudentList() != null){
            branch.setBranchStudentList(branchDetails.getBranchStudentList());
        }
        return branchRepository.save(branch);
    }

    public void deleteBranch(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));
        branchRepository.delete(branch);
    }

    public Branch addStudentToBranch(Long branchid, Long studentId){
        Branch branch = branchRepository.findById(branchid)
                .orElseThrow(() -> new EntityNotFoundException("Not found branch with id: " + branchid));
        //You can throw custom Exceptions also.
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        //Now both branch and student is with us.
        List<Student> studentList = branch.getBranchStudentList();
        studentList.add(student);
        return branchRepository.save(branch);
    }
}