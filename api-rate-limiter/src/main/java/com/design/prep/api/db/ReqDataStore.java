package com.design.prep.api.db;

import com.design.prep.api.req.Request;

import java.util.Map;
import java.util.Queue;

public interface ReqDataStore {
    public Queue<Request> getCurrentState(String userId);
    public void setCurrentState(Queue<Request> requests);
}
