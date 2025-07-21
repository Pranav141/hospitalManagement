package com.pranav.hospitalManagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BusinessHoursValidator.class)
public @interface ValidBusinessHours {
    String message() default "Appointment must be scheduled during business hours (Monday-Friday, 9:00 AM - 6:00 PM)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
