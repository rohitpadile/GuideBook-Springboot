package com.guidebook.GuideBook.Repository.cutomrepository;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.Language;
import com.guidebook.GuideBook.Models.Student;
import com.guidebook.GuideBook.dtos.FilteredStudentListRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CustomStudentRepositoryImpl implements CustomStudentRepository{

    private EntityManager entityManager;
    @Autowired
    public CustomStudentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<Student> findStudentsByFilters(FilteredStudentListRequest filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);

        Root<Student> studentRoot = criteriaQuery.from(Student.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filter by Branch Name
        if (filters.getBranchName() != null && !filters.getBranchName().isEmpty()) {
            Join<Student, Branch> branchJoin = studentRoot.join("studentBranch", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(branchJoin.get("branchName"), filters.getBranchName()));
        }

        // Filter by Minimum Grade
        if (filters.getMinGrade() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("grade"), filters.getMinGrade()));
        }

        // Filter by Minimum CET Percentile
        if (filters.getMinCetPercentile() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(studentRoot.get("cetPercentile"), filters.getMinCetPercentile()));
        }

        // Filter by Student Class Type
        if (filters.getStudentClassType() != null) {
            predicates.add(criteriaBuilder.equal(studentRoot.get("studentClassType"), filters.getStudentClassType()));
        }

        // Filter by Language Name
        if (filters.getLanguageName() != null && !filters.getLanguageName().isEmpty()) {
            Join<Student, Language> languageJoin = studentRoot.join("studentLanguageList", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(languageJoin.get("languageName"), filters.getLanguageName()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
