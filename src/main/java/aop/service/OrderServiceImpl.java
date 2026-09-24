package aop.service;

public class OrderServiceImpl implements OrderService {
    @Override
    public String placeOrder(String item, int quantity) {
        System.out.println("------------OrderServiceImpl.placeOrder---------------");
        return "订单已创建: " + item + " x" + quantity;
    }
}
