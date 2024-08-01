package com.guidebook.GuideBook.ADMIN.mapper;

import com.guidebook.GuideBook.ADMIN.Models.Branch;
import com.guidebook.GuideBook.ADMIN.dtos.AddBranchRequest;
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
