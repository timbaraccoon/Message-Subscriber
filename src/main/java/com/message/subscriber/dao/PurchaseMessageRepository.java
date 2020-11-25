package com.message.subscriber.dao;

import com.message.subscriber.entity.PurchaseMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseMessageRepository extends JpaRepository<PurchaseMessage, Integer> {

}
