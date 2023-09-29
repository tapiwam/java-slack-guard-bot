package com.myapp.guardbot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "member")
@Data
@Builder
@ToString
public class Member {

    @Id
    private String slackUserId;

    private String name;
    private String email;
    private Boolean mailingListOptIn;
    private LocalDateTime createTs;

    public Member() {
    }

    public Member(String slackUserId, String name, String email, Boolean mailingListOptIn, LocalDateTime createTs) {
        this.slackUserId = slackUserId;
        this.name = name;
        this.email = email;
        this.mailingListOptIn = mailingListOptIn;
        this.createTs = createTs;
    }
}
