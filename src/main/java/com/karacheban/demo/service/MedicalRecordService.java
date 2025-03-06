package com.karacheban.demo.service;

import com.karacheban.demo.model.MedicalRecord;
import com.karacheban.demo.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public List<MedicalRecord> getAllRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getRecordById(Long id) {
        return medicalRecordRepository.findById(id).orElseThrow(() -> new RuntimeException("Record not found"));
    }

    public void saveRecord(MedicalRecord record) {
        medicalRecordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }
}