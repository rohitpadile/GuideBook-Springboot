package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.CollegeClub;
import com.guidebook.GuideBook.Repository.CollegeClubRepository;
import com.guidebook.GuideBook.Repository.cutomrepository.CustomCollegeClubRepositoryImpl;
import com.guidebook.GuideBook.dtos.GetClubListForCollegeResponse;
import com.guidebook.GuideBook.exceptions.CollegeClubNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CollegeClubService {
    private CollegeClubRepository collegeClubRepository;
    private CustomCollegeClubRepositoryImpl customCollegeClubRepositoryImpl;
    @Autowired
    public CollegeClubService(CollegeClubRepository collegeClubRepository,
                              CustomCollegeClubRepositoryImpl customCollegeClubRepositoryImpl) {
        this.collegeClubRepository = collegeClubRepository;
        this.customCollegeClubRepositoryImpl = customCollegeClubRepositoryImpl;
    }


    public GetClubListForCollegeResponse getCollegeClubsForCollege(String collegeName)
            throws CollegeClubNotFoundException {
        GetClubListForCollegeResponse response = new GetClubListForCollegeResponse();

        for(CollegeClub club :
                customCollegeClubRepositoryImpl.findClubByCollegeNameIgnoreCase(collegeName)){
            response.getCollegeClubNameList().add(
                    club.getCollegeClubCollegeName()
            );
        }
        return response;
    }
}
