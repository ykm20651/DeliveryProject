package com.example.deliveryproject.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @NotBlank(message = "이름은 필수 항목입니다.")
        String name,

        @NotBlank(message = "이메일은 필수 항목입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", message = "비밀번호는 최소 8자 이상이어야 하며, 문자와 숫자를 포함해야 합니다.")
        String password,

        @NotBlank(message = "전화번호는 필수 항목입니다.")
        @Pattern(regexp = "^\\d{10,11}$", message = "전화번호는 10~11자리 숫자여야 합니다.")
        String phoneNumber
) {
}
