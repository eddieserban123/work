package com.aqr.tca.controller;


import com.aqr.tca.beans.JobExecutor;
import com.aqr.tca.beans.Worker;
import com.aqr.tca.model.PersistToWebService;
import com.aqr.tca.service.TopicService;
import com.aqr.tca.utils.ConsumerBuilder;
import com.aqr.tca.utils.StatusWork;
import com.aqr.tca.utils.StatusWorkHelper;
import com.aqr.tca.utils.Topics;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/kafka/topics")
public class KafkaController {

    @Autowired
    private TopicService topicService;


    @Autowired
    JobExecutor jobExecutor;

    @Autowired
    ConsumerBuilder cm;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Topics getTopics() {
        return topicService.getTopics();
    }

    //    {"location":"http://localhost:8080","method":"POST","headers":[]}
//curl -X POST -H "Content-Type: application/json" -d '{"location":"http://localhost:8080","method":"POST","headers":[]}' http://localhost:8080/restpersister/kafka/topics/toweb

    @RequestMapping(method = RequestMethod.POST, path = "/tofake",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> moveDataFake(@RequestBody PersistToWebService persist) {

        jobExecutor.executeWork(new Worker(persist.getLocation().toString()) {
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

    //    {"location":"http://localhost:8080","method":"POST","headers":[]}
//curl -X POST -H "Content-Type: application/json" -d '{"location":"http://localhost:8080","method":"POST","headers":[]}' http://localhost:8080/restpersister/kafka/topics/toweb

    @RequestMapping(method = RequestMethod.POST, path = "/toweb",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> moveDataFromTopicsToWebServices(@RequestBody PersistToWebService persist) {


        jobExecutor.executeWork(new Worker(persist.getLocation().toString()) {
            @Override
            public StatusWork call() {
                try {
                    final Consumer<Long, String> consumer = cm.createKafkaConsumer("testtopic1","myId");
                    final int giveUp = 100;
                    int noRecordsCount = 0;

                    while (true) {
                        final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);

                        if (consumerRecords.count() == 0) {
                            noRecordsCount++;
                            if (noRecordsCount > giveUp) break;
                            else continue;
                        }

                        consumerRecords.forEach(record -> {
                            System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                                    record.key(), record.value(),
                                    record.partition(), record.offset());
                        });

                        consumer.commitAsync();
                    }
                    consumer.close();
                    System.out.println("DONE");


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



