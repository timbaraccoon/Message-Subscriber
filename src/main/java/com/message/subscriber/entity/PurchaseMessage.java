package com.message.subscriber.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PurchaseMessage extends AbstractMessage {

    private String action;

    public PurchaseMessage() {
    }

    public PurchaseMessage(int messageId, int msisdn, Timestamp timestamp, String action) {
        super(messageId, msisdn, timestamp);
        if (action.equalsIgnoreCase("PURCHASE")) {
            this.action = action;
        } else {
            throw new IllegalArgumentException("PurchaseMessage must have PURCHASE action");
        }
    }

    public PurchaseMessage(Message message) {
        super(message.getMessageId(), message.getMsisdn(), message.getTimestamp());
        if (message.getAction().equalsIgnoreCase("PURCHASE")) {
            this.action = message.getAction();
        } else {
            throw new IllegalArgumentException("PurchaseMessage must have PURCHASE action");
        }

    }

}
