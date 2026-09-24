package aop;

import aop.proxy.JdkDymamicProxy;
import aop.service.OrderService;
import aop.service.OrderServiceImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class AopTest {

    @Test
    public void testAop() {
        OrderService orderService = new OrderServiceImpl();
        JdkDymamicProxy jdkDymamicProxy = new JdkDymamicProxy(orderService);

        OrderService orderServiceProxy = (OrderService)Proxy.newProxyInstance(orderService.getClass().getClassLoader(),
                orderService.getClass().getInterfaces(), jdkDymamicProxy);

        orderServiceProxy.placeOrder("键盘", 2);
    }
}
