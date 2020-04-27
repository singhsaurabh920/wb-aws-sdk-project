package org.worldbuild.aws.service;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Log4j2
@Service
public class SecretsManagerServiceImpl implements SecretsManagerService {

    @Autowired
    @Qualifier("awsSecretsManager")
    private AWSSecretsManager awsSecretsManager;

    @Override
    public String getAwsSecret(String secretKey) {
        String secret = null;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretKey);
        GetSecretValueResult getSecretValueResult = null;
        try {
            getSecretValueResult = awsSecretsManager.getSecretValue(getSecretValueRequest);
        } catch (Exception e){
            log.error("SecretsManager exception - ",e);
        }
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
        } else {
            secret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
        }
        log.info(secret);
        return secret;
    }


}
