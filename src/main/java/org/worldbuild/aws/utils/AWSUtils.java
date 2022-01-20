package org.worldbuild.aws.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import lombok.extern.log4j.Log4j2;
import org.worldbuild.core.modal.EmailModal;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class AWSUtils {

    private static final String SENDER = "xyz@gmail.com";
    private static final String RECIPIENT = "abc@gmail.com";
    //
    private static final String SUBJECT = "Greetings From ISB";
    private static final String ATTACHMENT = "/home/insight/Downloads/PaymentReceipt.pdf";
    private static final String TEXT_CONTENT = "Please see the attached file for a list of customers to contact.";

	/*public static String getSecretV2() {
		String secretName = "prod/gps/db";
		String region = "ap-south-1";
		SecretsManagerClient client = SecretsManagerClient.builder().region(Region.of(region)).build();
		GetSecretValueResponse response = null;
		try {
			response = client.getSecretValue(GetSecretValueRequest.builder().secretId(secretName).build());
		} catch (DecryptionFailureException e) {
			log.error("InternalServiceErrorException",e);
		} catch (InternalServiceErrorException e) {
			log.error("InternalServiceErrorException",e);
		} catch (InvalidParameterException e) {
			log.error("InternalServiceErrorException",e);
		} catch (InvalidRequestException e) {
			log.error("InternalServiceErrorException",e);
		} catch (ResourceNotFoundException e) {
			log.error("InternalServiceErrorException",e);
		}
		String secret=response.secretString();
		log.info(secret);
		return secret;
	}*/


    public static EmailModal sendSampleEmail(){
        EmailModal emailModal=new EmailModal();
        emailModal.setFrom(SENDER);
        emailModal.setTo(RECIPIENT);
        emailModal.setSubject(SUBJECT);
        emailModal.setContent(TEXT_CONTENT);
        emailModal.setTemplateFilePath("email/general");
        emailModal.setAttachment(ATTACHMENT);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Saurabh!");
        model.put("location", "Delhi, India");
        model.put("sign", "Saurabh Singh");
        emailModal.setModal(model);
        return emailModal;
    }
}
