package aop.chain;

import aop.MethodInvocation;
import aop.chain.MethdHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class AroundHandler implements MethdHandler {
    @Override
    public void invoke(MethodInvocation methodInvocation) throws Exception {
        log.info("执行 around 开始方法");
        methodInvocation.proceed();
        log.info("执行 around 结束方法");
    }
}
