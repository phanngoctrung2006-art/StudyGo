package web.com.dao;

import web.com.model.Cart_24110366;
import web.com.model.Order_24110366;
import java.util.List;

public interface IOrderDao_24110366 {
    int createOrder(Order_24110366 order, Cart_24110366 cart);
    Order_24110366 findById(int orderId);
    List<Order_24110366> findByUsername(String username);
}
