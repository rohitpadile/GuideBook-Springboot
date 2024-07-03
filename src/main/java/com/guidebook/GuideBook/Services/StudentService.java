package com.guidebook.GuideBook.Services;



import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Repository.StudentRepository;
import com.guidebook.GuideBook.Repository.cutomrepository.CustomStudentRepositoryImpl;
import com.guidebook.GuideBook.dtos.AddStudentRequest;
import com.guidebook.GuideBook.dtos.FilteredStudentListRequest;
import com.guidebook.GuideBook.dtos.FilteredStudentDetails;
import com.guidebook.GuideBook.exceptions.BranchNotFoundException;
import com.guidebook.GuideBook.exceptions.CollegeNotFoundException;
import com.guidebook.GuideBook.exceptions.StudentClassTypeNotFoundException;
import com.guidebook.GuideBook.mapper.StudentMapper;
import com.guidebook.GuideBook.mapper.StudentProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private LanguageService languageService;
    private CollegeService collegeService;
    private BranchService branchService;
    private StudentClassTypeService studentClassTypeService;
    private StudentRepository studentRepository;
    private CustomStudentRepositoryImpl customStudentRepositoryImpl;
    private StudentProfileService studentProfileService;

    @Autowired
    public StudentService(StudentClassTypeService studentClassTypeService,LanguageService languageService,BranchService branchService,StudentRepository studentRepository,CollegeService collegeService, CustomStudentRepositoryImpl customStudentRepositoryImpl) {
        this.studentRepository = studentRepository;
        this.customStudentRepositoryImpl = customStudentRepositoryImpl;
        this.collegeService = collegeService;
        this.branchService = branchService;
        this.languageService = languageService;
        this.studentClassTypeService = studentClassTypeService;
        this.studentClassTypeService = studentClassTypeService;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<FilteredStudentDetails> getFilteredStudentList(FilteredStudentListRequest filteredStudentListRequest) {
        List<FilteredStudentDetails> list = new ArrayList<>();
        try {
            List<Student> studentList = customStudentRepositoryImpl.findStudentsByFiltersIgnoreCase(filteredStudentListRequest);

            for (Student student : studentList) {
                list.add(
                        new FilteredStudentDetails(
                                student.getStudentName(),
                                student.getStudentMis()
                        )
                );
            }
        } catch (Exception ex) {
            // Log the exception
            ex.printStackTrace();
            throw new RuntimeException("Error fetching filtered student list", ex); // Example: Rethrow as a more specific exception
        }

        return list;
    }



    public Student addStudent(AddStudentRequest addStudentRequest) throws CollegeNotFoundException, BranchNotFoundException, StudentClassTypeNotFoundException {
        Student newStudent = StudentMapper.mapToStudent(addStudentRequest);
        StudentProfile newStudentProfile = StudentProfileMapper.mapToStudentProfile(addStudentRequest);
        studentProfileService.addStudentProfile(newStudentProfile); //Saving a profile for this student in studentprofile table

        if((collegeService.getCollegeByCollegeNameIgnoreCase(addStudentRequest.getStudentCollegeName())) == null){
            throw new CollegeNotFoundException("College not found: " + addStudentRequest.getStudentCollegeName());
            //Throw custom CollegeNotFound Exception here - after charging plugged laptop.
        } else {
            newStudent.setStudentCollege(collegeService.getCollegeByCollegeNameIgnoreCase(addStudentRequest.getStudentCollegeName()));
        }

        if((branchService.getBranchByBranchNameIgnoreCase(addStudentRequest.getStudentBranchName())) == null){
            throw new BranchNotFoundException("Branch not found: " + addStudentRequest.getStudentBranchName());
            //Throw custom BranchNotFoundException
        } else {
            newStudent.setStudentBranch(branchService.getBranchByBranchNameIgnoreCase(addStudentRequest.getStudentBranchName()));
        }

        List<Language> languageList = new ArrayList<>();
        for (String studentLanguageName : addStudentRequest.getStudentLanguageNames()) {
            Language language = languageService.GetLanguageByLanguageNameIgnoreCase(studentLanguageName);
            if (language == null) {
                // Language does not exist, create a new one
                language = new Language();
                language.setLanguageName(studentLanguageName);
                language = languageService.addLanguage(language); // SAVE THE NEW LANGUAGE TO THE DATABASE
            }
            languageList.add(language); //add the language to the studentLanguageList
        }
        newStudent.setStudentLanguageList(languageList);

        //Do for studentClassType also
        if((studentClassTypeService.getStudentClassTypeByStudentClassTypeName(addStudentRequest.getStudentClassType())) == null){
            throw new StudentClassTypeNotFoundException("StudentClassType not found: " + addStudentRequest.getStudentClassType());
            //Throw custom StudentClassTypeNotFoundException
        } else {
            newStudent.setStudentClassType(studentClassTypeService.getStudentClassTypeByStudentClassTypeName(addStudentRequest.getStudentClassType()));
        }

        return studentRepository.save(newStudent);
    }

}

