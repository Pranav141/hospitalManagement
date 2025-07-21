package com.pranav.hospitalManagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

class BusinessHoursValidator implements ConstraintValidator<ValidBusinessHours, LocalDateTime> {

    private static final LocalTime BUSINESS_START = LocalTime.of(9, 0); // 9:00 AM
    private static final LocalTime BUSINESS_END = LocalTime.of(17, 45);  // 5:45 PM

    @Override
    public void initialize(ValidBusinessHours constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime dateTime, ConstraintValidatorContext context) {
        if (dateTime == null) {
            return true;
        }
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();
        boolean isWeekday = dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
        boolean isBusinessHours = !time.isBefore(BUSINESS_START) && !time.isAfter(BUSINESS_END);

        return isWeekday && isBusinessHours;
    }
}