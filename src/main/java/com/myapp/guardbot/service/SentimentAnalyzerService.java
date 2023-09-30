package com.myapp.guardbot.service;

import com.myapp.guardbot.model.Sentiment;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Slf4j
public class SentimentAnalyzerService {
    private StanfordCoreNLP pipeline;

    public SentimentAnalyzerService() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public Sentiment findSentiment(String msg) {
        int mainSentiment = 0;
        String sentimentType = "NULL";
        if (msg != null && msg.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(msg);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            }
        }

        log.info("Sentiment: {}, Final: {}", sentimentType, Sentiment.findSentiment(sentimentType));

        //sentiment ranges from very negative, negative, neutral, positive, very positive
        return Sentiment.findSentiment(sentimentType);
    }

}
