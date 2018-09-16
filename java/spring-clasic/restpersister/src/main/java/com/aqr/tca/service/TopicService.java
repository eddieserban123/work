package com.aqr.tca.service;

import com.aqr.tca.repository.TopicRepository;
import com.aqr.tca.utils.Topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    @Autowired
    private TopicRepository repository;

    public Topics getTopics() {
        return repository.getTopics();
    }


}
