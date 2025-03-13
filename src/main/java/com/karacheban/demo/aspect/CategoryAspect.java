package com.karacheban.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class CategoryAspect {
    @Before("execution(* com.karacheban.demo.service.MedicalRecordService.saveRecord(..)) && args(medicalRecord)")
    public void setDefaultCategory(JoinPoint joinPoint, Object medicalRecord) {
        try {

            Field categoryField = medicalRecord.getClass().getDeclaredField("category");
            categoryField.setAccessible(true);

            String currentCategory = (String) categoryField.get(medicalRecord);
            if (currentCategory == null || currentCategory.isEmpty()) {
                categoryField.set(medicalRecord, "General");
                System.out.println("The category 'General' is set for the entry: " + medicalRecord);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error setting category: " + e.getMessage());
        }
    }
}
