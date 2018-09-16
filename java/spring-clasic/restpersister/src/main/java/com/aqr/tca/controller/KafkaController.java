package com.aqr.tca.controller;


import com.aqr.tca.beans.sync.JobSyncExecutor;
import com.aqr.tca.model.PersistToWebService;
import com.aqr.tca.service.TopicService;
import com.aqr.tca.utils.Topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

//http://www.baeldung.com/java-logging-intro

@RestController
@RequestMapping("/kafka/topics")
public class KafkaController {

    @Autowired
    private TopicService topicService;
    private static final Logger logger = LogManager.getLogger(KafkaController.class);


    @Autowired
    JobSyncExecutor jobSyncExecutor;



    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Topics getTopics() {
        return topicService.getTopics();
    }




    // curl -X POST -H "Content-Type: application/json" -d '{"fromTopic":"testtopic","appName":"AQRPersister1","location":"http://localhost:8080","method":"POST","headers":[{"dsdsd":"asa"},{"head2":"val2"}]}' http://localhost:8080/restpersister/kafka/topics/streaming
    @RequestMapping(method = RequestMethod.POST, path = "/streaming", consumes = MediaType.APPLICATION_JSON_VALUE)
    public StreamingResponseBody getLongStreamingData(@RequestBody PersistToWebService persist) {
        return os -> {
            jobSyncExecutor.executeWork(persist, os);
        };
    }

    //curl -XGET http://localhost:8080/restpersister/kafka/topics/statusstreamingwork
    @RequestMapping(method = RequestMethod.GET, path = "/statusstreamingwork")
    public ResponseEntity<List<String>> getAsyncStatusofWork(final PersistToWebService persist) {
        return new ResponseEntity<>(jobSyncExecutor.getWorkBeingExecuted(), HttpStatus.OK);
    }


    //curl DELETE http://localhost:8080/restpersister/kafka/topics/work/
    @RequestMapping(method = RequestMethod.DELETE, path = "/work/{appName}")
    public ResponseEntity stopWorker(@PathVariable String appName) {
        jobSyncExecutor.stopWorker(appName);
        return new ResponseEntity(HttpStatus.OK);
    }


}



