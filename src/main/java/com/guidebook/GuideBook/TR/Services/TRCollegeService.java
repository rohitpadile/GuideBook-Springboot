package com.guidebook.GuideBook.TR.Services;

import com.guidebook.GuideBook.ADMIN.Models.College;
import com.guidebook.GuideBook.ADMIN.Repository.CollegeRepository;
import com.guidebook.GuideBook.TR.dtos.TRGetAllCollegeNameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TRCollegeService {
    private CollegeRepository collegeRepository;
    @Autowired
    public TRCollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }


    public TRGetAllCollegeNameResponse getAllCollegeNameList(){

        List<College> colleges = collegeRepository.findAll();
        TRGetAllCollegeNameResponse response = new TRGetAllCollegeNameResponse();
        for(College clg : colleges){
            response.getAllCollegeNames().add(clg.getCollegeName());
        }
        return response;
    }
}
