package com.example.userLogin.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class DeleteOrderRequest {
    @NotBlank(message = "Enter a providers email")
    private final String provider;
    @NotNull(message = "Enter a number of order that you want to delete")
    private final Long id;


}
