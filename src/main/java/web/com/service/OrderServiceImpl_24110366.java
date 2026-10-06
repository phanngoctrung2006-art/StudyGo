package web.com.service;

import web.com.dao.IOrderDao_24110366;
import web.com.dao.OrderDaoImpl_24110366;
import web.com.model.Cart_24110366;
import web.com.model.Order_24110366;

import java.util.List;

public class OrderServiceImpl_24110366 implements IOrderService_24110366 {

    private final IOrderDao_24110366 orderDao;

    public OrderServiceImpl_24110366() {
        this.orderDao = new OrderDaoImpl_24110366();
    }

    public OrderServiceImpl_24110366(IOrderDao_24110366 orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public int createOrder(Order_24110366 order, Cart_24110366 cart, String userEmail) {
        int orderId = orderDao.createOrder(order, cart);
        if (orderId > 0 && userEmail != null && !userEmail.trim().isEmpty()) {
            // Gửi email xác nhận bất đồng bộ qua EmailService
            EmailService_24110366.sendOrderConfirmation(
                    userEmail,
                    order.getFullname(),
                    orderId,
                    order.getAddress(),
                    order.getFormattedTotalAmount()
            );
        }
        return orderId;
    }

    @Override
    public Order_24110366 findById(int orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public List<Order_24110366> findByUsername(String username) {
        return orderDao.findByUsername(username);
    }
}
