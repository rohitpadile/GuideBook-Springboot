package com.guidebook.GuideBook.mapper;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.dtos.AddBranchRequest;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;

@Data
@UtilityClass
@Builder
public class BranchMapper {
    public static Branch mapToBranch(AddBranchRequest request){
        return Branch.builder()
                .branchName(request.getBranchName())
                .build();
    }
}
