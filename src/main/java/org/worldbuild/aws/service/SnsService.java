package org.worldbuild.aws.service;

import com.amazonaws.services.sns.model.*;

import java.util.Map;

public interface SnsService {

    CreateTopicResult createSNSTopic(String name);

    DeleteTopicResult deleteSNSTopic(String topicArn);

    SubscribeResult subscribeToTopic(String topicArn, String protocol, String endpoint);

    PublishResult publishToTopic(String topicArn, String message);

    PublishResult publishToDirectSMS(String message, String phoneNumber, Map<String, MessageAttributeValue> smsAttributes);

}
