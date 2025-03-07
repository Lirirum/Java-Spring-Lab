package com.karacheban.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientDto {
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
}
