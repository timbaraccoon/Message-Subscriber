package com.message.subscriber.jsonpojo;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Message {

    private int messageId;
    private int msisdn;
    private String action;
    private Timestamp timestamp;

}
