package com.example.logic.ann.validation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Employee {
    // NotNull - only for objects
    @NotNull(message = "Id can't be null")
    private String id;

    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, max = 5, message = "Name should be between 3 and 5 letters")
    private String name;

    @CreditCardNumber(message = "Credit card should be correct")
    private String cardNumber;

    @Digits(integer = 3, fraction = 0, message = "Ccv should be exactly 3 digits")
    private String ccv;
}
