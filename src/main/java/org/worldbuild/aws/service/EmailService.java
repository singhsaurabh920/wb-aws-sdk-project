package org.worldbuild.aws.service;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    public void sendEmail() throws MessagingException, IOException;

}
