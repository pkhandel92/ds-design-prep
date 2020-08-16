package com.design.prep.api.proxy;

import com.design.prep.api.req.Request;

import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * In real world our api rate limiter works on
 * Proxy but this we are considering our main
 * app
 */
public class Application implements IApplication{
    public HttpResponse<Response> process(Request request){
        System.out.println("Request passed");
        return new HttpResponse<Response>() {
            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return HttpRequest.newBuilder().build();
            }

            @Override
            public Optional<HttpResponse<Response>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public Response body() {
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
    }
}
