package cn.udday.lnyxnet.net;

import android.os.Build;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

public class LnyxRetrofit {
    Executor callbackExecutor;
    String baseUrl;
    public static final class Builder{
        private String baseUrl;
        private Executor callbackExecutor;
        Builder(LnyxRetrofit lnyxRetrofit){
            this.baseUrl = lnyxRetrofit.baseUrl;
            this.callbackExecutor = lnyxRetrofit.callbackExecutor;
        }
        public LnyxRetrofit.Builder baseUrl(String url){
            return this.baseUrl(url);
        }
        public LnyxRetrofit.Builder callbackExecutor(Executor executor){
            this.callbackExecutor = executor;
            return null;
        }
    }
    public LnyxRetrofit build(){

            return this;
    }
    public <T> T create(final Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        for (int i = 0; i < method.getAnnotations().length; i++) {
                            System.out.println("1"+method.getAnnotations()[i].annotationType().toString());
                            System.out.println(method.getGenericReturnType().getTypeName());
                            for (int i1 = 0; i1 < method.getParameterTypes().length; i1++) {
                                System.out.println("2"+method.getParameterTypes()[i].toString());
                            }
                            for (int i1 = 0; i1 < method.getParameterAnnotations().length; i1++) {
                                for (int i2 = 0; i2 < method.getParameterAnnotations()[i].length; i2++) {
                                    System.out.println("3"+method.getParameterAnnotations()[i1][i2].toString());
                                }
                            }
                        }
                        return 0;
                    }
                }
        );
    }


}
