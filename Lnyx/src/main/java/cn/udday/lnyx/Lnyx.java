package cn.udday.lnyx;

import android.os.Build;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.Executor;

public class Lnyx {
    String baseUrl;
    LnyxHttpClient lnyxHttpClient;
    Lnyx lnyx = this;
    Lnyx(String baseURl,LnyxHttpClient lnyxHttpClient){
    this.baseUrl = baseURl;
    this.lnyxHttpClient = lnyxHttpClient;
    }
    public <T> T create(Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(),new Class<?>[]{service},new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                RequestFactory requestFactory = RequestFactory.paresAnnotations(lnyx, method);

            }
        });
    }
    public static class Builder{
        String baseUrl;
        LnyxHttpClient lnyxHttpClient;
        Builder(Lnyx lnyx,LnyxHttpClient lnyxHttpClient){
            baseUrl = lnyx.baseUrl;
            lnyxHttpClient = lnyx.lnyxHttpClient;
        }
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }
        public Builder lnyxHttpClient(LnyxHttpClient lnyxHttpClient){
            this.lnyxHttpClient = lnyxHttpClient;
            return  this;
        }
    }

    public String baseUrl() {
        return baseUrl;
    }
    public Lnyx build(){
        return new Lnyx(baseUrl,lnyxHttpClient);
    }

}
