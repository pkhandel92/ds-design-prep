package com.prep.ds.design;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

public abstract class ConnectionPool<T> {
    //Using a queue to keep tasks available at O(1);
    private BlockingQueue<T> availableConn;
    private BlockingQueue<T> inUseConn;
    //Keeping a map entry for each available/unavailable queue to get last executed time of task for ttl
    private Map<T,Long> availiableConnMap;
    private Map<T,Long> inUseConnectionMap;
    //Total no of connections
    private int corePoolSize;
    // amount of time in which each connection will expire
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
        while(!availableConn.isEmpty()){//iterate through the available connections
            T temp=availableConn.poll();//fetch first available connection
            if(availiableConnMap.get(temp)!=null){
                //check time of the available connection
                long currTime= availiableConnMap.get(temp);
                if(now-currTime>expiryTime){
                    //if time expires remove that connection
                    availiableConnMap.remove(temp);
                    availableConn.remove(temp);
                    //responsibility of kiilliing the connection will be with caller
                    killConnection(temp);
                }else{
                    if(isValid(temp)) {//check if connection is still valid**. Responsibility with valid
                         t=temp;
                         //Remove the connection from available connections
                         availableConn.remove(t);
                         availiableConnMap.remove(t);
                         //Add to in Use connections
                        inUseConnectionMap.put(t, now);
                        inUseConn.offer(t);
                        return t;
                    }else{
                        //if connection is not valid remove and kill the connection
                        availiableConnMap.remove(temp);
                        availableConn.remove(temp);
                        killConnection(temp);
                    }

                }
            }
        }
        if(t==null){//No need for this check just a fail safe
            //Create a connection if available connections had no reusable connections
            if(inUseConn.size()<corePoolSize) {
                t = create();
                inUseConnectionMap.put(t, now);
                inUseConn.offer(t);
            }
            else{
                throw new RejectedExecutionException("No connections available");
            }
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
