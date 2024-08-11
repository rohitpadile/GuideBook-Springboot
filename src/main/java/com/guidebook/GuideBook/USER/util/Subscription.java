package com.guidebook.GuideBook.USER.util;

import org.springframework.beans.factory.annotation.Value;

//utility class
public class Subscription {
    @Value("${submonthly}")
    public Long subMonthly;
    public Long subYearly;
}
