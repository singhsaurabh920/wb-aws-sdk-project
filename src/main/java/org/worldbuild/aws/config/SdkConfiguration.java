package org.worldbuild.aws.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Log4j2
@Configuration
public class SdkConfiguration {

    public static final String AWS_SECRET = "prod/gps/db";
    public static final String AWS_REGION = "ap-southeast-1";

    @Profile("dev")
    @Bean("amazonSNS")
    public AmazonSNS amazonSNSDev(){
        /*BasicAWSCredentials basicAwsCredentials = new BasicAWSCredentials("accessKey","secretKey");
        AWSCredentialsProvider awsCredentialsProvider=new AWSStaticCredentialsProvider(basicAwsCredentials);*/
        return  AmazonSNSClientBuilder
                .standard()
                .withRegion(AWS_REGION)
                .build();
    }

    @Profile("prod")
    @Bean("amazonSNS")
    public AmazonSNS amazonSNSProd(){
        return AmazonSNSClient
                .builder()
                .withRegion(AWS_REGION)
                .withCredentials(new InstanceProfileCredentialsProvider(true))
                .build();
    }

    @Bean("awsSecretsManager")
    public AWSSecretsManager awsSecretsManager() {
        return AWSSecretsManagerClientBuilder
                .standard()
                .withRegion(AWS_REGION)
                .build();
    }
}
