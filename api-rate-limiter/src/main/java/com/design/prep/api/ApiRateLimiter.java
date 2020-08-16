package com.design.prep.api;

import com.design.prep.api.builder.HttpEntity;
import com.design.prep.api.proxy.Application;
import com.design.prep.api.db.ApiRateLimiterDB;
import com.design.prep.api.db.ApiReqDataStore;
import com.design.prep.api.proxy.IApplication;
import com.design.prep.api.proxy.Response;
import com.design.prep.api.req.Request;

import java.net.http.HttpResponse;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;

/**
 * Api rate limiter implements Proxy Pattern
 * It follows the same pattern as actual service
 * but only instantiates when it has to
 */
public class ApiRateLimiter implements IApplication {
    private ApiRateLimiterDB connection;
    private ApiReqDataStore reqDataStore;
    private IApplication application;

    public HttpResponse<Response> process(Request request) {
        long currentTime = System.currentTimeMillis();
        Queue<Request> requestQueue = reqDataStore.getCurrentState(request.getUserId());
        int requestsMax = connection.getTotalRequests(request.getUserId());
        if (requestQueue.isEmpty()) {
            requestQueue.offer(request);
            connection.setTotalRequests(request.getUserId(), 1);
            connection.setTimeToLive(request.getUserId(), currentTime);
            return callApplication(request);

        }
        if (requestQueue.size() < requestsMax) {
            addToQueue(requestQueue, request, currentTime);
            //Allow application to process

            return callApplication(request);
        } else {
            Request oldReq = requestQueue.peek();
            if (oldReq.getTimeOfRequest() - currentTime > connection.getTimeToLive(request.getUserId())) {
                connection.setTimeToLive(request.getUserId(), currentTime);
                connection.setTotalRequests(request.getUserId(), connection.getTotalRequests(request.getUserId()) + 1);
                requestQueue.offer(request);
            }


        }
        throw new RejectedExecutionException("Consumed request per sec quota exceeded");

    }

    private HttpResponse<Response> callApplication(Request request) {
        HttpEntity.HttpEntityBuilder entityBuilder = new HttpEntity.HttpEntityBuilder();
        HttpEntity entity = entityBuilder.url("url").port("port").isSSL(true).httpRequestType("post").build();
        //Just a mock call
        return entity.callApplication(request);
    }

    private void addToQueue(Queue<Request> requestQueue, Request request, long currentTime) {
        long timeToLive = connection.getTimeToLive(request.getUserId());
        Request oldestElement = requestQueue.peek();

        //Remove all expired request and create a new running sum based
        while (oldestElement.getTimeOfRequest() - currentTime > timeToLive) {
            Request oldReq = requestQueue.poll();
            connection.setTotalRequests(oldReq.getUserId(), connection.getTotalRequests(request.getUserId()) - 1);
            oldestElement = requestQueue.peek();
        }
        //Update the current time to live
        if (1 + connection.getTotalRequests(request.getUserId()) < connection.getMaxRequest()) {
            //Update time to live
            connection.setTimeToLive(request.getUserId(), currentTime);
            requestQueue.offer(request);
            //Update the current running sum
            connection.setTotalRequests(request.getUserId(), connection.getTotalRequests(request.getUserId()) + 1);
        } else {
            throw new RejectedExecutionException("Consumed request per sec quota exceeded");
        }
    }
}
