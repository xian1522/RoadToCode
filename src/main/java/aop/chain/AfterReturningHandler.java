package aop.chain;

import aop.MethodInvocation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AfterReturningHandler implements MethdHandler{
    @Override
    public void invoke(MethodInvocation methodInvocation) throws Exception {
        methodInvocation.proceed();
        log.info("执行 afterReturning 方法");
    }
}
