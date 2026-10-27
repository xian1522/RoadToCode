package study.aop.chain;

import study.aop.MethodInvocation;

public interface MethdHandler {

    void invoke(MethodInvocation methodInvocation) throws Exception;
}
