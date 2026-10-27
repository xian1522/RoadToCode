package design_pattern.proxy.chain_of_resiponsibility;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import study.design_pattern.chain_of_resiponsibility.Cto;
import study.design_pattern.chain_of_resiponsibility.Director;
import study.design_pattern.chain_of_resiponsibility.Handler;
import study.design_pattern.chain_of_resiponsibility.Manager;

/**
 * 责任链为链式结构，非列表，链上每个处理器都参与处理
 * 链的控制流沿引用链递归调用
 * 链尾为空时特殊判断
 */
@Slf4j
public class ChainOfResiponsibility {

    public String handle(Handler handler){
        String result;
        if(handler.canHandle(1500)) {
            result = handler.doHandle();
        }else if (handler.getNext() != null){
            result = handle(handler.getNext());
        }else {
            result = "";
            log.info("nobody can appovle");
        }
        return result;
    }

    @Test
    public void chainHandleReq() {
        Manager manager = new Manager(new Director(new Cto(null)));
        handle(manager);
    }
}
