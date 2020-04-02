package com.example.logic.ann.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import lombok.Data;

@Data
public class Employee {
    private LocalDate birthDate;

    @NotNull
    @Size(min = 2, max = 6)
    private String name;
}
