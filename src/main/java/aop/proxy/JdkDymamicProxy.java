package aop.proxy;

import aop.MethodInvocation;
import aop.service.OrderService;
import lombok.Getter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Getter
public class JdkDymamicProxy implements InvocationHandler {

    private OrderService orderService;

    public JdkDymamicProxy(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation(this, method, args);
        methodInvocation.proceed();
        return null;
    }
}
