package org.worldbuild.aws;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.worldbuild.aws.service.SnsService;
import org.worldbuild.core.config.CoreConfiguration;

@Log4j2
@SpringBootApplication
@Import({CoreConfiguration.class})
public class Application implements CommandLineRunner {

	@Autowired
	private SnsService snsService;

	public static void main(String[] args) {
		log.info("Application is initializing..........");
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Triggering init ..............");
		//snsService.publishToTopic("arn:aws:sns:ap-southeast-1:969695673397:MONGO_ALERT","AWS SNS Service testing");
	}
}
