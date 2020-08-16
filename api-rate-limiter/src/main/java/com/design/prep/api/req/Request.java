package com.design.prep.api.req;

public class Request {
    private String userId;
    private long timeOfRequest;
    private int noOfRequests;


    public int getNoOfRequests() {
        return noOfRequests;
    }

    public void setNoOfRequests(int noOfRequests) {
        this.noOfRequests = noOfRequests;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimeOfRequest() {
        return timeOfRequest;
    }

    public void setTimeOfRequest(long timeOfRequest) {
        this.timeOfRequest = timeOfRequest;
    }
}
