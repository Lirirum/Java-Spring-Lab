package com.karacheban.demo.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/medical")
public class MedicalRecordController {
    private final Map<Long, String> records = new HashMap<>();

    @GetMapping("/id")
    public String getRecord(@PathVariable Long id){
        return "Record " +id;
    }
    @GetMapping
    public Map<Long, String> getRecords() {
        return records;
    }
    @PostMapping("/add")
    public String addRecord(@RequestParam String record) {
        long id = records.size() + 1;
        records.put(id, record);
        return "Record added with ID: " + id;
    }

    @PutMapping("/{id}")
    public String updateRecord(@PathVariable Long id, @RequestParam String record) {
        records.put(id, record);
        return "Record updated for ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable Long id) {
        records.remove(id);
        return "Record deleted";
    }
}