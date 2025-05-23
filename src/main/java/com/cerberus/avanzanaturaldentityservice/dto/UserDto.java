package com.cerberus.avanzanaturaldentityservice.dto;

import com.cerberus.avanzanaturaldentityservice.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {

    private final static Long SerialVersionUID= 1L;

    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Length(min = 2, max = 30, message = "Минимальная длина имени составляет 2 символа, максимальная - 30 символов")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Length(min = 2, max = 30, message = "Минимальная длина фамилии составляет 2 символа, максимальная - 30 символов")
    private String lastName;

    @NotBlank(message = "Email не может быть пустым")
    @Email
    @Length(min = 5, max = 50, message = "Минимальная длина email составляет 5 символа, максимальная - 50 символов")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    private Boolean emailConfirmed;

    @NotNull(message = "Роль не может быть пустой")
    private Role role;
}
