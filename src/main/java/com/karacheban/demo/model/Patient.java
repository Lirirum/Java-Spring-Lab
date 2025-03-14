package com.karacheban.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ім'я обов'язкове")
    private String firstName;

    @NotBlank(message = "Прізвище обов'язкове")
    private String lastName;

    @Transient // Поле не зберігається в БД
    private String fullName;

    @NotBlank(message = "Email обов'язковий")
    @Email
    @Column(unique = true)
    private String email;

    @NotNull(message = "Вік обов'язковий")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private  Integer age;

    @NotBlank(message = "Пароль обов'язковий")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getUsername() {
        return email; // Використовуємо email як ім'я користувача
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
