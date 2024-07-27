package com.guidebook.GuideBook.Repository.cutomrepository;

import com.guidebook.GuideBook.Models.*;
import com.guidebook.GuideBook.Repository.*;
import com.guidebook.GuideBook.Services.*;
import com.guidebook.GuideBook.dtos.filterstudents.FilteredStudentListRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CustomStudentRepositoryImpl implements CustomStudentRepository {

    private final EntityManager entityManager;
    private final CollegeService collegeService;
    private final BranchService branchService;
    private final StudentClassTypeService studentClassTypeService;
    private final LanguageService languageService;
    private final StudentCategoryService studentCategoryService;


    @Autowired
    public CustomStudentRepositoryImpl(EntityManager entityManager,
                                       CollegeService collegeService,
                                       BranchService branchService,
                                       StudentClassTypeService studentClassTypeService,
                                       LanguageService languageService,
                                       StudentCategoryService studentCategoryService) {
        this.entityManager = entityManager;
        this.branchService = branchService;
        this.collegeService = collegeService;
        this.studentCategoryService = studentCategoryService;
        this.languageService = languageService;
        this.studentClassTypeService = studentClassTypeService;
    }

    @Override
    public List<Student> findStudentsByFiltersIgnoreCase(FilteredStudentListRequest filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);

        Root<Student> studentRoot = criteriaQuery.from(Student.class);
        List<Predicate> predicates = new ArrayList<>();


        List<String> validCollegeNames = collegeService.getAllCollegeNameList();

        List<String> validBranchNames = branchService.getAllBranchNameList();

        List<String> validStudentClassTypes = studentClassTypeService.getAllStudentClassTypeNameList();

        //below method is already in use with other services, so be cautious to update it.
        List<String> validLanguageNames = languageService.getAllLanguageNamesList().getAllLanguageNamesList();

        List<String> validStudentCategories = studentCategoryService.getAllStudentCategoryNameList();

        // Filter by College Name (Case-Insensitive)
        if (filters.getCollegeName() != null && !filters.getCollegeName().isEmpty() && validCollegeNames.contains(filters.getCollegeName())) {
            Join<Student, College> collegeJoin = studentRoot.join("studentCollege", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(collegeJoin.get("collegeName")),
                    filters.getCollegeName().toLowerCase()
            ));
        }

        // Filter by Branch Name (Case-Insensitive)
        if (filters.getBranchName() != null && !filters.getBranchName().isEmpty() && validBranchNames.contains(filters.getBranchName())) {
            Join<Student, Branch> branchJoin = studentRoot.join("studentBranch", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(branchJoin.get("branchName")),
                    filters.getBranchName().toLowerCase()
            ));
        }

        // Filter by Minimum Grade
        if (filters.getMinGrade() != null && filters.getMinGrade() >= 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("grade"), filters.getMinGrade()));
        } else {
            // Use default value 7.0 if MinGrade is null or invalid
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("grade"), 7.0));
        }

        // Filter by Minimum CET Percentile
        if (filters.getMinCetPercentile() != null && filters.getMinCetPercentile() >= 0) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("cetPercentile"), filters.getMinCetPercentile()));
        } else {
            // Use default value 0 if MinCetPercentile is null or invalid
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("cetPercentile"), 0.0));
        }

        // Filter by Student Class Type (Case-Insensitive)
        if (filters.getStudentClassType() != null && !filters.getStudentClassType().isEmpty() && validStudentClassTypes.contains(filters.getStudentClassType())) {
            Join<Student, StudentClassType> classTypeJoin = studentRoot.join("studentClassType", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(classTypeJoin.get("studentClassTypeName")),
                    filters.getStudentClassType().toLowerCase()
            ));
        }

        // Filter by Language Name (Case-Insensitive)
        if (filters.getLanguageName() != null && !filters.getLanguageName().isEmpty() && validLanguageNames.contains(filters.getLanguageName())) {
            Join<Student, Language> languageJoin = studentRoot.join("studentLanguageList", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(languageJoin.get("languageName")),
                    filters.getLanguageName().toLowerCase()
            ));
        }

        // Filter by Student Category (Case-Insensitive)
        if (filters.getStudentCategory() != null && !filters.getStudentCategory().isEmpty() && validStudentCategories.contains(filters.getStudentCategory())) {
            Join<Student, StudentCategory> categoryJoin = studentRoot.join("studentCategory", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(categoryJoin.get("studentCategoryName")),
                    filters.getStudentCategory().toLowerCase()
            ));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
