package com.herb.heaven.fig;
import java.util.concurrent.*;

public class FigWorkerValidator implements Runnable{

    private long workId;
    private FigWorker figWorker;

    private long delay = 1000*60*60;

    public FigWorkerValidator(long workId,FigWorker figWorker){
        this.workId = workId;
        this.figWorker = figWorker;
    }

    @Override
    public void run() {
        long lastTimestamp = figWorker.getWorkIdTimestamp(workId);
        long currentTimestamp = System.currentTimeMillis();
        if(lastTimestamp <= currentTimestamp){
            lastTimestamp = currentTimestamp + 1000*60*60*24*30;//续期一个月
            figWorker.setWorkIdTimestamp(workId,lastTimestamp);
        }
    }

    public void enableValidator(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactory(){
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    return thread;
                }
            }
        );
        service.scheduleAtFixedRate(this, delay, 0, TimeUnit.MILLISECONDS);
    }
}
