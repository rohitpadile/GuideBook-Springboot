package com.guidebook.GuideBook.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    String zoomSessionFeedbackFormPara1;
    Float zoomSessionFeedbackFormRating; //OUT OF 5 - CREATE A STAR PATTERN IN REACT CHANGE THE COLOR OF TOTAL STARTS
    //WHEN MOUSE IS HOVERED ON IT.


}
