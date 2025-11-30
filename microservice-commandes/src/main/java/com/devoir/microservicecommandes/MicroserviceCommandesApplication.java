package com.devoir.microservicecommandes;

import com.devoir.microservicecommandes.model.Commande;
import com.devoir.microservicecommandes.repository.CommandeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
//@EnableDiscoveryClient
public class MicroserviceCommandesApplication {
    public static void main(String[] args) {

        SpringApplication.run(MicroserviceCommandesApplication.class, args);
   }

   @Bean
    CommandLineRunner run(CommandeRepository commandeRepository) {
        return args -> {
            commandeRepository.deleteAll();
            //commandeRepository.save(Commande.builder().description("JHGDFGSDF").build());
        };
    }
}
