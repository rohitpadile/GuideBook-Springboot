package com.guidebook.GuideBook.Repository.cutomrepository;

import com.guidebook.GuideBook.Models.Branch;
import com.guidebook.GuideBook.Models.College;
import com.guidebook.GuideBook.dtos.selectStudentFiltering.GetAllBranchNameListForCollegeRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomBranchRepositoryImpl implements CustomBranchRepository{

    private EntityManager entityManager;
    @Autowired
    public CustomBranchRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Branch> findBranchesForCollegeIgnoreCase(GetAllBranchNameListForCollegeRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Branch> criteriaQuery = criteriaBuilder.createQuery(Branch.class);
        Root<Branch> branchRoot = criteriaQuery.from(Branch.class);
        Join<Branch, College> collegeJoin = branchRoot.join("branchCollegeList", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        // Filter by College Name (Case-Insensitive)
        if (request.getCollegeName() != null && !request.getCollegeName().isEmpty()) {
            predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(collegeJoin.get("collegeName")),
                    request.getCollegeName().toLowerCase()
            ));
        }

        criteriaQuery.select(branchRoot).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
