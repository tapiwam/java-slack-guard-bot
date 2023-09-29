package com.myapp.guardbot.entity;

import com.myapp.guardbot.model.Sentiment;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "message")
@Data
@Builder
@ToString
public class Message {

    @Id
    @GeneratedValue
    private Long id;
    private String eventId;
    private String text;
    private String channel;
    private String sender;
    private String senderName;
    private String senderEmail;
    private Boolean isProfane;
    @Enumerated(EnumType.STRING) private Sentiment sentiment;
    private String msgTs;
    private LocalDateTime createTs;

    public Message() {
    }

    public Message(Long id, String eventId, String text, String channel, String sender, String senderName, String senderEmail, Boolean isProfane, Sentiment sentiment, String msgTs, LocalDateTime createTs) {
        this.id = id;
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
