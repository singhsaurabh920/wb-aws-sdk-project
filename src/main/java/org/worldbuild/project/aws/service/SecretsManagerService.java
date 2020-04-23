package org.worldbuild.project.aws.service;

public interface SecretsManagerService {

    public String getAwsSecret(String secretKey);
}
