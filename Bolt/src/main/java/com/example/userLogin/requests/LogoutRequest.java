package com.example.userLogin.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LogoutRequest {
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Enter an email in proper form")
    @Pattern(regexp = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")
    private final String email;

    @NotBlank(message = "Password can not be blank")
    @Size(min = 6, max = 100, message = "Password must be beetwen 6-100 chars long")
    private final String password;

}
