
package PruebasUnitarias;

import java.util.List;

public class OrderService {

    private OrderRepository orderRepository;
    private PaymentService paymentService;


    public OrderService(OrderRepository orderRepository, PaymentService
            paymentService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    public boolean placeOrder(Order order) {

        boolean paymentProcessed =
                paymentService.processPayment(order.getAmount());
        if (paymentProcessed) {
            orderRepository.save(order);
            return true;
        }
        return false;
    }


    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void cancelOrder(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order notfound"));
        orderRepository.delete(order);
    }


    public List<Order> listAllOrders() {


        return orderRepository.findAll();

    }



}

