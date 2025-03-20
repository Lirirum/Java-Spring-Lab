package com.karacheban.demo.aspect;

import com.karacheban.demo.service.EmailService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

@Aspect
@Component
public class CategoryAspect {

    private final EmailService emailService;

    @Autowired
    public CategoryAspect(EmailService emailService) {
        this.emailService = emailService;
    }

    @Before("execution(* com.karacheban.demo.service.MedicalRecordService.saveRecord(..)) && args(medicalRecord)")
    public void setDefaultCategory(JoinPoint joinPoint, Object medicalRecord) {
        try {
            Field categoryField = medicalRecord.getClass().getDeclaredField("category");
            categoryField.setAccessible(true);
            String currentCategory = (String) categoryField.get(medicalRecord);

            StringBuilder details = new StringBuilder();
            details.append("<h3>Category Aspect Execution</h3>");
            details.append("<p>Method: ").append(joinPoint.getSignature()).append("</p>");
            details.append("<p>Timestamp: ").append(new Date()).append("</p>");

            if (currentCategory == null || currentCategory.isEmpty()) {
                categoryField.set(medicalRecord, "General");
                System.out.println("The category 'General' is set for the entry: " + medicalRecord);

                details.append("<p>Action: Default category 'General' was set for record</p>");
                details.append("<p>Record: ").append(medicalRecord).append("</p>");
            } else {
                details.append("<p>Action: No changes made (Category already set)</p>");
                details.append("<p>Current Category: ").append(currentCategory).append("</p>");
            }

            // Send email notification about aspect execution
            emailService.sendAspectExecutionNotification("CategoryAspect", details.toString());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error setting category: " + e.getMessage());
        }
    }
}