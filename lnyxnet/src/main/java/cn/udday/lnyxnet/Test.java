package cn.udday.lnyxnet;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.udday.lnyxnet.port.GET;
import cn.udday.lnyxnet.port.Query;

public class Test {
    public <T> T create(final Class<T> service){
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println(method.getGenericReturnType().toString());
                            for (int i1 = 0; i1 < method.getParameterTypes().length; i1++) {
                                System.out.println(method.getParameterTypes()[i1].toGenericString());
                            }
                        for (int i = 0; i < args.length; i++) {
                            System.out.println(args[i].toString());
                        }
                            for (int i1 = 0; i1 < method.getParameterAnnotations().length; i1++) {
                                for (int i2 = 0; i2 < method.getParameterAnnotations()[i1].length; i2++) {
                                    if (method.getParameterAnnotations()[i1][i2] instanceof Query){
                                         Query query = (Query)method.getParameterAnnotations()[i1][i2];
                                        System.out.println(query.value()+i1+":"+i2);
                                    }
                                }
                            }

                        Annotation[] annotations = method.getAnnotations();
                        for (int it = 0; it < annotations.length; it++) {
                            if (annotations[it] instanceof GET) {
                                System.out.println(((GET) annotations[it]).value());
                            }
                        }
                        return null;
                    }
                }
        );
    }
}
