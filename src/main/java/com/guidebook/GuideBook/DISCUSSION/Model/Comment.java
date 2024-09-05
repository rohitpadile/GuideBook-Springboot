//package com.guidebook.GuideBook.DISCUSSION.Model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.guidebook.GuideBook.USER.Models.MyUser;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.util.Date;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
//public class Comment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String commentId;
//    @Lob
//    private String text;
//
//    //map user
//    @ManyToOne
//    @JoinColumn(name = "fk_commentId_userEmail", referencedColumnName = "username")
//    private MyUser user;
//    //map discussion
//    @ManyToOne
//    @JoinColumn(name = "fk_commentId_discussId", referencedColumnName = "discussionId")
//    private Discussion discussion;
//
//
//
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonIgnore
//    Date createdOn;
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonIgnore
//    Date updatedOn;
//    @Version
//    @JsonIgnore
//    private Integer version;
//}
