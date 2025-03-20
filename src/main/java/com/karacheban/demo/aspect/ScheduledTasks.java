package com.karacheban.demo.aspect;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ScheduledTasks {

    @Scheduled(initialDelay = 400, fixedRate = 2500)
    public void logThemeInformation() {
        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append("\n==== Theme Information ====\n");
        infoBuilder.append("Time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\n\n");

        // Information about the first aspect
        infoBuilder.append("Medical Operation:\n");



        // Print the information to the console
        System.out.println(infoBuilder.toString());
}
}