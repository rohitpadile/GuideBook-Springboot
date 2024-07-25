package com.guidebook.GuideBook.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guidebook.GuideBook.enums.ZoomSessionFeebackFormOverallFeedback;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Enumerated(value = EnumType.STRING)
    ZoomSessionFeebackFormOverallFeedback overallFeedback;
    @Lob
//    @Column(length = 10000)
    String purposeFulfilled;
    @Lob
//    @Column(length = 10000)
    String moreFeedbackAboutStudent;

    @Lob
    String feedbackForCompany;

    @OneToOne(mappedBy = "zoomSessionFeedbackForm")
            @JsonIgnore
    ZoomSessionTransactionFree zoomSessionTransactionFree;
}
