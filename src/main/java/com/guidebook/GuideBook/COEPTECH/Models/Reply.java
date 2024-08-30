package com.guidebook.GuideBook.COEPTECH.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String text;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;


    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private Reply parentReply;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_reply_id")
    private List<Reply> replies;

}
