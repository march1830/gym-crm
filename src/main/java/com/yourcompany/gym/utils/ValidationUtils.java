package com.yourcompany.gym.utils;

import java.util.Objects;

public final class ValidationUtils {

    // A private constructor to prevent instantiation
    private ValidationUtils() {}

    /**
     * Checks if the provided objects are null or if String objects are blank.
     * Throws an IllegalArgumentException if any validation fails.
     * @param fields A variable number of objects to validate.
     */

    public static void validateRequiredFields(Object... fields) {
        for (Object field : fields) {
            if (field == null) {
                throw new IllegalArgumentException("Required field cannot be null.");
            }
            if (field instanceof String && ((String) field).isBlank()) {
                throw new IllegalArgumentException("Required String field cannot be blank.");
            }
        }
    }
}