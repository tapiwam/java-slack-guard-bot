package com.myapp.guardbot.config;

import com.myapp.guardbot.model.SentimentTracker;
import com.slack.api.bolt.App;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Value("${application.sentiment.size:100}")
    private Integer sentimentSize;

    @Bean
    public App app(){
        return new App();
    }

    @Bean
    public SentimentTracker sentimentTracker(){
        return new SentimentTracker(sentimentSize);
    }

}
