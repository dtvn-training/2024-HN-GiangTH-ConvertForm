package com.example.convertform.validation;

import com.example.convertform.validation.annotation.ValidTimeFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeFormatValidator implements ConstraintValidator<ValidTimeFormat, String> {

    @Override
    public boolean isValid(String time, ConstraintValidatorContext constraintValidatorContext) {
        String patternString = "\\d\\d:\\d\\d";
        final Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);

        final Matcher matcher = pattern.matcher(time);

        return matcher.matches();
    }
}
