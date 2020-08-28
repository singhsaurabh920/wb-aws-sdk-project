package org.worldbuild.aws.service;

import org.worldbuild.core.modal.EmailModal;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface EmailService {

    public void sendEmail(EmailModal emailModal) throws MessagingException;

}
