package com.aqr.tca.controller;


import com.aqr.tca.beans.JobExecutor;
import com.aqr.tca.model.PersistToWebService;
import com.aqr.tca.service.TopicService;
import com.aqr.tca.utils.StatusWork;
import com.aqr.tca.utils.StatusWorkHelper;
import com.aqr.tca.utils.Topics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/kafka/topics")
public class KafkaController {

    @Autowired
    private TopicService topicService;


    @Autowired
    JobExecutor jobExecutor;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Topics getTopics() {
        return topicService.getTopics();
    }

//    {"location":"http://localhost:8080","method":"POST","headers":[]}
//curl -X POST -d '{"location":"http://localhost:8080","method":"POST","headers":[]}' http://localhost:8080/restpersister/kafka/topics/
    @RequestMapping(method = RequestMethod.POST, path = "/toweb")
    public ResponseEntity<String> moveDataFromTopicsToWebServices(final PersistToWebService persist) {


        jobExecutor.executeWork(new Callable<StatusWork>() {
            @Override
            public StatusWork call() {
                try {
                    Thread.sleep(7000);
                } catch (Exception ex) {
                    return StatusWorkHelper.buildErrorStatus("Er:blabla");
                }
                return StatusWorkHelper.buildOkStatus("blabla");
            }
        });
        return new ResponseEntity<String>("anaremere", HttpStatus.CREATED);
    }
//curl -XGET http://localhost:8080/restpersister/kafka/topics/statuswork
    @RequestMapping(method = RequestMethod.GET, path = "/statuswork")
    public ResponseEntity<List<String>> getStatusofWork(final PersistToWebService persist) {

        return new ResponseEntity<List<String>>(jobExecutor.getWorkBeeingExecuted(), HttpStatus.OK);
    }
}



