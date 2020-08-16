package com.design.prep.api.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class to store what we would ideally store in db
 * Running sum of not-expired requests and
 * timeForExpiry
 */
public class ApiRateLimiterDB {

    //Assuming this is a running sumer per userId;
    private ConcurrentHashMap<String,Integer> reqPerUsr;
    private ConcurrentHashMap<String,Long> timeToLivePerUser;

    public long getTimeToLive(String userId) {
        return timeToLivePerUser.getOrDefault(userId,0L);
    }
    public int getTotalRequests(String userId){
        return reqPerUsr.getOrDefault(userId,0);
    }
    public long setTimeToLive(String userId,long timeToLive) {
        return timeToLivePerUser.put(userId,timeToLive);
    }
    public int setTotalRequests(String userId,int req){
        return reqPerUsr.put(userId,req);
    }

    public int getMaxRequest() {
        return 10;
    }
}
