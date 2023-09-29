package com.myapp.guardbot.boltfunctions;

import com.myapp.guardbot.model.SentimentTracker;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.model.event.MessageChannelJoinEvent;
import com.slack.api.model.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppFunctions {

    @Autowired
    private SentimentTracker sentimentTracker;

    public SlashCommandHandler helloCommandHandler = (req, ctx) -> {
        log.info("Received hello command: user={}", req.getPayload().getUserId());
        return ctx.ack(":Wave: Hello!");
    };

    public SlashCommandHandler sentimentCommandHandler = (req, ctx) -> {
        log.info("Received sentiment command: @user={}", req.getPayload().getUserId());
        return ctx.ack(sentimentTracker.getSummary());
    };

    public BoltEventHandler<MessageChannelJoinEvent> memberJoinedChannelHandler = (req, ctx) -> {
        log.info("A member has joined the channel: @user={}", req.getEvent().getUser());
        return ctx.ack();
    };

    public BoltEventHandler<MessageEvent> msgEventHandler = (req, ctx) -> {
        log.info("Received message: @user={} @message={}", req.getEvent().getUser(), req.getEvent().getText());
        return ctx.ack();
    };

}
