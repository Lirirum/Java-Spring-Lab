package com.karacheban.demo.controller;
import com.karacheban.demo.model.Patient;
import com.karacheban.demo.repository.PatientRepository;
import com.karacheban.demo.service.PatientService;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;


import java.util.List;

@Controller
@RequestMapping()
public class PatientController {
    private final PatientRepository patientRepository;
    private  final PatientService patientService;
    private final AuthenticationManager authenticationManager;
    public PatientController(PatientRepository patientRepository, PatientService patientService, AuthenticationManager authenticationManager) {

        this.patientRepository = patientRepository;
        this.patientService= patientService;
        this.authenticationManager= authenticationManager;
    }



    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Невірний email або пароль");
        }
        return "login";
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
    public String registerPatient(@Valid @ModelAttribute Patient patient,Model model) {
        if (patientService.findByEmail(patient.getEmail()).isPresent()) {
            model.addAttribute("error", "Користувач з таким email вже існує");
            return "register";
        }
        patientService.registerPatient(patient);
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String getAllPatients(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        model.addAttribute("title", "Patient List");
        return "PatientList";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/list";
        } catch (Exception e) {
            model.addAttribute("error", "Невірний email або пароль");
            System.out.println(e.getMessage());
            return "login";
        }
    }

    @Data
    class AuthRequest {
        private String username;
        private String password;
    }
}