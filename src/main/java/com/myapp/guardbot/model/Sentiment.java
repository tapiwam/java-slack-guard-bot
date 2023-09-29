package com.myapp.guardbot.model;

public enum Sentiment {

    VERY_POSITIVE, POSITIVE, NEUTRAL, NEGATIVE, VERY_NEGATIVE;

    public static Sentiment findSentiment(String msg) {
        // Sanitize
        msg = msg == null ? "" : msg.toLowerCase();

        // Find
        return switch (msg) {
            case "very positive" -> VERY_POSITIVE;
            case "positive" -> POSITIVE;
            case "neutral" -> NEUTRAL;
            case "negative" -> NEGATIVE;
            case "very negative" -> VERY_NEGATIVE;
            default -> null;
        };
    }

}
