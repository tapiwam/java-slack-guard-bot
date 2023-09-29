package com.myapp.guardbot.service;


import edu.stanford.nlp.io.IOUtils;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static edu.stanford.nlp.io.IOUtils.readLines;

@Service
@Slf4j
public class ProfanityService {

    @Value("classpath*:bad-words/*")
    private Resource[] files;

    // A list of bad words organized by language
    @Getter
    Map<String, Set<String>> badWordsByLanguage;

    @PostConstruct
    public void init() throws IOException {
        badWordsByLanguage = new HashMap<>();

        for(Resource file : files) {
            String lang =file.getFilename().replace(".txt", "");
            badWordsByLanguage.put(
                    lang,
                    StreamSupport
                            .stream(readLines(file.getFile()).spliterator(), false)
                            .map(String::toLowerCase)
                            .collect(Collectors.toSet())
                    );

            log.debug("Added lang {} with count: {}", lang, badWordsByLanguage.get(lang).size());
        }
    }

    public boolean isProfane(String text) {
        // TODO
        return false;
    }

}
