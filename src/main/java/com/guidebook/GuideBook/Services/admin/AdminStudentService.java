package com.guidebook.GuideBook.Services.admin;



import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminStudentService {

    private StudentRepository studentRepository;

    @Autowired
    public AdminStudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

//    public Student updateStudent(Long id, Student student) {

//        Optional<Student> optionalStudent = studentRepository.findById(id);
//        if(optionalStudent.isPresent()){
//            Student existingStudent = optionalStudent.get();
//            //set the updated fields
//            existingStudent.setStudentName(student.getStudentName());
//            //remaining fields update
//            existingStudent.setCollege(student.getCollege());
//            existingStudent.setBranch(student.getBranch());
//            existingStudent.setGrade(student.getGrade());
//            existingStudent.setCetPercentile(student.getCetPercentile());
//            existingStudent.setStudentLanguageIds(student.getStudentLanguageIds());
//            existingStudent.setYearOfStudy(student.getYearOfStudy());
//            return studentRepository.save(student);
//        }else {
//            return null;
//        }
//    }

//    Optional<Language> optionalLanguage = languageRepository.findById(id);
//        if (optionalLanguage.isPresent()) {
//        Language language = optionalLanguage.get();
//        language.setLanguageName(updatedLanguage.getLanguageName());
//        // Set other fields to update as needed
//
//        return languageRepository.save(language);
//    } else {
//        return null; // or throw exception, depending on your use case
//    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
}

