package com.example.userLogin.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class OrderRequest {
    @NotBlank(message = "Please enter your email")
    @Pattern(regexp = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")
    private final String customer;
    @NotBlank(message = "Please enter a provider")
    @Pattern(regexp = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")
    private final String provider;
    @NotBlank(message = "Please enter actual destination")
    private final String aDestination;
    @NotBlank(message = "Please enter final destination")
    private final String fDestination;
}
