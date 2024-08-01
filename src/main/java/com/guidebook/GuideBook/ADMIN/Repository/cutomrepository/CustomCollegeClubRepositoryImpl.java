//package com.guidebook.GuideBook.Repository.cutomrepository;
//
//import com.guidebook.GuideBook.Models.College;
////import com.guidebook.GuideBook.Models.CollegeClub;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.criteria.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//@Repository
//public class CustomCollegeClubRepositoryImpl implements CustomCollegeClubRepository{
//    private final EntityManager entityManager;
//    @Autowired
//    public CustomCollegeClubRepositoryImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    @Override
//    public List<CollegeClub> findClubByCollegeNameIgnoreCase(String collegeName) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<CollegeClub> query = criteriaBuilder.createQuery(CollegeClub.class);
//        Root<CollegeClub> root = query.from(CollegeClub.class);
//
//        // Join to College entity
//        Join<CollegeClub, College> collegeJoin = root.join("collegeClubCollege", JoinType.INNER);
//
//        // Create predicates
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(collegeJoin.get("collegeName")), collegeName.toLowerCase()));
//
//        query.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
//
//        return entityManager.createQuery(query).getResultList();
//    }
//}
