package design_pattern.proxy.jdk_dynamic;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


@Slf4j
public class PersonInvocationHandler implements InvocationHandler {
    private Object target;

    public PersonInvocationHandler(Object object) {
        this.target = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("调用方法: " + method.getName());
        method.invoke(this.target, args);
        return "HesenBerg";
    }
}
