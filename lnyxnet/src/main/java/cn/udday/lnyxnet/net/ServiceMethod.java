package cn.udday.lnyxnet.net;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

abstract class ServiceMethod<T> {
    ServiceMethod(){}
    static <T> ServiceMethod<T> parseAnnotations(LnyxRetrofit lnyxRetrofit, Method method){

        Type returnType = method.getGenericReturnType();
        return null;
    }

    abstract T invoke(Object[] var1);
}
