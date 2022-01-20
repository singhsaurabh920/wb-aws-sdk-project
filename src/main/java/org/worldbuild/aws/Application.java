package org.worldbuild.aws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.worldbuild.aws.service.EmailService;
import org.worldbuild.aws.service.EmailServiceImpl;
import org.worldbuild.aws.service.SecretsManagerService;
import org.worldbuild.aws.service.SnsService;
import org.worldbuild.aws.utils.AWSUtils;
import org.worldbuild.core.config.CoreConfiguration;
import org.worldbuild.core.modal.EmailModal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
@Log4j2
@SpringBootApplication
@Import({CoreConfiguration.class})
public class Application implements CommandLineRunner  {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SnsService snsService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private SecretsManagerService secretsManagerService;
	@Autowired
	@Qualifier("scheduledExecutorService")
	private ScheduledExecutorService scheduledExecutorService;

	public static void main(String[] args) {
		log.info("Application is initializing..........");
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Triggering init ..............");
		secretsManagerService.getAwsSecret("spring.data.mongodb.database");
		String secret = secretsManagerService.getAwsSecret("spring.data.mongodb");
		DBCredential dbCredential = objectMapper.convertValue(secret,DBCredential.class);
		log.info(dbCredential);
		//scheduledExecutorService.scheduleWithFixedDelay(()-> doJob(),10L,10L, TimeUnit.SECONDS);
		//emailService.sendEmail(AWSUtils.sendSampleEmail());
		//snsService.publishToTopic(AWSUtils.SNS_ARN,"AWS SNS Service testing");
	}

	public void doJob() {
		log.info("Current Time ===> {}",new Date());
	}

	@AllArgsConstructor
	@NoArgsConstructor
	public static class DBCredential {
		private String username;
		private String password;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

}
