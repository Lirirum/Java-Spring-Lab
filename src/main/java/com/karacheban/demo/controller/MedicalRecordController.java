package com.karacheban.demo.controller;
import com.karacheban.demo.model.MedicalRecord;
import com.karacheban.demo.model.Patient;
import com.karacheban.demo.service.MedicalRecordService;
import com.karacheban.demo.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final PatientService patientService;
    public MedicalRecordController(MedicalRecordService medicalRecordService, PatientService patientService) {
        this.medicalRecordService = medicalRecordService;
        this.patientService = patientService;
    }


    // Отримати всі записи
    @GetMapping
    public String getAllRecords(Model model) {
        List<MedicalRecord> records = medicalRecordService.getAllRecords();
        model.addAttribute("records", records);
        model.addAttribute("title", "Medical Records");
        return "medical-records";
    }

    // Форма для додавання нового запису
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("medicalRecord", new MedicalRecord());
        model.addAttribute("title", "Add Medical Record");
        model.addAttribute("patients", patientService.getAllPatients());
        return "medical-record-form";
    }

    // Додавання запису
    @PostMapping("/add")
    public String addRecord(@Valid @ModelAttribute MedicalRecord medicalRecord, BindingResult result) {
        if (result.hasErrors()) {
            return "medical-record-form";
        }
        medicalRecordService.saveRecord(medicalRecord);
        return "redirect:/medical-records";
    }

    // Форма для оновлення запису
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MedicalRecord record = medicalRecordService.getRecordById(id);
        model.addAttribute("medicalRecord", record);
        model.addAttribute("title", "Edit Medical Record");
        List<Patient> patients =patientService.getAllPatients();
        model.addAttribute("patients", patients);
        return "medical-record-edit";
    }

    // Оновлення запису
    @PutMapping("/edit/{id}")
    public String updateRecord(@PathVariable Long id, @Valid @ModelAttribute MedicalRecord medicalRecord,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Edit Medical Record");
            return "medical-record-edit";
        }
        medicalRecord.setId(id);
        medicalRecordService.saveRecord(medicalRecord);
        return "redirect:/medical-records";
    }


    // Видалення запису
    @GetMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Long id) {
        medicalRecordService.deleteRecord(id);
        return "redirect:/medical-records";
    }




}