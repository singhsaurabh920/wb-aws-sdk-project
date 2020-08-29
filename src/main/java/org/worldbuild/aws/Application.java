package org.worldbuild.aws;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.worldbuild.aws.service.SnsService;
import org.worldbuild.core.config.CoreConfiguration;
import org.worldbuild.core.modal.EmailModal;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@SpringBootApplication
@Import({CoreConfiguration.class})
public class Application implements CommandLineRunner {
	private static final String SENDER = "xyz@gmail.com";
	private static final String RECIPIENT = "abc920@gmail.com";
	//
	private static final String SUBJECT = "Greetings From ISB";
	private static final String ATTACHMENT = "/home/insight/Downloads/PaymentReceipt.pdf";

	private static String BODY_TEXT = "Please see the attached file for a list of customers to contact.";

	private static String BODY_HTML = "<html>"
			+ "<head></head>"
			+ "<body>"
			+ "<h1>Hello!</h1>"
			+ "<p>Please see the attached file for a "
			+ "list of customers to contact.</p>"
			+ "</body>"
			+ "</html>";

	@Autowired
	private SnsService snsService;
	@Autowired
	private EmailService emailService;

	public static void main(String[] args) {
		log.info("Application is initializing..........");
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Triggering init ..............");
		EmailModal emailModal=new EmailModal();
		emailModal.setFrom(SENDER);
		emailModal.setTo(RECIPIENT);
		emailModal.setSubject(SUBJECT);
		emailModal.setContent(BODY_TEXT);
		emailModal.setTemplateFilePath("email/general");
		emailModal.setAttachment(ATTACHMENT);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("name", "Saurabh!");
		model.put("location", "Delhi, India");
		model.put("sign", "Saurabh Singh");
		emailModal.setModal(model);
		emailService.sendEmail(emailModal);
		log.info("Email Sent");
		//snsService.publishToTopic("arn:aws:sns:ap-southeast-1:969695673397:MONGO_ALERT","AWS SNS Service testing");
	}
}
