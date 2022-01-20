package org.worldbuild.aws.service;

public interface SecretsManagerService {

    String getAwsSecret(String secretKey);
}
