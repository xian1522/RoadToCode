package design_pattern.proxy.chain_of_resiponsibility;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Cto implements Handler{

    private Handler next;

    public Cto(Handler next) {
        this.next = next;
    }

    @Override
    public boolean canHandle(int amount) {
        return amount <= 3000;
    }

    @Override
    public String doHandle() {
        log.info("Cto handle the Request");
        return "Cto handle the Request";
    }

}
