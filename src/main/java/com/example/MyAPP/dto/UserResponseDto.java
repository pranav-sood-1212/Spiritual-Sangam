package com.example.MyAPP.dto;

import java.util.List;

public record UserResponseDto(String username, List<String> roles) {
}
