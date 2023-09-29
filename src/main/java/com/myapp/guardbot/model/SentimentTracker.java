package com.myapp.guardbot.model;

import com.google.common.collect.EvictingQueue;
import lombok.Getter;

import java.util.Queue;

public class SentimentTracker {

    // Have a Fifo queue to keep track of recent messages
    @Getter
    private final Queue<Message> queue;

    // What is the average sentiment score
    @Getter
    private Double score = 0.0;

    // What percentage of recent messages are profane
    @Getter
    private Double profanePercent = 0.0;

    public SentimentTracker(Integer size) {
        this.queue = EvictingQueue.create(size);
    }

    public void addMessage(Message message) {
        queue.add(message);
        setScore();
    }

    public Integer getSize() {
        return queue.size();
    }

    private void setScore(){
    }

    public Sentiment getAvergaeSentiment(){
        long roundedScore = Math.round(score);
        return switch ((int) roundedScore) {
            case 2 -> Sentiment.VERY_POSITIVE;
            case 1 -> Sentiment.POSITIVE;
            case -1 -> Sentiment.NEGATIVE;
            case -2 -> Sentiment.VERY_NEGATIVE;
            default -> Sentiment.NEUTRAL;
        };
    }

    // Provide a summary for sentiment and profanity for the most recent messages
    public String getSummary(){
        return "Sentiment for the last " + getSize() + " messages: Average Sentiment: " + getAvergaeSentiment() + ", Profane: " + String.format("%.2f", profanePercent*100) + "%";
    }

}
