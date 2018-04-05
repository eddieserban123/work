package com.aqr.tca.beans.sync;

import com.aqr.tca.model.PersistToWebService;
import com.aqr.tca.utils.ConsumerBuilder;
import com.aqr.tca.utils.StatusWork;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Component
public class JobSyncExecutor {

    private static final Logger logger = LogManager.getLogger(JobSyncExecutor.class);
    private CopyOnWriteArrayList<SyncWorker> workBeingExecuted;

    @Autowired
    ConsumerBuilder cm;


    @PostConstruct
    private void finishBean() {
        workBeingExecuted = new CopyOnWriteArrayList<>();
    }

    public JobSyncExecutor() {
    }


    public void executeWork(PersistToWebService persist, OutputStream os) {


        SyncWorker work = new SyncWorker() {
            @Override
            public StatusWork call() {
                setAppName(persist.getAppName());
                setDateTime(LocalDateTime.now());
                setThreadId(Thread.currentThread().getId());
                setTopic(persist.getFromTopic());

                try {

                    final Consumer<Long, String> consumer = cm.createKafkaConsumer(getTopic(), getAppName());
                    final int giveUp = 100;
                    int noRecordsCount = 0;


                    while (shouldContinue()) {
                        final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);

                        if (consumerRecords.count() == 0) {
                            noRecordsCount++;
                            if (noRecordsCount > giveUp) break;
                            else continue;
                        } else {
                            noRecordsCount = 0;
                        }


                        consumerRecords.forEach(record -> {
                            try {
                                os.write(String.format("Consumer Record:(%d, %s, %d, %d)\n",
                                        record.key(), record.value(),
                                        record.partition(), record.offset()).getBytes());
                            } catch (IOException e) {
                                logger.error(e.getLocalizedMessage());
                                e.printStackTrace();
                            }
                            logger.debug(String.format("Consumer Record:(%d, %s, %d, %d)\n",
                                    record.key(), record.value(),
                                    record.partition(), record.offset()));
                        });
                        consumer.commitAsync();
                    }
                    consumer.close();
                    logger.error("DONE for ");


                } catch (Exception ex) {
                    logger.error("Excpetion for " + ex.getLocalizedMessage());

                }
                return null;
            }
        };
        try {
            workBeingExecuted.add(work);
            work.call();

        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        } finally {
            workBeingExecuted.remove(work);
        }
    }

    public List<String> getWorkBeingExecuted() {
        List<String> work = new ArrayList<>();
        workBeingExecuted.iterator().forEachRemaining(elem -> work.add(elem.getAppName()));
        return work;
    }


    public void stopWorker(String appName) {
        workBeingExecuted.iterator().forEachRemaining(elem -> {
            if (elem.getAppName().equalsIgnoreCase(appName))
                elem.stop();
        });

    }





}
