package web.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.com.model.Order_24110366;
import web.com.service.IOrderService_24110366;
import web.com.service.OrderServiceImpl_24110366;

import java.io.IOException;

@WebServlet(urlPatterns = "/order-success")
public class OrderSuccessController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IOrderService_24110366 orderService = new OrderServiceImpl_24110366();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("orderId");
        int orderId = -1;
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                orderId = Integer.parseInt(idParam.trim());
            } catch (NumberFormatException ignored) {}
        }

        if (orderId > 0) {
            Order_24110366 order = orderService.findById(orderId);
            req.setAttribute("order", order);
        }

        req.getRequestDispatcher("/views/web/order-success.jsp").forward(req, resp);
    }
}
