package org.worldbuild.project.aws.service;

import java.util.Map;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import com.amazonaws.services.sns.model.DeleteTopicResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;

@Log4j2
@Service
public class SnsServiceImpl implements SnsService {

	@Autowired
	@Qualifier("amazonSNS")
	private AmazonSNS amazonSNS;

	@Override
	public CreateTopicResult createSNSTopic(String name) {
		CreateTopicResult createTopicResult = null;
		try {
			final CreateTopicRequest createTopicRequest = new CreateTopicRequest(name);
			createTopicResult = amazonSNS.createTopic(createTopicRequest);
			log.info("Create topic response - " + createTopicResult);
		} catch (Exception e) {
			log.error("Create topic exception- ", e);
		}
		return createTopicResult;

	}

	@Override
	public DeleteTopicResult deleteSNSTopic(String topicArn) {
		DeleteTopicResult deleteTopicResult = null;
		try {
			final DeleteTopicRequest deleteTopicRequest = new DeleteTopicRequest(topicArn);
			deleteTopicResult = amazonSNS.deleteTopic(deleteTopicRequest);
			log.info("Delete topic response - " + deleteTopicResult);
		} catch (Exception e) {
			log.error("Delete topic exception- ", e);
		}
		return deleteTopicResult;
	}

	@Override
	public SubscribeResult subscribeToTopic(String topicArn, String protocol, String endpoint) {
		SubscribeResult subscribeResult = null;
		try {
			SubscribeRequest subscribeRequest = new SubscribeRequest(topicArn, protocol, endpoint);
			subscribeResult = amazonSNS.subscribe(subscribeRequest);
			log.info("Subscribe response - " + subscribeResult);
		} catch (Exception e) {
			log.error("Subscribe topic exception- ", e);
		}
		return subscribeResult;
	}

	@Override
	public PublishResult publishToTopic(String topicArn, String message) {
		PublishResult publishResult = null;
		try {
			PublishRequest publishRequest = new PublishRequest()
					.withTopicArn(topicArn)
					.withMessage(message);
			publishResult = amazonSNS.publish(publishRequest);
			log.info("Publish topic response - " + publishResult);
		} catch (Exception e) {
			log.error("Publish topic exception- ", e);
		}
		return publishResult;
	}

	@Override
	public PublishResult publishToDirectSMS(String message, String phoneNumber,Map<String, MessageAttributeValue> smsAttributes) {
		PublishResult publishResult = null;
		try {
			PublishRequest publishRequest = new PublishRequest()
					.withMessage(message)
					.withPhoneNumber(phoneNumber)
					.withMessageAttributes(smsAttributes);
			publishResult = amazonSNS.publish(publishRequest);
		log.info("Publish direct sms response - " + publishResult);
		} catch (Exception e) {
			log.error("Publish direct sms exception- ", e);
		}
		return publishResult;
	}

	/*
	 * public void sendSmapleMassage(){ Map<String, MessageAttributeValue>
	 * smsAttributes=new HashMap<>(); smsAttributes.put("AWS.SNS.SMS.SenderID", new
	 * MessageAttributeValue() .withStringValue("TB0012") //The sender ID shown on
	 * the device. .withDataType("String"));
	 * smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
	 * .withStringValue("0.50") //Sets the max price to 0.50 USD.
	 * .withDataType("Number")); smsAttributes.put("AWS.SNS.SMS.SMSType", new
	 * MessageAttributeValue() .withStringValue("Promotional") //Sets the type to
	 * promotional. .withDataType("String"));
	 * publishToDirectSMS("MONGO_ALERTS","+919125959953",smsAttributes); }
	 */

}
