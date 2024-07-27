package com.guidebook.GuideBook.Services;



import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Models.StudentProfile;
import com.guidebook.GuideBook.Repository.StudentRepository;
import com.guidebook.GuideBook.Repository.cutomrepository.CustomStudentRepositoryImpl;
import com.guidebook.GuideBook.dtos.AddStudentRequest;
import com.guidebook.GuideBook.dtos.filterstudents.FilteredStudentListRequest;
import com.guidebook.GuideBook.dtos.filterstudents.FilteredStudentDetails;
import com.guidebook.GuideBook.dtos.GetStudentBasicDetailsResponse;
import com.guidebook.GuideBook.exceptions.*;
import com.guidebook.GuideBook.mapper.StudentMapper;
import com.guidebook.GuideBook.mapper.StudentProfileMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StudentService {
    private LanguageService languageService;
    private CollegeService collegeService;
    private BranchService branchService;
    private StudentCategoryService studentCategoryService;
    private StudentClassTypeService studentClassTypeService;
    private StudentRepository studentRepository;
    private CustomStudentRepositoryImpl customStudentRepositoryImpl;
    private StudentProfileService studentProfileService;

    @Autowired
    public StudentService(
            StudentClassTypeService studentClassTypeService,
            LanguageService languageService,
            BranchService branchService,
            StudentCategoryService studentCategoryService,
            StudentRepository studentRepository,
            CollegeService collegeService,
            CustomStudentRepositoryImpl customStudentRepositoryImpl,
            StudentProfileService studentProfileService
    ) {
        this.studentRepository = studentRepository;
        this.customStudentRepositoryImpl = customStudentRepositoryImpl;
        this.collegeService = collegeService;
        this.branchService = branchService;
        this.languageService = languageService;
        this.studentClassTypeService = studentClassTypeService;
        this.studentCategoryService = studentCategoryService;
        this.studentProfileService = studentProfileService;
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
                                student.getStudentWorkEmail()
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


    @Transactional //THIS CAN THROW ERROR - KEEP A WATCH
    public Student addStudent(AddStudentRequest addStudentRequest) throws
            CollegeNotFoundException,
            BranchNotFoundException,
            StudentClassTypeNotFoundException,
            StudentCategoryNotFoundException,
            LanguageNotFoundException
    {
        Student newStudent = StudentMapper.mapToStudent(addStudentRequest);
        StudentProfile newStudentProfile = StudentProfileMapper.mapToStudentProfile(addStudentRequest);
        studentProfileService.addStudentProfileWithAddStudent(newStudentProfile); //Saving a profile for this student in studentprofile table

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

        //Add for student category also - if not throw StudentCategoryNotFoundException
        if((studentCategoryService.
                getStudentCategoryByStudentCategoryNameIgnoreCase(addStudentRequest.getStudentCategoryName())) == null){
            throw new StudentCategoryNotFoundException("Student category not found " + addStudentRequest.getStudentCategoryName());
        }else {
            newStudent.setStudentCategory(
                    studentCategoryService.getStudentCategoryByStudentCategoryNameIgnoreCase(
                            addStudentRequest.getStudentCategoryName()
                    )
            );
        }

/////////////////////////SEPARATE PRIVATE METHOD TO ADD LANGUAGE LIST ////////////////////////////////////
        Boolean languageListAddedSuccess = addStudentLanguageList(addStudentRequest ,newStudent);
        log.info("Language list added boolean : {}", languageListAddedSuccess);

        //Do for studentClassType also
        if((studentClassTypeService.getStudentClassTypeByStudentClassTypeName(addStudentRequest.getStudentClassType())) == null){
            throw new StudentClassTypeNotFoundException("StudentClassType not found: " + addStudentRequest.getStudentClassType());
            //Throw custom StudentClassTypeNotFoundException
        } else {
            newStudent.setStudentClassType(studentClassTypeService.getStudentClassTypeByStudentClassTypeName(addStudentRequest.getStudentClassType()));
        }

        return studentRepository.save(newStudent);
    }

    public GetStudentBasicDetailsResponse getStudentBasicDetails(String studentWorkEmail)
    throws StudentBasicDetailsNotFoundException
    {
        Student student = studentRepository.findByStudentWorkEmail(studentWorkEmail);
        GetStudentBasicDetailsResponse response = new GetStudentBasicDetailsResponse();
        if(student.getStudentPublicEmail()!=null){ //just to be sure apart from the exception.
            response.setPublicEmail(student.getStudentPublicEmail());
        }
        response.setBranch(student.getStudentBranch().getBranchName());
        response.setGrade(student.getGrade());
        response.setCetPercentile(student.getCetPercentile());
        response.setClassType(student.getStudentClassType().getStudentClassTypeName());

        List<Language> languageList = student.getStudentLanguageList();
        for(Language language : languageList){
            response.getLanguagesSpoken().add(language.getLanguageName());
        }
        return response;
    }

    private boolean addStudentLanguageList(AddStudentRequest addStudentRequest, Student newStudent)
    throws LanguageNotFoundException{
        List<Language> languageList = new ArrayList<>();
        for (String studentLanguageName : addStudentRequest.getStudentLanguageNames()) {
            Language language = languageService.GetLanguageByLanguageNameIgnoreCase(studentLanguageName);
            if (language == null) {
                throw new LanguageNotFoundException("Language not found : " + studentLanguageName);
            }
            languageList.add(language); //add the language to the studentLanguageList if not null
        }
        newStudent.setStudentLanguageList(languageList);
        return true;
    }
}

