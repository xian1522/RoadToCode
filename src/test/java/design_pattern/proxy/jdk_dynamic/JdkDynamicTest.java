package design_pattern.proxy.jdk_dynamic;

import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.assertEquals;

/**
 * java动态代理
 */
public class JdkDynamicTest {
    @Test
    public void jdkDynamicProxy(){
        //被代理类
        PersonService personService = new PersonServiceImpl("Walt White");
        //invocationHandler持有被代理类
        PersonInvocationHandler invocationHandler = new PersonInvocationHandler(personService);
        /**
         * 动态生成的代理类与被代理实现相同接口方法
         * 持有invoactionHandler对象，并在每个被代理接口方法中调用InvocationHandler.invoke()方法
         */
        PersonService proxy = (PersonService) Proxy.newProxyInstance(personService.getClass().getClassLoader(),
                            personService.getClass().getInterfaces(), invocationHandler);

        assertEquals(proxy.sayMyName(), "HesenBerg");
        proxy.reply();
    }
}
