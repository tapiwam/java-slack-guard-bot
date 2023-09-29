package com.myapp.guardbot.service;

import com.myapp.guardbot.entity.Member;
import com.myapp.guardbot.model.Sentiment;
import com.myapp.guardbot.model.SentimentTracker;
import com.myapp.guardbot.repo.MemberRepo;
import com.myapp.guardbot.repo.MessageRepo;
import com.myapp.guardbot.entity.Message;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SlackService {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private SentimentTracker sentimentTracker;

    @Autowired
    private SentimentAnalyzerService sentimentService;

    @Autowired
    private ProfanityService profanityService;

    public Message saveMessage(Message message){
        log.info("Saving message {}", message.getEventId());
        // TODO
        return null;
    }

    public Member saveMember(Member member){
        log.info("Saving member: {}", member.getSlackUserId());
        // TODO
        return null;
    }

}
