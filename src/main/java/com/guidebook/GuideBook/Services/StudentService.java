package com.guidebook.GuideBook.Services;



import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Repository.StudentRepository;
import com.guidebook.GuideBook.Repository.cutomrepository.CustomStudentRepositoryImpl;
import com.guidebook.GuideBook.dtos.AddStudentRequest;
import com.guidebook.GuideBook.dtos.FilteredStudentListRequest;
import com.guidebook.GuideBook.enums.LanguageEnum;
import com.guidebook.GuideBook.exceptions.BranchNotFoundException;
import com.guidebook.GuideBook.exceptions.CollegeNotFoundException;
import com.guidebook.GuideBook.mapper.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

@Service
public class StudentService {
    private LanguageService languageService;
    private CollegeService collegeService;
    private BranchService branchService;
    private StudentRepository studentRepository;
    private CustomStudentRepositoryImpl customStudentRepository;

    @Autowired
    public StudentService(LanguageService languageService,BranchService branchService,StudentRepository studentRepository,CollegeService collegeService, CustomStudentRepositoryImpl customStudentRepository) {
        this.studentRepository = studentRepository;
        this.customStudentRepository = customStudentRepository;
        this.collegeService = collegeService;
        this.branchService = branchService;
        this.languageService = languageService;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> filteredStudentListRequest(FilteredStudentListRequest filteredStudentListRequest){
        return customStudentRepository.findStudentsByFilters(filteredStudentListRequest);
    }

    public Student addStudent(AddStudentRequest addStudentRequest) throws CollegeNotFoundException, BranchNotFoundException {
        Student newStudent = StudentMapper.mapToStudent(addStudentRequest);

        if((collegeService.findCollegeByCollegeName(addStudentRequest.getStudentCollegeName())) == null){
            throw new CollegeNotFoundException("College not found: " + addStudentRequest.getStudentCollegeName());
            //Throw custom CollegeNotFound Exception here - after charging plugged laptop.
        } else {
            newStudent.setStudentCollege(collegeService.findCollegeByCollegeName(addStudentRequest.getStudentCollegeName()));
        }

        if((branchService.getBranchByName(addStudentRequest.getStudentBranchName())) == null){
            throw new BranchNotFoundException("Branch not found: " + addStudentRequest.getStudentBranchName());
            //Throw custom BranchNotFoundException
        } else {
            newStudent.setStudentBranch(branchService.getBranchByName(addStudentRequest.getStudentBranchName()));
        }

        List<Language> languageList = new ArrayList<>();
        for (String studentLanguageName : addStudentRequest.getStudentLanguageNames()) {
            Language language = languageService.findLanguageByLanguageName(studentLanguageName);
            if (language == null) {
                // Language does not exist, create a new one
                language = new Language();
                language.setLanguageName(studentLanguageName.toUpperCase());
                language = languageService.addLanguage(language); // Save the new language to database
            }
            languageList.add(language); //add the language to the studentLanguageList
        }

        newStudent.setStudentLanguageList(languageList);
        return studentRepository.save(newStudent);
    }

}

