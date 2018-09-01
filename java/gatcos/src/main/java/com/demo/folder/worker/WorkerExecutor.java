package com.demo.folder.worker;

import java.util.List;
import java.util.concurrent.*;

import com.demo.folder.worker.pojos.WorkStatus;

public enum WorkerExecutor {

    INSTANCE;

    private ExecutorService execService;
    private CompletionService<List<WorkStatus>> complService;


    WorkerExecutor() {
        execService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        complService = new ExecutorCompletionService(execService);
    }

    public Future<List<WorkStatus>> submit(Work work) {
        return complService.submit(() -> work.doWork());
    }

    public Future<List<WorkStatus>> submit(Callable<List<WorkStatus>> work) {
        return complService.submit(work::call);
    }


    public List<WorkStatus> take() {
        List<WorkStatus> workStatusList = null;
        try {
            workStatusList = complService.take().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return workStatusList;
    }
}
