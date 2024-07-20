package com.guidebook.GuideBook.Repository.cutomrepository;

import com.guidebook.GuideBook.Models.*;
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

    @Autowired
    public CustomStudentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Student> findStudentsByFiltersIgnoreCase(FilteredStudentListRequest filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);

        Root<Student> studentRoot = criteriaQuery.from(Student.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filter by College Name (Case-Insensitive)
        if (filters.getCollegeName() != null && !filters.getCollegeName().isEmpty()) {
            Join<Student, College> collegeJoin = studentRoot.join("studentCollege", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(collegeJoin.get("collegeName")),
                    filters.getCollegeName().toLowerCase()
            ));
        }

        // Filter by Branch Name (Case-Insensitive)
        if (filters.getBranchName() != null && !filters.getBranchName().isEmpty()) {
            Join<Student, Branch> branchJoin = studentRoot.join("studentBranch", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(branchJoin.get("branchName")),
                    filters.getBranchName().toLowerCase()
            ));
        }

        // Filter by Minimum Grade
        if (filters.getMinGrade() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("grade"), filters.getMinGrade()));
        }

        // Filter by Minimum CET Percentile
        if (filters.getMinCetPercentile() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("cetPercentile"), filters.getMinCetPercentile()));
        }

        // Filter by Student Class Type (Case-Insensitive)
        if (filters.getStudentClassType() != null && !filters.getStudentClassType().isEmpty()) {
            Join<Student, StudentClassType> classTypeJoin = studentRoot.join("studentClassType", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(classTypeJoin.get("studentClassTypeName")),
                    filters.getStudentClassType().toLowerCase()
            ));
        }

        // Filter by Language Name (Case-Insensitive)
        if (filters.getLanguageName() != null && !filters.getLanguageName().isEmpty()) {
            Join<Student, Language> languageJoin = studentRoot.join("studentLanguageList", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(languageJoin.get("languageName")),
                    filters.getLanguageName().toLowerCase()
            ));
        }

        // Filter by Student Category (Case-Insensitive)
        if (filters.getStudentCategory() != null && !filters.getStudentCategory().isEmpty()) {
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
