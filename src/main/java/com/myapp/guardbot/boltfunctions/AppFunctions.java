package com.myapp.guardbot.boltfunctions;

import com.myapp.guardbot.model.Message;
import com.myapp.guardbot.model.Sentiment;
import com.myapp.guardbot.model.SentimentTracker;
import com.myapp.guardbot.service.ProfanityService;
import com.myapp.guardbot.service.SentimentAnalyzerService;
import com.slack.api.bolt.App;
import com.slack.api.bolt.handler.BoltEventHandler;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostEphemeralResponse;
import com.slack.api.model.event.MessageChannelJoinEvent;
import com.slack.api.model.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class AppFunctions {

    @Autowired
    private App app;

    @Autowired
    private ProfanityService profanityService;

    @Autowired
    private SentimentAnalyzerService sentimentAnalyzerService;

    @Autowired
    private SentimentTracker sentimentTracker;

    public SlashCommandHandler helloCommandHandler = (req, ctx) -> {
        log.info("Received hello command: user={}", req.getPayload().getUserId());
        return ctx.ack(":Wave: Hello!");
    };

    public SlashCommandHandler sentimentCommandHandler = (req, ctx) -> {
        log.info("Received sentiment command: @user={}", req.getPayload().getUserId());
        String stats = sentimentTracker.getSummary();
        return ctx.ack(stats);
    };

    public BoltEventHandler<MessageChannelJoinEvent> memberJoinedChannelHandler = (req, ctx) -> {
        log.info("A member has joined the channel: @user={}", req.getEvent().getUser());

        // Send welcome message
        sendEphemeralMessageToSlackUser(req.getEvent().getUser(), req.getEvent().getChannel(), getWelcomeMessage());
        return ctx.ack();
    };

    public BoltEventHandler<MessageEvent> msgEventHandler = (req, ctx) -> {
        log.info("Received message: @user={} @message={}", req.getEvent().getUser(), req.getEvent().getText());

        // Compute sentiment and profanity indicators
        Boolean isProfane = profanityService.isProfane(req.getEvent().getText());
        Sentiment sentiment = sentimentAnalyzerService.findSentiment(req.getEvent().getText());

        log.info("Detected sentiment for message: @sentiment={} @message={}", sentiment, req.getEvent().getText());

        // Build message
        Message msg = Message.builder()
                .eventId(req.getEventId())
                .channel(req.getEvent().getChannel())
                .sender(req.getEvent().getUser())
                .text(req.getEvent().getText())
                .sentiment(sentiment)
                .isProfane(isProfane)
                .msgTs(req.getEvent().getTs())
                .createTs(LocalDateTime.now())
                .build();

        // Add message to sentiment tracker
        sentimentTracker.addMessage(msg);
        log.info("Persisted message: {}", msg);

        return ctx.ack();
    };

    // Helper methods
    private boolean sendEphemeralMessageToSlackUser(String slackUser, String channelId, String text) throws SlackApiException, IOException {
        ChatPostEphemeralResponse response = app.client().chatPostEphemeral(r -> r
                .channel(channelId)
                .user(slackUser)
                .text(text)
        );

        if(response.isOk()) {
            log.info("Sent ephemeral message: @slackUser={}", slackUser);
            return true;
        } else {
            log.error("Failed to send ephemeral message: @slackUser={}, @error={}", slackUser, response.getError());
            return false;
        }
    }

    private String getWelcomeMessage() {
        return ":star2: *Welcome to the Tech Enthusiasts Forum!* :star2:\n" +
                "\n" +
                "Dear Members,\n" +
                "\n" +
                "We're thrilled to have you join our vibrant community of tech enthusiasts from around the world. Whether you're a seasoned pro or just getting started, you've found a place where your passion for technology is celebrated.\n" +
                "\n" +
                ":rocket: *About Tech Enthusiasts Forum:*\n" +
                "---------------------------------------\n" +
                "Our forum is a hub for discussing all things tech-related. From the latest gadgets and software to coding challenges and tech trends, there's something here for everyone. We encourage open and respectful dialogue, where diverse viewpoints are welcomed.\n" +
                "\n" +
                ":scroll: *Forum Rules and Guidelines:*\n" +
                "-------------------------------\n" +
                "To ensure a positive and constructive atmosphere, please take a moment to familiarize yourself with our forum rules. These guidelines help maintain a respectful and inclusive community. Remember to be courteous, avoid spam, and respect intellectual property rights.\n" +
                "\n" +
                ":waving: *Introduce Yourself:*\n" +
                "-----------------------\n" +
                "We'd love to get to know you better! Take a moment to introduce yourself in our \"New Member Introductions\" section. Share your tech interests, your favorite gadgets, or what brought you here. Our community is eager to welcome you with open arms.\n" +
                "\n" +
                ":handshake: *Our Values:*\n" +
                "---------------\n" +
                "At Tech Enthusiasts Forum, we value diversity, respect, and the free exchange of ideas. We believe in friendly debates and the power of collaboration. Differences of opinion are welcomed as opportunities for learning and growth.\n" +
                "\n" +
                ":shield: *Moderation and Support:*\n" +
                "---------------------------\n" +
                "Our dedicated team of moderators works diligently to ensure a safe and enjoyable experience for all members. If you ever come across any issues or have questions, please don't hesitate to reach out to our moderators. Your feedback is valuable to us.\n" +
                "\n" +
                ":books: *Helpful Resources:*\n" +
                "-----------------------\n" +
                "To help you make the most of your forum experience, we've put together a list of resources, including FAQs and tutorials. You can find them in the \"Forum Resources\" section.\n" +
                "\n" +
                ":rocket: *Start Exploring:*\n" +
                "---------------------\n" +
                "Now that you're part of our tech-savvy community, it's time to dive in! Explore the latest discussions, ask questions, and share your knowledge. Together, we'll continue to fuel our passion for technology.\n" +
                "\n" +
                "Once again, *welcome* to the Tech Enthusiasts Forum! We're excited to have you here, and we look forward to connecting with you in our discussions.\n" +
                "\n" +
                "Happy posting!\n" +
                "\n" +
                "Best regards,\n" +
                "The Tech Enthusiasts Forum";
    }

}
