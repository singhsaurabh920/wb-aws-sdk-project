package org.worldbuild.aws.service;

public interface SecretsManagerService {

    public String getAwsSecret(String secretKey);
}
