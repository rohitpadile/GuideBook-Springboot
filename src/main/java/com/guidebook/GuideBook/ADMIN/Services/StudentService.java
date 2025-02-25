package com.guidebook.GuideBook.ADMIN.Services;



import com.guidebook.GuideBook.ADMIN.Services.emailservice.EmailServiceImpl;
import com.guidebook.GuideBook.ADMIN.dtos.*;
import com.guidebook.GuideBook.ADMIN.dtos.filterstudents.FilteredStudentListRequest;
import com.guidebook.GuideBook.ADMIN.exceptions.*;
import com.guidebook.GuideBook.ADMIN.mapper.StudentProfileMapper;
import com.guidebook.GuideBook.ADMIN.Models.Language;
import com.guidebook.GuideBook.ADMIN.Models.Student;
import com.guidebook.GuideBook.ADMIN.Models.StudentProfile;
import com.guidebook.GuideBook.ADMIN.Repository.StudentRepository;
import com.guidebook.GuideBook.ADMIN.Repository.cutomrepository.CustomStudentRepositoryImpl;
import com.guidebook.GuideBook.ADMIN.dtos.filterstudents.FilteredStudentDetails;

import com.guidebook.GuideBook.ADMIN.mapper.StudentMapper;
import com.guidebook.GuideBook.TR.util.EncryptionUtilForStudentProfileEdit;
import com.guidebook.GuideBook.USER.Models.ClientAccount;
import com.guidebook.GuideBook.USER.Models.StudentMentorAccount;
import com.guidebook.GuideBook.USER.Service.ClientAccountService;
import com.guidebook.GuideBook.USER.Service.StudentMentorAccountService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private EmailServiceImpl emailService;
    private final StudentMentorAccountService studentMentorAccountService;
    private final ClientAccountService clientAccountService;
    @Value("${websitedomainname}")
    private String websiteDomainName;

    @Autowired
    public StudentService(
            StudentMentorAccountService studentMentorAccountService,
            StudentClassTypeService studentClassTypeService,
            LanguageService languageService,
            BranchService branchService,
            StudentCategoryService studentCategoryService,
            StudentRepository studentRepository,
            CollegeService collegeService,
            CustomStudentRepositoryImpl customStudentRepositoryImpl,
            StudentProfileService studentProfileService,
            EmailServiceImpl emailService,
            ClientAccountService clientAccountService
    ) {
        this.studentRepository = studentRepository;
        this.customStudentRepositoryImpl = customStudentRepositoryImpl;
        this.collegeService = collegeService;
        this.branchService = branchService;
        this.languageService = languageService;
        this.studentClassTypeService = studentClassTypeService;
        this.studentCategoryService = studentCategoryService;
        this.studentProfileService = studentProfileService;
        this.emailService = emailService;
        this.studentMentorAccountService = studentMentorAccountService;
        this.clientAccountService = clientAccountService;
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
                                student.getStudentWorkEmail(),
                                student.getStudentCollege().getCollegeName(),
                                student.getStudentBranch().getBranchName(),
                                student.getCetPercentile().toString()

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
            LanguageNotFoundException,
            StudentProfileContentNotFoundException,
            EncryptionFailedException,
            AlreadyPresentException {
        if((studentRepository.findByStudentWorkEmailIgnoreCase(addStudentRequest.getStudentWorkEmail())) != null){
            throw new AlreadyPresentException("Student is already present with work email: " + addStudentRequest.getStudentWorkEmail());
        }
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
//        if((studentCategoryService.
//                getStudentCategoryByStudentCategoryNameIgnoreCase(addStudentRequest.getStudentCategoryName())) == null){
//            throw new StudentCategoryNotFoundException("Student category not found " + addStudentRequest.getStudentCategoryName());
//        }else {
//            newStudent.setStudentCategory(
//                    studentCategoryService.getStudentCategoryByStudentCategoryNameIgnoreCase(
//                            addStudentRequest.getStudentCategoryName()
//                    )
//            );
//        }

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
        if(!(studentProfileService.checkIfStudentProfileExists(addStudentRequest.getStudentWorkEmail()))){
            StudentProfile newStudentProfile = StudentProfileMapper.mapToStudentProfile(addStudentRequest);
            studentProfileService.addStudentProfileWithAddStudent(newStudentProfile); //Saving a profile for this student in studentprofile table
        } else {
            log.info("Student profile already exists for email: {}",addStudentRequest.getStudentWorkEmail());
        }

        // Encrypt studentWorkEmail in Student Profile Edit Navigation and send to Student Mentor
        try {
            String encryptedEmail = EncryptionUtilForStudentProfileEdit.encrypt(addStudentRequest.getStudentWorkEmail());
            String emailBody = getEmailForAddStudent(encryptedEmail, newStudent);
            emailService.sendSimpleMessage(addStudentRequest.getStudentWorkEmail(), "Edit Your Profile", emailBody);
        } catch (Exception e) {
            log.error("Error encrypting email or sending email", e);
            throw new EncryptionFailedException("Email at addStudent() method failed");
        }

        //Make a StudentMentor Account also here
        ClientAccount clientAccount = clientAccountService.getAccountByEmail(addStudentRequest.getStudentWorkEmail());
        if((clientAccount) != null){
            //A client account exists
            //transfer the data from client account to Student Mentor account
            StudentMentorAccount studentMentorAccount =
                    getStudentMentorAccountUsingClientAccount(addStudentRequest, clientAccount);
//            THIS IS VERY IMPORANT - KEEP UPDATING THIS FROM TIME TO TIME
//            and delete client account
            clientAccountService.deleteClientAccount(clientAccount);
            studentMentorAccountService.addStudentMentorAccount(studentMentorAccount);
        } else {
            //Client account do not exists, we want to make a student mentor account with disabled subscription
            StudentMentorAccount studentMentorAccount = getNewStudentMentorAccount(addStudentRequest);
            studentMentorAccountService.addStudentMentorAccount(studentMentorAccount);
        }

        return getStudentBasicDetailsResponse(studentRepository.save(newStudent));
    }
    @Transactional
    private static StudentMentorAccount getNewStudentMentorAccount(AddStudentRequest addStudentRequest) {
        StudentMentorAccount studentMentorAccount = new StudentMentorAccount();
        studentMentorAccount.setStudentMentorAccountWorkEmail(addStudentRequest.getStudentWorkEmail());
        //monthly subscription transfer from client account
        studentMentorAccount.setStudentMentorAccountSubscription_Monthly(0);//disabled subscription
        studentMentorAccount.setClientCollege(addStudentRequest.getStudentCollegeName());
        studentMentorAccount.setStudentMentorAccountZoomSessionCount(0L);
        studentMentorAccount.setStudentMentorAccountOfflineSessionCount(0L);

        return studentMentorAccount;
    }

    @Transactional
    private static StudentMentorAccount getStudentMentorAccountUsingClientAccount(AddStudentRequest addStudentRequest, ClientAccount clientAccount) {
        StudentMentorAccount studentMentorAccount = new StudentMentorAccount();
        studentMentorAccount.setStudentMentorAccountWorkEmail(addStudentRequest.getStudentWorkEmail());
        //transfer monthly subscription from client account
        studentMentorAccount.setStudentMentorAccountSubscription_Monthly(
                clientAccount.getClientAccountSubscription_Monthly()
        );
        studentMentorAccount.setClientCollege(addStudentRequest.getStudentCollegeName());
        //This is verfied college name from company

        studentMentorAccount.setClientFirstName(clientAccount.getClientFirstName());
        studentMentorAccount.setClientMiddleName(clientAccount.getClientMiddleName());
        studentMentorAccount.setClientLastName(clientAccount.getClientLastName());
        studentMentorAccount.setClientPhoneNumber(clientAccount.getClientPhoneNumber());
        studentMentorAccount.setClientAge(clientAccount.getClientAge());
        studentMentorAccount.setClientValidProof(clientAccount.getClientValidProof());
        studentMentorAccount.setClientZoomEmail(clientAccount.getClientZoomEmail());
        studentMentorAccount.setStudentMentorAccountZoomSessionCount(clientAccount.getClientAccountZoomSessionCount());
        studentMentorAccount.setStudentMentorAccountOfflineSessionCount(clientAccount.getClientAccountOfflineSessionCount());
        return studentMentorAccount;
    }

    @Transactional
    private String getEmailForAddStudent(String encryptedEmail, Student newStudent) {
        String profileEditLink = websiteDomainName + "/studentprofileedit/" + encryptedEmail;
        String emailBody = String.format("Dear %s,\n\n" +
                "Congratulations on coming on this platform.\n" +
                "We hope to see more of you and wish you well for your future" +
                "\n\nPlease signup on the website using work email, login and edit your profile(use work email to signup)" +
                "\n\nFor any help, please contact mentorhelpguidebookx@gmail.com", newStudent.getStudentName());
        return emailBody;
    }

    public String getEditStudentProfileLink(Student student) throws Exception {
        String encryptedEmail = EncryptionUtilForStudentProfileEdit.encrypt(student.getStudentWorkEmail());
        return "studentprofileedit/" + encryptedEmail;
    }
    public String getStudentProfileLink(Student student) throws Exception {
        String encryptedEmail = EncryptionUtilForStudentProfileEdit.encrypt(student.getStudentWorkEmail());
        return websiteDomainName + "/studentProfile/" + encryptedEmail;
    }

    public GetStudentBasicDetailsResponse getStudentBasicDetails(String studentWorkEmail)
    throws StudentBasicDetailsNotFoundException
    {
        Student student = studentRepository.findByStudentWorkEmailIgnoreCase(studentWorkEmail);
        return getStudentBasicDetailsResponse(student);
    }

    @Transactional
    public GetStudentBasicDetailsResponse updateStudent(UpdateStudentRequest updateStudentRequest)
            throws CollegeNotFoundException,
            StudentClassTypeNotFoundException,
            StudentCategoryNotFoundException,
            LanguageNotFoundException,
            StudentNotFoundException {
        Student student = studentRepository.findByStudentWorkEmailIgnoreCase(updateStudentRequest.getStudentWorkEmail());
        if(student == null){
            throw new StudentNotFoundException("Student not found with work email: " + updateStudentRequest.getStudentWorkEmail());
        }
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
//        student.setStudentCategory(studentCategoryService.getStudentCategoryByStudentCategoryNameIgnoreCase(
//                updateStudentRequest.getStudentCategoryName()
//        ));
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
//        response.setCategory(student.getStudentCategory().getStudentCategoryName());

        List<String> languages = new ArrayList<>();
        for(Language l : student.getStudentLanguageList()){
            languages.add(l.getLanguageName());
        }
        response.setLanguagesSpoken(languages);

        return response;
    }

    @Transactional
    public void deactivateStudent(DeactivateStudentRequest deactivateStudentRequest)
            throws StudentNotFoundException {
        Student student = studentRepository.findByStudentWorkEmailIgnoreCase(
                deactivateStudentRequest.getStudentWorkEmail());
        if(student!=null){
            student.setIsActivated(0);
            studentRepository.save(student);
        } else {
            throw new StudentNotFoundException("Student not found at deactivateStudent() method");
        }
    }

    @Transactional
    public void activateStudent(ActivateStudentRequest activateStudentRequest)
            throws StudentNotFoundException {
        Student student = studentRepository.findByStudentWorkEmailIgnoreCase(
                activateStudentRequest.getStudentWorkEmail());
        if(student!=null){
            student.setIsActivated(1);
            studentRepository.save(student);
        }else {
            throw new StudentNotFoundException("Student not found at activateStudent() method");
        }
    }

    public Student getStudentByWorkEmail(String workEmail){
        return studentRepository.findByStudentWorkEmail(workEmail);
    }

//    public List<GetStudentBasicDetailsResponse> getStudentsWithName(String studentName) {
//        List<GetStudentBasicDetailsResponse> list = new ArrayList<>();
//        for(Student student : studentRepository.findByStudentNameIgnoreCase(studentName/*.replace(" ", "")*/)){
//            GetStudentBasicDetailsResponse response = new GetStudentBasicDetailsResponse();
//            log.info("Student found : {}",student.getStudentName() );
//            response.setBranch(student.getStudentBranch().getBranchName());
//            response.setCollege(student.getStudentCollege().getCollegeName());
//            response.setCetPercentile(student.getCetPercentile());
//            response.setStudentName(student.getStudentName());
//            list.add(response);
//        }
//        return  list;
//    }
}

