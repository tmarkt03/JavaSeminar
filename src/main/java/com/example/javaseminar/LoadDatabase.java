package com.example.javaseminar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(PersonRepository repository) {
        return args -> {
            if(false){
                repository.save(new Person("Peter Grant", "Kecskemet", 35, 77.5));
                repository.save(new Person("Kate Davis", "Szeged", 22, 72.3));
            }
        };
    }
}
