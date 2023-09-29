package com.myapp.guardbot.config;

import com.myapp.guardbot.model.SentimentTracker;
import com.slack.api.bolt.App;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public App app(){
        return new App();
    }

    @Bean
    public SentimentTracker sentimentTracker(){
        return new SentimentTracker(100);
    }

}
