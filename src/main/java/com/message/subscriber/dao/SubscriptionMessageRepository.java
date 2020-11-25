package com.message.subscriber.dao;

import com.message.subscriber.entity.SubscriptionMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionMessageRepository extends JpaRepository<SubscriptionMessage, Integer> {

}
