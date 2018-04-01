package com.aqr.tca.beans;

import com.aqr.tca.utils.StatusWork;
import com.aqr.tca.utils.StatusWorkHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class JobExecutor {

    private BlockingQueue<Worker> workToDo;
    private CopyOnWriteArrayList<Worker> workBeeingExecuted;
    private List<Thread> threads;
    private int cpu = 4;

    JobExecutor() {
        workToDo = new ArrayBlockingQueue<Worker>(20);
        workBeeingExecuted = new CopyOnWriteArrayList<>();
        threads = new ArrayList();
        for (int i = 0; i < cpu; i++) {
            threads.add(new Thread(() -> {
                while (true) {
                    Worker work = null;
                    try {
                        work = workToDo.take();
                        if (work != null) {
                            workBeeingExecuted.add(work);
                            StatusWork status = ((Callable<StatusWork>) work).call();
                            if (StatusWorkHelper.isErrorStatus(status)) {
                                System.out.println(status.getText() + "failed");
                            }

                        }
                    } catch (InterruptedException ex) {
                        //someone stop war
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (work != null)
                            workBeeingExecuted.remove(work);
                    }

                }
            }));
        }
        threads.stream().forEach(Thread::start);
    }


    public void executeWork(Worker work) {
        workToDo.add(work);
    }

    public List<String> getWorkBeeingExecuted() {
        List<String> work = new ArrayList<>();
        workBeeingExecuted.iterator().forEachRemaining(elem -> work.add(elem.getInfo()));
        return work;
    }

    @PreDestroy
    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }


}
