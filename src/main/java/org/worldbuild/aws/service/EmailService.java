package org.worldbuild.aws.service;

import com.amazonaws.services.simpleemail.model.SendRawEmailResult;
import org.worldbuild.core.modal.EmailModal;

import javax.mail.MessagingException;
import java.util.concurrent.CompletableFuture;

public interface EmailService {

    public CompletableFuture<SendRawEmailResult> sendEmail(EmailModal emailModal) throws MessagingException;

}
