package com.design.prep.api.proxy;

import com.design.prep.api.req.Request;

import java.net.http.HttpResponse;


public interface IApplication {
    public HttpResponse<Response> process(Request request);
}
