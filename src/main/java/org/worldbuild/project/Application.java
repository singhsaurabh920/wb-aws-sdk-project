package org.worldbuild.project;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.worldbuild.project.aws.service.SecretsManagerService;
import org.worldbuild.project.aws.service.SnsService;

@Log4j2
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private SnsService snsService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Triggering alert ..............");
		snsService.publishToTopic("arn:aws:sns:ap-southeast-1:969695673397:MONGO_ALERT","AWS SNS Service testing");
	}
}
