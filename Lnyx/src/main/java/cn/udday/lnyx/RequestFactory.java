package cn.udday.lnyx;

import android.util.ArrayMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.udday.lnyx.http.GET;
import cn.udday.lnyx.http.Headers;
import cn.udday.lnyx.http.POST;

public class RequestFactory {
    private static final String PARAM = "[a-zA-Z][a-zA-Z0-9_-]*";
    private static final Pattern PARAM_URL_REGEX = Pattern.compile("\\{(" + PARAM + ")\\}");
    private static final Pattern PARAM_NAME_REGEX = Pattern.compile(PARAM);
    private Method method;
    private String url;
    private String HttpMethod;
    private Map<String,String> headers;
    private String minorUrl;
    private boolean hasBody;


    static RequestFactory paresAnnotations(Lnyx lnyx, Method method){
        return new Builder(lnyx,method).build();
    }
    Request create(Object[] args){

    }
    RequestFactory(Builder builder){
        method = builder.method;
    }
    static class Builder {

        Annotation[] methodAnnotations;
        Annotation[][] parameterAnnotationsArray;
        Type[] parameterTypes;

        Lnyx lnyx;
        Method method;
        String url;
        String httpMethod;
        String minorUrl;
        Map<String,String> headers;
        Set<String> minorUrlParamNames;
        boolean hasBody;

        Builder(Lnyx lynx,Method method){
            this.lnyx = lynx;
            this.url = lynx.baseUrl;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        public RequestFactory build() {
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            return new RequestFactory(this);
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                parseHttpMethodAndPath("GET", ((GET) annotation).value(), false);
            } else if (annotation instanceof POST) {
                parseHttpMethodAndPath("POST", ((POST) annotation).value(), true);
            } else if (annotation instanceof Headers){
                String[] headersToParse = ((Headers) annotation).value();
                headers = parseHeaders(headersToParse);
            }
        }

        private Map<String, String> parseHeaders(String[] headersToParse) {
            Map<String,String> headers = new ArrayMap<>();
            for (int i = 0; i < headersToParse.length; i++) {
                String[] split = headersToParse[i].split(":");
                headers.put(split[0],split[1]);
            }
            return headers;
        }

        private void parseHttpMethodAndPath(String httpMethod, String value, boolean hasBody){
            this.httpMethod = httpMethod;
            this.hasBody = hasBody;

            this.minorUrl = value;
            this.minorUrlParamNames = parsePathParameters(value);
        }

        private Set<String> parsePathParameters(String value) {
            Matcher m = PARAM_URL_REGEX.matcher(value);
            Set<String> patterns = new LinkedHashSet<>();
            while (m.find()) {
                patterns.add(m.group(1));
            }
            return patterns;
        }
    }
}
