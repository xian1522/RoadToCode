package study.aop.chain;

import study.aop.MethodInvocation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AroundHandler implements MethdHandler {
    @Override
    public void invoke(MethodInvocation methodInvocation) throws Exception {
        log.info("执行 around 开始方法");
        methodInvocation.proceed();
        log.info("执行 around 结束方法");
    }
}
