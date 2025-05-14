package tn.ensit.soa.UserSOA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class UserSoaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSoaApplication.class, args);
	}

}
