package io.hahnsoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ItSupportTicketSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItSupportTicketSystemApplication.class, args);
	}

}
