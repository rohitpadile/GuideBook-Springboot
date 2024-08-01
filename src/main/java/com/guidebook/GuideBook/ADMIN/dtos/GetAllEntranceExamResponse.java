package com.guidebook.GuideBook.ADMIN.dtos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Data
public class GetAllEntranceExamResponse {

    Set<String> entranceExamNameSet = new HashSet<>();
}
