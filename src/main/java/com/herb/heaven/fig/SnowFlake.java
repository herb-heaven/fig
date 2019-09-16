package com.herb.heaven.fig;

public class SnowFlake {



    /**
     * 工作 ID 需保持分布式唯一性
     */
    private long workId;

    /**
     * workerId 所占的位数
     */
    private long workerIdBits = 10L;

    private long maxWorkId = -1L ^ (-1L << workerIdBits);

    /**
     * 毫秒内序列
     */
    private volatile long sequence = 0L;

    /**
     * 序列所占位数
     */
    private final long sequenceBits = 12L;

    /**
     * 序列最大值
     */
    private final long maxSequence = -1L ^ (-1L << sequenceBits);

    /** 上次生成ID的时间戳 */
    private volatile long lastTimestamp = -1L;

    /**
     * 初始时间戳
     */
    private final long epoch = 1546272000000L;//2019-01-01 00:00:00

    private final long epochBits = workerIdBits + sequenceBits;


    public synchronized long nextId(){
        Long currentTimestamp = System.currentTimeMillis();
        if(currentTimestamp < lastTimestamp){
            throw new RuntimeException("服务器时间回拨");
        }
        if(currentTimestamp == lastTimestamp){
            if(sequence == maxSequence){
                currentTimestamp = getNextMillis(lastTimestamp);
                sequence = 0L;
            }else{
                sequence = sequence + 1L;
            }
        }else{
            sequence = 0L;
        }
        lastTimestamp = currentTimestamp;
        return ((currentTimestamp-epoch) << epochBits)
                | (workId << workerIdBits)
                | (sequence);
    }

    private long getNextMillis(long lastTimestamp){
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp){
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
