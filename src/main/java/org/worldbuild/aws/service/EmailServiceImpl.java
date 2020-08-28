package org.worldbuild.aws.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.worldbuild.core.modal.EmailModal;

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

    @Autowired
    @Qualifier("awsSimpleEmailService")
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public void sendEmail(EmailModal emailModal) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSubject(emailModal.getSubject(), "UTF-8");
        message.setFrom(new InternetAddress(emailModal.getFrom()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailModal.getTo()));
        MimeMultipart msg = new MimeMultipart("mixed");
        setContent(emailModal,msg);
        message.setContent(msg);
        sendEmail(message);
    }
    private void setContent(EmailModal emailModal,MimeMultipart msg ) throws MessagingException {
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(emailModal.getContent(), "text/plain; charset=UTF-8");
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(emailModal.getTemplate(),"text/html; charset=UTF-8");
        MimeMultipart msg_body = new MimeMultipart("mixed");
        msg_body.addBodyPart(textPart);
        msg_body.addBodyPart(htmlPart);
        MimeBodyPart content = new MimeBodyPart();
        content.setContent(msg_body);
        msg.addBodyPart(content);
        setAttachment(emailModal,msg);
    }
    private void setAttachment(EmailModal emailModal,MimeMultipart msg ){
        try {
            final String attachment = emailModal.getAttachment();
            if (StringUtils.isNoneEmpty(attachment)) {
                MimeBodyPart att = new MimeBodyPart();
                DataSource fds = new FileDataSource(attachment);
                att.setDataHandler(new DataHandler(fds));
                att.setFileName(fds.getName());
                msg.addBodyPart(att);
            }
        }catch (Exception ex){
            log.error("Attachment Set Exception: ",ex);
        }
    }

    private void sendEmail(final MimeMessage message){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);//.withConfigurationSetName(CONFIGURATION_SET);
            amazonSimpleEmailService.sendRawEmail(rawEmailRequest);
            log.info("Email sent!");
        }catch (Exception ex){
            log.error("Email Sent Exception: ",ex);
        }
    }
}
