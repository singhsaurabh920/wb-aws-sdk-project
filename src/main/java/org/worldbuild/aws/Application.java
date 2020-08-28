package org.worldbuild.aws;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.worldbuild.aws.service.EmailService;
import org.worldbuild.aws.service.SnsService;
import org.worldbuild.core.config.CoreConfiguration;
import org.worldbuild.core.modal.EmailModal;

@Log4j2
@SpringBootApplication
@Import({CoreConfiguration.class})
public class Application implements CommandLineRunner {
	private static final String SENDER = "xyz@gmail.com";
	private static final String RECIPIENT = "abc@gmail.com";
	//
	private static final String SUBJECT = "WB Sample Doc ";
	private static final String ATTACHMENT = "/home/insight/Downloads/PaymentReceipt.pdf";

	private static String BODY_TEXT = "Hello,\r\n"
			+ "Please see the attached file for a list "
			+ "of customers to contact.";

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
		emailModal.setTemplate(BODY_HTML);
		emailModal.setAttachment(ATTACHMENT);
		//emailService.sendEmail(emailModal);
		//snsService.publishToTopic("arn:aws:sns:ap-southeast-1:969695673397:MONGO_ALERT","AWS SNS Service testing");
	}
}
