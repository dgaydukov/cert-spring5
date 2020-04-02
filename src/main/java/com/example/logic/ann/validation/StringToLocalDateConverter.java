package com.example.logic.ann.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT);

    @Override
    public LocalDate convert(String s) {
        return LocalDate.parse(s, formatter);
    }
}
