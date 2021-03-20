package cn.udday.lnyx2;


import android.util.ArrayMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import cn.udday.lnyx2.http.GET;
import cn.udday.lnyx2.http.Headers;
import cn.udday.lnyx2.http.POST;
import cn.udday.lnyx2.http.Query;

public class RequestFactory {
    private Lnyx lnyx;
    private Method method;
    private String httpMethod;
    private String baseUrl;
    private String nextUrl;
    private Object[] args;
    private Map<String,String> headers;
    private Map<String, String> parameterHandlers;
    boolean hasBody;
    RequestFactory(RequestFactory.Builder builder){
        this.lnyx = builder.lnyx;
        this.method = builder.method;
        this.baseUrl = builder.baseUrl;
        this.httpMethod = builder.httpMethod;
        this.nextUrl = builder.nextUrl;
        this.headers = builder.headers;
        this.hasBody = builder.hasBody;
        this.parameterHandlers = builder.parameterHandlers;
        this.args = builder.args;
    }
    Request create(){
        return new Request.Builder(method,baseUrl,nextUrl,headers,hasBody,parameterHandlers).build();
    }
    static final class Builder{
        private Lnyx lnyx;
        private Object[] args;
        private Annotation[] methodAnnotations;
        private Annotation[][] parameterAnnotationsArray;
        private Type[] parameterTypes;
        private Method method;
        private String httpMethod;
        private String baseUrl;
        private String nextUrl;
        private Map<String,String> headers;
        private Map<String, String> parameterHandlers;
        boolean hasBody;

    Builder(Lnyx lnyx, Method method,Object[] args){
        this.lnyx = lnyx;
        this.baseUrl = lnyx.baseUrl;
        this.method = method;
        this.methodAnnotations = method.getAnnotations();
        this.parameterTypes = method.getGenericParameterTypes();
        this.parameterAnnotationsArray = method.getParameterAnnotations();
        this.args = args;

    }

    RequestFactory build(){
        Annotation[] var1 = this.methodAnnotations;
        int p = var1.length;
        int lastParameter;
        for(lastParameter = 0; lastParameter < p; ++lastParameter) {
            Annotation annotation = var1[lastParameter];
            this.parseMethodAnnotation(annotation);
        }
        this.parseParametes(args, parameterAnnotationsArray);
        return new RequestFactory(this);
    }

        private void parseParametes(Object[] args, Annotation[][] parameterAnnotationsArray) {
            Map<String, String> result = new ArrayMap<>();
            for (int i = 0; i < parameterAnnotationsArray.length; i++) {
                for (int i1 = 0; i1 < parameterAnnotationsArray[i].length; i1++) {
                    String s = this.parameterParameteAnnotation(parameterAnnotationsArray[i][i1]);
                    if(s != null)
                    result.put(s,(String) args[i]);
                }
            }
            this.parameterHandlers = result;
        }
        private String parameterParameteAnnotation (Annotation annotation){
            if (annotation instanceof Query){
                String value = ((Query) annotation).value();
                return value;
            }
            return "";
        }
        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                this.parseHttpMethodAndPath("GET", ((GET)annotation).value(), false);
            } else if (annotation instanceof POST) {
                this.parseHttpMethodAndPath("POST", ((POST)annotation).value(), true);
            } else if (annotation instanceof Headers) {
                String[] headersToParse = ((Headers)annotation).value();
                this.headers = this.parseHeaders(headersToParse);
        }}
        private Map<String, String> parseHeaders(String[] headersToParse) {
        Map<String,String> result = new ArrayMap<>();
            for (int i = 0; i < headersToParse.length; i++) {
                String[] split = headersToParse[i].split(":");
                result.put(split[0],split[1]);
            }
            return result;
        }
        private void parseHttpMethodAndPath(String get, String value, boolean b) {
            this.httpMethod = get;
            this.hasBody = b;
            this.nextUrl = value;
        }

    }
}
