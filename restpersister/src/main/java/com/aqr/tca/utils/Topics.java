package com.aqr.tca.utils;

import java.util.ArrayList;
import java.util.List;

public class Topics {

    private List<String> topics;

    public Topics() {
        topics = new ArrayList<>();
    }

    public List<String> getTopics() {
        return topics;
    }

    public void addTopic(String topic) {
        this.topics.add(topic);
    }


}
