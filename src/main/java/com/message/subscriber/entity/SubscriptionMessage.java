package com.message.subscriber.entity;

import com.message.subscriber.jsonpojo.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SubscriptionMessage extends AbstractMessage {

    private String action;

    public SubscriptionMessage() {
    }

    public SubscriptionMessage(int messageId, int msisdn, Timestamp timestamp, String action) {
        super(messageId, msisdn, timestamp);
        if (action.equalsIgnoreCase("SUBSCRIPTION")) {
            this.action = action;
        } else {
            throw new IllegalArgumentException("SubscriptionMessage must have SUBSCRIPTION action");
        }
    }

    public SubscriptionMessage(Message message) {
        super(message.getMessageId(), message.getMsisdn(), message.getTimestamp());
        if (message.getAction().equalsIgnoreCase("SUBSCRIPTION")) {
            this.action = message.getAction();
        } else {
            throw new IllegalArgumentException("SubscriptionMessage must have SUBSCRIPTION action");
        }

    }

}
