package com.gregorriegler.seamer.cglib;

import com.gregorriegler.seamer.Seamer;
import com.gregorriegler.seamer.core.ProxyInvokable;
import com.gregorriegler.seamer.core.Seam;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class SeamMethodInterceptor<T> implements MethodInterceptor {

    private String methodName;
    private String seamId;
    private Seam<T> seam;

    // for deserialization
    public SeamMethodInterceptor() {
    }

    public SeamMethodInterceptor(String methodName, String seamId) {
        this.methodName = methodName;
        this.seamId = seamId;
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!methodName.equals(method.getName())) {
            return proxy.invokeSuper(target, args);
        }

        if (seam == null) {
            seam = Seamer.createSeam(
                seamId,
                ProxyInvokable.of(target, methodName)
            );
            return seam.invoke(args);
        }

        T result = invoke(proxy, target, args);
        seam.record(args, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    public T invoke(MethodProxy proxy, Object target, Object[] args) throws Throwable {
        return (T) proxy.invokeSuper(target, args);
    }
}
