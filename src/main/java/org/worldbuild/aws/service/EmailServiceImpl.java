package org.worldbuild.aws.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

@Log4j2
@Service
public class EmailServiceImpl implements EmailService{

    private static final String CONFIGURATION_SET = "ConfigSet";
    private static final String SENDER = "singhsaurabh920@gmail.com";
    private static final String RECIPIENT = "savitasinghkhanpur@gmail.com";
    //
    private static final String SUBJECT = "WB Sample Doc ";
    private static final String ATTACHMENT = "/home/insight/Downloads/Saurabh_Summary.xlsx";


    @Autowired
    @Qualifier("awsSimpleEmailService")
    private AmazonSimpleEmailService amazonSimpleEmailService;


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

    @Override
    public void sendEmail() throws MessagingException, IOException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSubject(SUBJECT, "UTF-8");
        message.setFrom(new InternetAddress(SENDER));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(RECIPIENT));
        MimeMultipart msg_body = new MimeMultipart("alternative");
        MimeBodyPart wrap = new MimeBodyPart();
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(BODY_TEXT, "text/plain; charset=UTF-8");
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(BODY_HTML,"text/html; charset=UTF-8");
        msg_body.addBodyPart(textPart);
        msg_body.addBodyPart(htmlPart);
        wrap.setContent(msg_body);
        MimeMultipart msg = new MimeMultipart("mixed");
        message.setContent(msg);
        msg.addBodyPart(wrap);
        MimeBodyPart att = new MimeBodyPart();
        DataSource fds = new FileDataSource(ATTACHMENT);
        att.setDataHandler(new DataHandler(fds));
        att.setFileName(fds.getName());
        msg.addBodyPart(att);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
        SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);//.withConfigurationSetName(CONFIGURATION_SET);
        amazonSimpleEmailService.sendRawEmail(rawEmailRequest);
        log.info("Email sent!");
    }
}
