package com.karacheban.demo.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Integer age;
}
