package com.herb.heaven.fig;

import java.util.Map;

public interface FigWorker {

    long setWorkIdTimestamp(long workId,long timestamp);

    long getWorkIdTimestamp(long workId);

    Map<Long,Long> getAllIds();

    void removeWorkId(long workId);

    default long createWorkId(){
        Map<Long,Long> map = getAllIds();
        long workId = 0L;
        long timetamp = System.currentTimeMillis();
        for(Map.Entry<Long,Long> entry : map.entrySet()){
            if(timetamp > entry.getValue()){
                removeWorkId(entry.getKey());
            }else{
                if(entry.getKey() > workId){
                    workId = entry.getKey();
                }
            }
        }
        workId = workId +1;
        timetamp = timetamp + 30*60*1000;
        setWorkIdTimestamp(workId,timetamp);
        return workId;
    }
}
