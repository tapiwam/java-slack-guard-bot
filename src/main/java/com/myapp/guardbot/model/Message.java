package com.myapp.guardbot.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class Message {

    private String eventId;
    private String text;
    private String channel;
    private String sender;
    private String senderName;
    private String senderEmail;
    private Boolean isProfane;
    private Sentiment sentiment;
    private String msgTs;
    private LocalDateTime createTs;

    public Message() {
    }

    public Message(String eventId, String text, String channel, String sender, String senderName, String senderEmail, Boolean isProfane, Sentiment sentiment, String msgTs, LocalDateTime createTs) {
        this.eventId = eventId;
        this.text = text;
        this.channel = channel;
        this.sender = sender;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.isProfane = isProfane;
        this.sentiment = sentiment;
        this.msgTs = msgTs;
        this.createTs = createTs;
    }
}
