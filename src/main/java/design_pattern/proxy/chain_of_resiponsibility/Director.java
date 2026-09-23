package design_pattern.proxy.chain_of_resiponsibility;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Director implements Handler{
    private Handler next;
    public Director(Handler next) {
        this.next = next;
    }

    @Override
    public boolean canHandle(int amount) {
        return amount <= 2000;
    }

    @Override
    public String doHandle() {
        log.info("Director handle the request: ");
        return "Director handle the request";
    }
}
