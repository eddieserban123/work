package com.aqr.tca.beans;

import com.aqr.tca.utils.StatusWork;
import com.aqr.tca.utils.StatusWorkHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class JobExecutor<T extends Callable<StatusWork>> {

    private BlockingQueue<T> workToDo;
    private CopyOnWriteArrayList<T> workBeeingExecuted;
    private List<Thread> threads;
    private int cpu = 4;

    JobExecutor() {
        workToDo = new ArrayBlockingQueue<T>(20);
        workBeeingExecuted = new CopyOnWriteArrayList<>();
        threads = new ArrayList();
        for (int i = 0; i < cpu; i++) {
            threads.add(new Thread(() -> {
                while (true) {
                    T work = null;
                    try {
                        work = workToDo.take();
                        if (work != null) {
                            workBeeingExecuted.add(work);
                            StatusWork status = work.call();
                            if (StatusWorkHelper.isErrorStatus(status)) {
                                System.out.println(status.getText() + "failed");
                            }

                        }
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


    public void executeWork(T work) {
        workToDo.add(work);
    }

    public List<String> getWorkBeeingExecuted() {
        List<String> work = new ArrayList<>();
        workBeeingExecuted.stream().forEach(t -> work.add("aa"));
        return work;
    }


}
