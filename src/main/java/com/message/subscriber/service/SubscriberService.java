package com.message.subscriber.service;

import com.message.subscriber.entity.Message;

public interface SubscriberService {

    void receiveMessage(Message message);

    void sortMessagesToTargetDB();

}
