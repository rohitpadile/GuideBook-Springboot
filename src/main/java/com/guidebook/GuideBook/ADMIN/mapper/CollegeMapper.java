package com.guidebook.GuideBook.ADMIN.mapper;

import com.guidebook.GuideBook.ADMIN.dtos.AddCollegeRequest;
import com.guidebook.GuideBook.ADMIN.Models.College;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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
