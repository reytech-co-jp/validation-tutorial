package com.validationtutorial.task2;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {
    private static final String[] VALID_CATEGORIES = {"Electronics", "Clothing", "Books"};

    @Override
    public void initialize(ValidCategory constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        for (String validCategory : VALID_CATEGORIES) {
            if (validCategory.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
