package com.guidebook.GuideBook.ADMIN.Models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ZoomSessionFeedbackForm {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String zoomSessionFeedbackFormId;

//    @Enumerated(value = EnumType.STRING)
//    ZoomSessionFeebackFormOverallFeedback overallFeedback;
    String overallFeedback;
    @Lob
//    @Column(length = 10000)
    String purposeFulfilled;
    @Lob
//    @Column(length = 10000)
    String moreFeedbackAboutStudent;

    @Lob
    String feedbackForCompany;

    Integer isSubmitted;
}
