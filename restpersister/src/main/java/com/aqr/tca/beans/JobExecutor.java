package com.aqr.tca.beans;

import com.aqr.tca.utils.StatusWork;
import com.aqr.tca.utils.StatusWorkHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class JobExecutor {

    private static final Logger logger = LogManager.getLogger(JobExecutor.class);

    @Value("${jobexecutor.cpu.size:4}")
    private int cpuSize;

    private BlockingQueue<Worker> workToDo;
    private CopyOnWriteArrayList<Worker> workBeingExecuted;
    private List<Thread> threads;

    @PostConstruct
    private void finishExecutor(){
        workToDo = new ArrayBlockingQueue<Worker>(20);
        workBeingExecuted = new CopyOnWriteArrayList<>();
        threads = new ArrayList();
        for (int i = 0; i < cpuSize; i++) {
            threads.add(new Thread(() -> {
                while (true) {
                    Worker work = null;
                    try {
                        work = workToDo.take();
                        if (work != null) {
                            workBeingExecuted.add(work);
                            StatusWork status = ((Callable<StatusWork>) work).call();
                            if (StatusWorkHelper.isErrorStatus(status)) {
                                logger.error(status.getText() + "failed");
                            }

                        }
                    } catch (InterruptedException ex) {
                        //someone stop war
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error(e.getLocalizedMessage());
                    } finally {
                        if (work != null)
                            workBeingExecuted.remove(work);
                    }

                }
            }));
        }
        threads.stream().forEach(Thread::start);
    }


    public JobExecutor() {
    }


    public void executeWork(Worker work) {
        workToDo.add(work);
    }

    public List<String> getWorkBeingExecuted() {
        List<String> work = new ArrayList<>();
        workBeingExecuted.iterator().forEachRemaining(elem -> work.add(elem.getInfo()));
        return work;
    }

    @PreDestroy
    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }


}
