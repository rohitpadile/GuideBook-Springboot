package com.guidebook.GuideBook.ADMIN.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class CompanyFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String companyFeedbackId;
    private String companyFeedbackUserName;
    private String companyFeedbackUserEmail;
    @Lob
    private String companyFeedbackTextBoxContent;

    //NO JSON IGNORE FOR TIME AND VERSION FIELDS.
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)

    Date createdOn;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)

    Date updatedOn;
    @Version

    private Integer version;
}
