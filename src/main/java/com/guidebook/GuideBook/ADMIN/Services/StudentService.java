package com.guidebook.GuideBook.ADMIN.Services;



import com.guidebook.GuideBook.ADMIN.dtos.DeleteStudentRequest;
import com.guidebook.GuideBook.ADMIN.dtos.GetStudentBasicDetailsResponse;
import com.guidebook.GuideBook.ADMIN.dtos.filterstudents.FilteredStudentListRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.*;
import com.guidebook.GuideBook.ADMIN.mapper.StudentProfileMapper;
import com.guidebook.GuideBook.ADMIN.Models.Language;
import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Repository.StudentRepository;
import com.guidebook.GuideBook.ADMIN.Repository.cutomrepository.CustomStudentRepositoryImpl;
import com.guidebook.GuideBook.ADMIN.dtos.AddStudentRequest;
import com.guidebook.GuideBook.ADMIN.dtos.UpdateStudentRequest;
import com.guidebook.GuideBook.ADMIN.dtos.filterstudents.FilteredStudentDetails;

import com.guidebook.GuideBook.ADMIN.mapper.StudentMapper;
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

    public List<FilteredStudentDetails> getFilteredStudentList(FilteredStudentListRequest filteredStudentListRequest)
            throws FilteredStudentListNotFoundException
    {
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
            throw new FilteredStudentListNotFoundException("Error fetching filtered student list" + ex.getMessage()); // Example: Rethrow as a more specific exception
        }
        return list;
    }


    @Transactional //THIS CAN THROW ERROR - KEEP A WATCH
    public GetStudentBasicDetailsResponse addStudent(AddStudentRequest addStudentRequest) throws
            CollegeNotFoundException,
            BranchNotFoundException,
            StudentClassTypeNotFoundException,
            StudentCategoryNotFoundException,
            LanguageNotFoundException
    {
        Student newStudent = StudentMapper.mapToStudent(addStudentRequest);

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

/////////////////////////SEPARATE PRIVATE METHOD TO ADD LANGUAGE LIST @Transactional ////////////////////////////////////
        Boolean languageListAddedSuccess = addStudentLanguageList(addStudentRequest ,newStudent);
        log.info("Language list added boolean : {}", languageListAddedSuccess);

        //Do for studentClassType also
        if((studentClassTypeService.getStudentClassTypeByStudentClassTypeName(addStudentRequest.getStudentClassType())) == null){
            throw new StudentClassTypeNotFoundException("StudentClassType not found: " + addStudentRequest.getStudentClassType());
            //Throw custom StudentClassTypeNotFoundException
        } else {
            newStudent.setStudentClassType(studentClassTypeService.getStudentClassTypeByStudentClassTypeName(addStudentRequest.getStudentClassType()));
        }
        StudentProfile newStudentProfile = StudentProfileMapper.mapToStudentProfile(addStudentRequest);
        studentProfileService.addStudentProfileWithAddStudent(newStudentProfile); //Saving a profile for this student in studentprofile table
        return getStudentBasicDetailsResponse(studentRepository.save(newStudent));
    }

    public GetStudentBasicDetailsResponse getStudentBasicDetails(String studentWorkEmail)
    throws StudentBasicDetailsNotFoundException
    {
        Student student = studentRepository.findByStudentWorkEmail(studentWorkEmail);
        return getStudentBasicDetailsResponse(student);
    }

    @Transactional
    public GetStudentBasicDetailsResponse updateStudent(UpdateStudentRequest updateStudentRequest) throws CollegeNotFoundException, StudentClassTypeNotFoundException, StudentCategoryNotFoundException, LanguageNotFoundException {
        Student student = studentRepository.findByStudentWorkEmail(updateStudentRequest.getStudentWorkEmail());

        student.setStudentName(updateStudentRequest.getStudentName());
        student.setStudentMis(updateStudentRequest.getStudentMis());
        student.setStudentPublicEmail(updateStudentRequest.getStudentPublicEmail());
        student.setStudentCollege(collegeService.getCollegeByCollegeNameIgnoreCase(
                updateStudentRequest.getStudentCollegeName()
        ));
        student.setStudentBranch(branchService.getBranchByBranchNameIgnoreCase(
                updateStudentRequest.getStudentBranchName()
        ));
        student.setCetPercentile(updateStudentRequest.getStudentCetPercentile());
        student.setGrade(updateStudentRequest.getStudentGrade());
        student.setStudentClassType(studentClassTypeService.getStudentClassTypeByStudentClassTypeName(
                updateStudentRequest.getStudentClassType()
        ));
        student.setStudentCategory(studentCategoryService.getStudentCategoryByStudentCategoryNameIgnoreCase(
                updateStudentRequest.getStudentCategoryName()
        ));
        Boolean languageListAddedSuccess = updateStudentLanguageList(updateStudentRequest ,student);
        log.info("Language list updated boolean : {}", languageListAddedSuccess);

        return getStudentBasicDetailsResponse(studentRepository.save(student));
    }
    @Transactional
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

    @Transactional
    private boolean updateStudentLanguageList(UpdateStudentRequest updateStudentRequest, Student newStudent)
            throws LanguageNotFoundException{
        List<Language> languageList = new ArrayList<>();
        for (String studentLanguageName : updateStudentRequest.getStudentLanguageNames()) {
            Language language = languageService.GetLanguageByLanguageNameIgnoreCase(studentLanguageName);
            if (language == null) {
                throw new LanguageNotFoundException("Language not found : " + studentLanguageName);
            }
            languageList.add(language); //add the language to the studentLanguageList if not null
        }
        newStudent.setStudentLanguageList(languageList);
        return true;
    }
    @Transactional
    private static GetStudentBasicDetailsResponse getStudentBasicDetailsResponse(Student student){
        GetStudentBasicDetailsResponse response = new GetStudentBasicDetailsResponse();

        response.setStudentName(student.getStudentName());
        response.setStudentMis(student.getStudentMis());
        response.setStudentWorkEmail(student.getStudentWorkEmail());
        response.setStudentPublicEmail(student.getStudentPublicEmail());
        response.setCollege(student.getStudentCollege().getCollegeName());
        response.setBranch(student.getStudentBranch().getBranchName());
        response.setCetPercentile(student.getCetPercentile());
        response.setGrade(student.getGrade());
        response.setClassType(student.getStudentClassType().getStudentClassTypeName());
        response.setCategory(student.getStudentCategory().getStudentCategoryName());

        List<String> languages = new ArrayList<>();
        for(Language l : student.getStudentLanguageList()){
            languages.add(l.getLanguageName());
        }
        response.setLanguagesSpoken(languages);

        return response;
    }


    public void deleteStudent(DeleteStudentRequest deleteStudentRequest)
            throws StudentProfileContentNotFoundException
    {
        StudentProfile profile = studentProfileService.getStudentProfileForGeneralPurpose(
                deleteStudentRequest.getStudentWorkEmail());
        Student student = studentRepository.findByStudentWorkEmail(
                deleteStudentRequest.getStudentWorkEmail()
        );
        studentProfileService.deleteStudentProfile(profile);
        studentRepository.delete(student);
    }
}

