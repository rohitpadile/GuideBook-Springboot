package com.guidebook.GuideBook.Services;

import com.guidebook.GuideBook.Models.CollegeClub;
import com.guidebook.GuideBook.Repository.CollegeClubRepository;
import com.guidebook.GuideBook.Repository.cutomrepository.CustomCollegeClubRepositoryImpl;
import com.guidebook.GuideBook.dtos.AddCollegeClubRequest;
import com.guidebook.GuideBook.dtos.CollegeClubResponse;
import com.guidebook.GuideBook.dtos.GetClubListForCollegeResponse;
import com.guidebook.GuideBook.exceptions.CollegeClubNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CollegeClubService {
    private CollegeService collegeService;
    private CollegeClubRepository collegeClubRepository;
    private CustomCollegeClubRepositoryImpl customCollegeClubRepositoryImpl;
    @Autowired
    public CollegeClubService(CollegeClubRepository collegeClubRepository,
                              CustomCollegeClubRepositoryImpl customCollegeClubRepositoryImpl,
                              CollegeService collegeService) {
        this.collegeClubRepository = collegeClubRepository;
        this.customCollegeClubRepositoryImpl = customCollegeClubRepositoryImpl;
        this.collegeService = collegeService;
    }


    public GetClubListForCollegeResponse getCollegeClubsForCollege(String collegeName)
            throws CollegeClubNotFoundException {
        GetClubListForCollegeResponse response = new GetClubListForCollegeResponse();

        List<CollegeClub> collegeClubs = customCollegeClubRepositoryImpl.findClubByCollegeNameIgnoreCase(collegeName);
        if (collegeClubs.isEmpty()) {
            throw new CollegeClubNotFoundException("No clubs found for college: " + collegeName);
        }
        for (CollegeClub club : collegeClubs) {
            response.getCollegeClubNameList().add(club.getCollegeClubName());
        }
        return response;
    }

    public CollegeClubResponse addCollegeClub(AddCollegeClubRequest request) {
        CollegeClub newCollegeClub = new CollegeClub();
        newCollegeClub.setCollegeClubName(request.getCollegeClubName());
        newCollegeClub.setCollegeClubDescription(request.getCollegeClubDescription());
        newCollegeClub.setCollegeClubBannerPath(request.getCollegeClubBannerPath());
        newCollegeClub.setCollegeClubCollege(
                collegeService.getCollegeByCollegeNameIgnoreCase(request.getCollegeClubCollegeName())
        );
        return getCollegeClubResponse(collegeClubRepository.save(newCollegeClub));
    }
    private static CollegeClubResponse getCollegeClubResponse(CollegeClub club){
        CollegeClubResponse response = new CollegeClubResponse();
        response.setCollegeClubName(club.getCollegeClubName());
        response.setCollegeClubDescription(club.getCollegeClubDescription());
        response.setCollegeClubBannerPath(club.getCollegeClubBannerPath());
        response.setCollegeClubCollegeName(club.getCollegeClubCollege().getCollegeName());
        return response;
    }
}
