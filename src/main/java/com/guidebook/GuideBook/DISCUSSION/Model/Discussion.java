package com.guidebook.GuideBook.DISCUSSION.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guidebook.GuideBook.USER.Models.MyUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String discussionId;

    private String title;

//    @JsonIgnore
//    @OneToMany(mappedBy = "discussion")
//    List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "discussion")
    List<CommentV1> comments;

    @ManyToOne
    @JoinColumn(name = "fk_discussId_ownerUserId", referencedColumnName = "userId")
    private MyUser myUser;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    Date updatedOn;
    @Version
    @JsonIgnore
    private Integer version;

}
