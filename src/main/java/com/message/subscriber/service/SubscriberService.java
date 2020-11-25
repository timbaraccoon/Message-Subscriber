package com.message.subscriber.service;

import com.message.subscriber.jsonpojo.Message;

public interface SubscriberService {

    void receiveMessage(Message message);

    void sortMessagesToTargetDB();

}
