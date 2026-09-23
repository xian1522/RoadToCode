package design_pattern.proxy.chain_of_resiponsibility;

public interface Handler {
    boolean canHandle(int amount);
    String doHandle();
    Handler getNext();
}
