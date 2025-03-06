package com.karacheban.demo.controller;
import com.karacheban.demo.model.Patient;
import com.karacheban.demo.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;


import java.util.List;

@Controller
@RequestMapping("patients")
public class PatientController {
    private final PatientRepository patientRepository;
    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }



    @PostMapping("/login")
    public String loginUser(@RequestParam String username) {
        return "Login successful for user: " + username;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Головна сторінка");
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "registerPatient";
    }

    @PostMapping("/register")
    public String registerPatient(@Valid @ModelAttribute Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            return "registerPatient";
        }
        System.out.println(patient);
        patientRepository.save(patient);
        return "redirect:/patients/list";
    }

    @GetMapping("/list")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        model.addAttribute("title", "Patient List");
        return "PatientList";
    }
}