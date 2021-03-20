package cn.udday.lnyx2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
X x = new Lnyx.Builder("https://v2.alapi.cn/api/").build().create(X.class);
    @Headers("xx")
    @GET("xx")
    Call xx();
    @Headers("xx")
    @POST("xx")
    Call xx(@Query("xx") String string);
*/
public class Lnyx {
    Lnyx lnyx = this;
    String baseUrl;
    Lnyx(Lnyx.Builder builder){
        this.baseUrl = builder.baseUrl;
    }
    public <T> T create(Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(),new Class<?>[]{service},new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                Request request = new RequestFactory.Builder(lnyx, method, args).build().create();
                return  new LnyxHttpClient.Builder().build().newCall(request);
            }
        });
    }
    public static final class Builder{
        String baseUrl;
    public  Builder(String baseUrl){
            this.baseUrl = baseUrl;
        }
        public Lnyx build(){
            return new Lnyx(this);
        }
    }
}
