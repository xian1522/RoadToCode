package aop.chain;

import aop.MethodInvocation;

public interface MethdHandler {

    void invoke(MethodInvocation methodInvocation) throws Exception;
}
