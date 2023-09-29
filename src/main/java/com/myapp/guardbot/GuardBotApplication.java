package com.myapp.guardbot;

import com.myapp.guardbot.boltfunctions.AppFunctions;
import com.slack.api.bolt.App;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.MessageChannelJoinEvent;
import com.slack.api.model.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GuardBotApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GuardBotApplication.class, args);
    }

    @Autowired
    private App app;

    @Autowired
    private AppFunctions appFunctions;

    @Override
    public void run(String... args) throws Exception {

        app.command("/hello", appFunctions.helloCommandHandler);
        app.command("/sentiment", appFunctions.sentimentCommandHandler);
        app.event(MessageChannelJoinEvent.class, appFunctions.memberJoinedChannelHandler);
        app.event(MessageEvent.class, appFunctions.msgEventHandler);

        new SocketModeApp(app).start();
    }
}
