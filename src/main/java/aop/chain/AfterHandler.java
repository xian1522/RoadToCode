package aop.chain;

import aop.MethodInvocation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AfterHandler implements MethdHandler{
    @Override
    public void invoke(MethodInvocation methodInvocation) throws Exception {
        try{
            methodInvocation.proceed();
        }finally {
            log.info("执行 after 方法");
        }
    }
}
