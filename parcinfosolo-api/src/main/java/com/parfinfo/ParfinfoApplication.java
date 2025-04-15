package com.parfinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.parfinfo.controller.*;
import com.parfinfo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Arrays;

@SpringBootApplication
public class ParfinfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParfinfoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Application démarrée en mode Spring Boot (sans REST)");
            System.out.println("Les contrôleurs suivants sont disponibles :");
            
            // Afficher les contrôleurs disponibles
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                if (beanName.endsWith("Controller")) {
                    System.out.println(" - " + beanName);
                }
            }
            
            System.out.println("\nUtilisez les services via les contrôleurs pour interagir avec l'application.");
        };
    }
} 