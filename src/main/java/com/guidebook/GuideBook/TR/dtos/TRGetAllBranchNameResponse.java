package com.guidebook.GuideBook.TR.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TRGetAllBranchNameResponse {
    List<String> allBranches = new ArrayList<>();
}
