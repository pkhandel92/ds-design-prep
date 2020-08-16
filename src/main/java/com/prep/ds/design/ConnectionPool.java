package com.prep.ds.design;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class ConnectionPool<T> {
    //Using a queue to keep tasks available at O(1);
    private BlockingQueue<T> availableConn;
    private BlockingQueue<T> inUseConn;
    //Keeping a map entry for each available/unavailable queue to get last executed time of task for ttl
    private Map<T,Long> availiableConnMap;
    private Map<T,Long> inUseConnectionMap;
    private int corePoolSize;
    private int expiryTime;

    public ConnectionPool(int corePoolSize, int expiryTime) {
        this.corePoolSize = corePoolSize;
        this.expiryTime = expiryTime;
        this.availableConn=new LinkedBlockingQueue<T>(corePoolSize);
        this.inUseConn=new LinkedBlockingQueue<T>(corePoolSize);
        this.availiableConnMap=new ConcurrentHashMap<>();
        this.inUseConnectionMap=new ConcurrentHashMap<>();
    }
    public T getConnection() throws Throwable {
        T t=null;
        long now=System.currentTimeMillis();
        while(!availableConn.isEmpty()){
            T temp=availableConn.poll();
            if(availiableConnMap.get(temp)!=null){
                long currTime= availiableConnMap.get(temp);
                if(now-currTime>expiryTime){
                    availiableConnMap.remove(temp);
                    availableConn.remove(temp);
                    killConnection(temp);
                }else{
                    if(isValid(temp)) {
                         t=temp;
                         availableConn.remove(t);
                         availiableConnMap.remove(t);
                        inUseConnectionMap.put(t, now);
                        inUseConn.offer(t);
                        return t;
                    }else{
                        availiableConnMap.remove(temp);
                        availableConn.remove(temp);
                        killConnection(temp);
                    }

                }
            }
        }
        if(t==null){
            t=create();
            availiableConnMap.put(t,now);
            availableConn.offer(t);
        }
        return t;
    }

    protected abstract void killConnection(T temp) throws Throwable;

    protected void releaseConnection(T t){
        inUseConnectionMap.remove(t);
        inUseConn.remove(t);
        availableConn.add(t);
        availiableConnMap.put(t,System.currentTimeMillis()) ;
    }
    protected abstract T create() throws Throwable;

    protected abstract boolean isValid(T temp) throws Throwable;
}
