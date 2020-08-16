package com.design.prep.api.builder;

import com.design.prep.api.proxy.Application;
import com.design.prep.api.proxy.IApplication;
import com.design.prep.api.proxy.Response;
import com.design.prep.api.req.Request;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpEntity{
    private String url;
    private String port;
    private boolean isSSL;
    private String httpRequestType;
    private HttpEntity(HttpEntityBuilder builder){
        this.url=builder.url;
        this.port=builder.port;
        this.isSSL=builder.isSSL;
        this.httpRequestType=builder.httpRequestType;
    }

    public HttpResponse<Response> callApplication(Request request) {
        IApplication application=new Application();
        return application.process(request);

    }

    public static class HttpEntityBuilder {
        private String url;
        private String port;
        private boolean isSSL;
        private String httpRequestType;
        public HttpEntityBuilder url(String url){
            this.url=url;
            return this;
        }
        public HttpEntityBuilder port(String port){
            this.port=port;
            return this;
        }
        public HttpEntityBuilder isSSL(boolean isSSL){
            this.isSSL=isSSL;
            return this;
        }
        public HttpEntityBuilder httpRequestType(String httpRequestType){
            this.httpRequestType=httpRequestType;
            return this;
        }
        public HttpEntity build() {
        HttpEntity entity=new HttpEntity(this);
        validateHttpEntity();
        return entity;
        }

        private void validateHttpEntity() {
            if(this.url==null ||this.url.isEmpty())
                throw new IllegalArgumentException("Url is mandatory");
            if(this.port==null||this.port.isEmpty())
                throw new IllegalArgumentException("Url is mandatory");
        }

    }
}


