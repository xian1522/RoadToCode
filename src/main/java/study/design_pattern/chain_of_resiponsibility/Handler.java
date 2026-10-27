package study.design_pattern.chain_of_resiponsibility;

public interface Handler {
    boolean canHandle(int amount);
    String doHandle();
    Handler getNext();
}
