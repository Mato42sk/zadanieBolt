package com.example.userLogin.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DeleteUserRequest {
    @NotBlank(message = "Enter an email of user that you want to delete")
    private final String email;
    @NotBlank(message = "Enter password of user that you want to delete")
    private final String password;
}
