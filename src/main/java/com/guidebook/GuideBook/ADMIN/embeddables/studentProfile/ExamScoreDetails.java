package com.guidebook.GuideBook.ADMIN.embeddables.studentProfile;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamScoreDetails {
    private String scoreDetail;
}
