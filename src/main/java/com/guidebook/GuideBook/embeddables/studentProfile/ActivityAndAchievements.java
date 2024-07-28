package com.guidebook.GuideBook.embeddables.studentProfile;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ActivityAndAchievements {
    private String activity;
}
