package com.message.subscriber.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class AbstractMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "counter")
    private int counter;

    @Column(name = "message_id")
    private int messageId;

    @Column(name = "msisdn")
    private int msisdn;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public AbstractMessage(int messageId, int msisdn, Timestamp timestamp) {
        this.messageId = messageId;
        this.msisdn = msisdn;
        this.timestamp = timestamp;
    }
}
