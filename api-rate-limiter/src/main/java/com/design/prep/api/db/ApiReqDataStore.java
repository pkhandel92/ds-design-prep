package com.design.prep.api.db;

import com.design.prep.api.comparator.ReqTimeComparator;
import com.design.prep.api.req.Request;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class ApiReqDataStore implements ReqDataStore{
    @Override
    public Queue<Request> getCurrentState(String userId) {
        //We are assuming we would get some data here from db just mocking the call
        //TODO:: hardcode the data
        Queue<Request> queue=new PriorityQueue<>(new ReqTimeComparator());
        return new PriorityQueue<>();
    }

    @Override
    public void setCurrentState(Queue<Request> userReq) {

    }
}
