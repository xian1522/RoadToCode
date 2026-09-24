package aop.chain;

import aop.MethodInvocation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BeforeHandler implements MethdHandler{
    @Override
    public void invoke(MethodInvocation methodInvocation) throws Exception {
      log.info(" 执行 before 方法");
      methodInvocation.proceed();
    }
}
