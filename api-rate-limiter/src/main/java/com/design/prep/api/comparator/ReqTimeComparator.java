package com.design.prep.api.comparator;

import com.design.prep.api.req.Request;

import java.util.Comparator;

public class ReqTimeComparator implements Comparator<Request> {
    @Override
    public int compare(Request o1, Request o2) {
        //Sorting the queue based on time in ascending order
        return (int) (o1.getTimeOfRequest()-o2.getTimeOfRequest());
    }
}
