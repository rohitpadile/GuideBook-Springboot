package com.guidebook.GuideBook.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public class FilteredStudentListResponse {

    private List<String> studentNameList = new ArrayList<>();
    //LATER I WANT BRANCH TO BE WRITTEN BELOW THE STUDENT - BUT THAT WILL BE DONKEY CASE
    //BECAUSE ALREADY THE CLIENT FILTERING OUT THE STUDENTS BASED ON BRANCH AND OTHER PROPERTIES RIGHT!
    //LET'S SEE WHICH LOOKS GOOD
    //FOR NOW NAMES OF STUDENT ARE ENOUGH PROVIDING A LOT OF INFO ABOUT THEM ON THE PAGE
}
