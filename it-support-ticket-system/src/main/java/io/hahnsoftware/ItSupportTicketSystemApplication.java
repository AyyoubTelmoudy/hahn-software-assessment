package io.hahnsoftware;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@OpenAPIDefinition(info = @Info(title = "IT Support API", version = "1.0", description = "API documentation for the IT Support Ticket System"))
public class ItSupportTicketSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItSupportTicketSystemApplication.class, args);
	}

}
