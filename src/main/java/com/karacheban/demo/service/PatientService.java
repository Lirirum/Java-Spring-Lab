package com.karacheban.demo.service;

import com.karacheban.demo.model.Patient;
import com.karacheban.demo.model.Role;
import com.karacheban.demo.repository.PatientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder) {

        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Patient registerPatient(Patient patient) {
        patient.setPassword(passwordEncoder.encode(patient.getPassword())); // Хешуємо пароль
        patient.setRole(Role.USER);
        return patientRepository.save(patient);
    }
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    }
}
