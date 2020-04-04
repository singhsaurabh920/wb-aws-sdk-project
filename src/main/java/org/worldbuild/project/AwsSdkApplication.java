package org.worldbuild.project;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.worldbuild.project.utils.ScreteManagerUtils;

@Log4j2
@SpringBootApplication
public class AwsSdkApplication {

	public static void main(String[] args) {
		//ScreteManagerUtils.getSecretV1();
		ScreteManagerUtils.getSecretV2();
		SpringApplication.run(AwsSdkApplication.class, args);
	}

}
