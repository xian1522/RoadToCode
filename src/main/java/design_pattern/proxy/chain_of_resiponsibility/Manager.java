package design_pattern.proxy.chain_of_resiponsibility;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
public class Manager implements Handler{
    private Handler next;

    public Manager(Handler next) {
        this.next = next;
    }

    @Override
    public boolean canHandle(int amount) {
        return amount <= 1000;
    }

    @Override
    public String doHandle() {
        log.info("Manager handle the request: ");
        return "Manager handle the request";
    }
}
