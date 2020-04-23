package org.worldbuild.project.aws.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import lombok.extern.log4j.Log4j2;
import java.util.Base64;

@Log4j2
public class AWSUtils {

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
}
