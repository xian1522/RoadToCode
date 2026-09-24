package aop;

import aop.chain.*;
import aop.proxy.JdkDymamicProxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MethodInvocation {

    private int currentIndex = -1;

    private JdkDymamicProxy proxy;

    private Method method;

    private Object[] args;

    private List<MethdHandler> methdHandlerList;

    public MethodInvocation(JdkDymamicProxy proxy, Method method, Object[] args) {
        this.proxy = proxy;
        this.method = method;
        this.args = args;

        methdHandlerList = Arrays.asList(new AroundHandler(), new BeforeHandler(),
                 new AfterHandler(), new AfterReturningHandler());
    }

    public void proceed() throws Exception {

        if(currentIndex == methdHandlerList.size() - 1) {
            method.invoke(proxy.getOrderService(), args);
            return;
        }

        MethdHandler methdHandler = methdHandlerList.get(++currentIndex);

        methdHandler.invoke(this);
    }


}
