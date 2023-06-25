package org.borave;

import org.borave.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
@ComponentScan(basePackages = "org.borave")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
