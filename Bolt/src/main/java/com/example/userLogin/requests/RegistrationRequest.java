package com.example.userLogin.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    //---------------> general
    @NotBlank(message = "First name can not be blank")
    @Size(min = 3, max = 30, message = "First name must be betweeen 3-30 chars")
    private final String firstName;
    @NotBlank(message = "Last name can not be empty")
    @Size(min = 3, max = 30, message = "Last name must be between 3-30 chars")
    private final String lastName;
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Enter an email in proper form")
    @Pattern(regexp = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")
    private final String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6-100 chars")
    private final String password;

//---------------> customer

    @Pattern(regexp = "^4[0-9]{12}(?:[0-9]{3})?$", message = "Only VISA cards allowed")
    private final String cardNumber;
    @Pattern(regexp = "\\b(0[1-9]|1[0-2])\\/?([0-9]{2})\\b", message = "Enter a cardValidity in xx/yy form")
    private final String cardValidity;
    @Pattern(regexp = "^[0-9]{3}$", message = "CV must be 3 numbers long")
    @Size(min = 3, max = 3, message = "CV must be 3 numbers long")
    private String cv;
    @Size(min = 10, max = 10, message = "Phone number must have 10 numbers")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number can contain only numbers")
    private String phoneNumber;

//---------------> provider
    @Size(min = 1,  max = 100, message = "Car must be between 1-100 chars")
    private final String car;
    @Pattern(regexp = "[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}", message = "Enter IBAN in proper form without spaces")
    private final String IBAN;
    @Size(max = 500, message = "Description can be max 500 chars long")
    private final String description;

}

