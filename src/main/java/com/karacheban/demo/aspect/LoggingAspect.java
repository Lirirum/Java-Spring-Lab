package com.karacheban.demo.aspect;

import com.karacheban.demo.service.EmailService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LoggingAspect {

    private final EmailService emailService;

    @Autowired
    public LoggingAspect(EmailService emailService) {
        this.emailService = emailService;
    }

    @Pointcut("within(com.karacheban.demo.controller.MedicalRecordController)")
    public void controllerMethods() {}

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logMethodArguments(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        System.out.println("Method " + methodName + " executed with arguments:");
        for (int i = 0; i < args.length; i++) {
            System.out.println("Argument " + (i+1) + ": " + args[i]);
        }
        System.out.println("Result: " + result);

        // Build email content
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<h3>Controller Method Execution Log</h3>");
        emailContent.append("<p>Method: ").append(methodName).append("</p>");
        emailContent.append("<p>Timestamp: ").append(new Date()).append("</p>");
        emailContent.append("<h4>Arguments:</h4>");
        emailContent.append("<ul>");

        for (int i = 0; i < args.length; i++) {
            emailContent.append("<li>Argument ").append(i+1).append(": ").append(args[i]).append("</li>");
        }

        emailContent.append("</ul>");
        emailContent.append("<h4>Result:</h4>");
        emailContent.append("<p>").append(result).append("</p>");

        // Send email notification
        emailService.sendAspectExecutionNotification("LoggingAspect", emailContent.toString());
    }
}