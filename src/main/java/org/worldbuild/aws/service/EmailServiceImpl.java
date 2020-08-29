package org.worldbuild.aws.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailResult;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
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
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class EmailServiceImpl implements EmailService{
    private static final String CONFIGURATION_SET = "ConfigSet";

    @Autowired
    private VelocityEngine velocityEngine;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Autowired
    @Qualifier("awsSimpleEmailService")
    private AmazonSimpleEmailService amazonSimpleEmailService;

    @Async
    @Override
    public CompletableFuture<SendRawEmailResult> sendEmail(EmailModal emailModal) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.setSubject(emailModal.getSubject(), "UTF-8");
        message.setFrom(new InternetAddress(emailModal.getFrom()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailModal.getTo()));
        MimeMultipart container = new MimeMultipart("mixed");
        setContent(emailModal,container);
        message.setContent(container);
        SendRawEmailResult result = sendEmail(message);
        return CompletableFuture.completedFuture(result);
    }
    private void setContent(EmailModal emailModal,MimeMultipart container ) throws MessagingException {
        MimeMultipart body = new MimeMultipart("mixed");
        setTextContent(emailModal,body);
        setHtmlContent(emailModal,body);
        MimeBodyPart content = new MimeBodyPart();
        content.setContent(body);
        container.addBodyPart(content);
        setAttachment(emailModal,container);
    }
    private void setTextContent(EmailModal emailModal,MimeMultipart body ) {
        try {
            final String content = emailModal.getContent();
            if (StringUtils.isNoneEmpty(content)) {
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setContent(content, "text/plain; charset=UTF-8");
                body.addBodyPart(textPart);
            }
        }catch (Exception ex){
            log.error("Text Set Exception: ",ex);
        }
    }

    private void setHtmlContent(EmailModal emailModal,MimeMultipart body ) {
        try {
            final Map<String,Object> modal = emailModal.getModal();
            final String templateFilePath = emailModal.getTemplateFilePath();
            if (StringUtils.isNoneEmpty(templateFilePath)) {
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlStr = convertThymeleafToString(templateFilePath,modal);
                htmlPart.setContent(htmlStr, "text/html; charset=UTF-8");
                body.addBodyPart(htmlPart);
            }
        }catch (Exception ex){
            log.error("Html Set Exception: ",ex);
        }
    }

    private String convertTemplateToString(String templateFilePath, Map<String,Object> modal) {
        Template template=velocityEngine.getTemplate(templateFilePath, StandardCharsets.UTF_8.name());
        StringWriter writer = new StringWriter();
        VelocityContext context = new VelocityContext(modal);
        template.merge(context, writer);
        return writer.toString();
    }

    private String convertThymeleafToString(String templateFilePath, Map<String,Object> modal) {
        Context context = new Context();
        context.setVariables(modal);
        return templateEngine.process(templateFilePath, context);
    }

    private void setAttachment(EmailModal emailModal,MimeMultipart container ){
        try {
            final String attachment = emailModal.getAttachment();
            if (StringUtils.isNoneEmpty(attachment)) {
                MimeBodyPart att = new MimeBodyPart();
                DataSource fds = new FileDataSource(attachment);
                att.setDataHandler(new DataHandler(fds));
                att.setFileName(fds.getName());
                container.addBodyPart(att);
            }
        }catch (Exception ex){
            log.error("Attachment Set Exception: ",ex);
        }
    }

    public SendRawEmailResult  sendEmail(final MimeMessage message){
        SendRawEmailResult result = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);//.withConfigurationSetName(CONFIGURATION_SET);
            result = amazonSimpleEmailService.sendRawEmail(rawEmailRequest);
            log.info("Email response: "+result);
        }catch (Exception ex){
            log.error("Email Sent Exception: ",ex);
        }
        return result;
    }
}
