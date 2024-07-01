package com.guidebook.GuideBook.mapper;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Services.BranchService;
import com.guidebook.GuideBook.dtos.AddCollegeRequest;
import jakarta.annotation.security.DenyAll;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Builder
@Data
@Slf4j
public class CollegeMapper {

    public static College mapToCollege(AddCollegeRequest request){
                return College.builder()
                .collegeName(request.getCollegeName())
                .build();
    }
}
