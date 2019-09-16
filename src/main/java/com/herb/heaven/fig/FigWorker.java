package com.herb.heaven.fig;

import java.util.Map;

public interface FigWorker {

    long setId(String id);

    Map<Long,Long> getAllIds();

    void putAllIds(Map<Long,Long> map);

    default long createWorkId(){
        Map<Long,Long> map = getAllIds();
        long workId = 0L;
        long timetamp = System.currentTimeMillis();
        for(Map.Entry<Long,Long> entry : map.entrySet()){
            if(timetamp > entry.getValue()){
                map.remove(entry.getKey(),entry.getValue());
            }else{
                if(entry.getKey() > workId){
                    workId = entry.getKey();
                }
            }
        }
        workId = workId +1;
        timetamp = timetamp + 30*60*1000;
        map.put(workId,timetamp);
        putAllIds(map);
        return workId;
    }
}
