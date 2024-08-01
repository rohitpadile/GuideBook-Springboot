package com.guidebook.GuideBook.TR.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TRGetAllCollegeNameResponse {
    private List<String> allCollegeNames = new ArrayList<>();
}
