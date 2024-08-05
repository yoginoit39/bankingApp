package org.bankingapp.bankingapplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "My Banking APP",
                description = "Backend REST APIs for the application",
                contact = @Contact(
                        name = "Yogesh",
                        email = "lakhaniy540@gmail.com",
                        url = "https://github/yoginoit39/BankingApp"
                ),
                license = @License(
                        name = "Yogesh Lakhani",
                        url = "https://github/yoginoit39/BankingApp"
                )

        ),
        externalDocs = @ExternalDocumentation(
                description = "Banking Application Documentation",
                url = "https://github/yoginoit39/BankingApp"
        )
)
public class BankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

}
