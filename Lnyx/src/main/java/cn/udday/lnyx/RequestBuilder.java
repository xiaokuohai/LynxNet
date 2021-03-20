package cn.udday.lnyx;

import java.util.Map;

public class RequestBuilder {
    String method;
    String baseUrl;
    String minorUrl;
    boolean hasBody;
    RequestBody body;
    Map<String,String> headers;
    Request request;

    public RequestBuilder(String method, String baseUrl, String minorUrl, boolean hasBody, RequestBody body, Map<String, String> headers, Request request) {
        this.method = method;
        this.baseUrl = baseUrl;
        this.minorUrl = minorUrl;
        this.hasBody = hasBody;
        this.body = body;
        this.headers = headers;
        this.request = request;

    }


}
