package com.guidebook.GuideBook.ADMIN.Repository.cutomrepository;

import com.guidebook.GuideBook.ADMIN.Models.College;
import com.guidebook.GuideBook.ADMIN.Models.EntranceExam;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomCollegeRepositoryImpl implements CustomCollegeRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<College> findCollegeByEntranceExamNameIgnoreCase(String examName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<College> query = cb.createQuery(College.class);
        Root<College> college = query.from(College.class);
        Join<College, EntranceExam> entranceExam = college.join("collegeEntranceSet");

        Predicate examNamePredicate = cb.equal(cb.lower(entranceExam.get("entranceExamName")), examName.toLowerCase());

        query.select(college).where(examNamePredicate);

        return entityManager.createQuery(query).getResultList();
    }
}