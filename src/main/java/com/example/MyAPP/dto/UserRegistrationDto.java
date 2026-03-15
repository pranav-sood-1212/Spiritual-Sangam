package com.example.MyAPP.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(
        @NotBlank String username,
        @Size(min = 6) String password
){

}
