package com.validationtutorial.task2;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {
    @Override
    public void initialize(ValidCategory constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        for (Category validCategory : Category.values()) {
            if (validCategory.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
