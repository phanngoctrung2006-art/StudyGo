package web.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.com.model.Cart_24110366;
import web.com.model.Order_24110366;
import web.com.model.User_24110366;
import web.com.service.IOrderService_24110366;
import web.com.service.OrderServiceImpl_24110366;

import java.io.IOException;

@WebServlet(urlPatterns = "/checkout")
public class CheckoutController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IOrderService_24110366 orderService = new OrderServiceImpl_24110366();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User_24110366 user = (User_24110366) session.getAttribute("user");

        // RÀNG BUỘC: Chưa đăng nhập không được thanh toán
        if (user == null) {
            session.setAttribute("authMessage", "Vui lòng đăng nhập để tiến hành thanh toán đơn hàng!");
            session.setAttribute("redirectUrl", req.getContextPath() + "/checkout");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Cart_24110366 cart = (Cart_24110366) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            session.setAttribute("cartMessage", "Giỏ hàng của bạn đang trống! Vui lòng chọn sản phẩm trước khi thanh toán.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.setAttribute("user", user);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User_24110366 user = (User_24110366) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Cart_24110366 cart = (Cart_24110366) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String note = req.getParameter("note");
        String paymentMethod = req.getParameter("paymentMethod");
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "COD";
        }

        if (fullname == null || fullname.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng điền đầy đủ Họ tên, Số điện thoại và Địa chỉ nhận hàng!");
            req.setAttribute("user", user);
            req.setAttribute("cart", cart);
            req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
            return;
        }

        Order_24110366 order = new Order_24110366();
        order.setUsername(user.getUsername());
        order.setFullname(fullname.trim());
        order.setPhone(phone.trim());
        order.setAddress(address.trim());
        order.setNote(note != null ? note.trim() : "");
        order.setTotalAmount(cart.getTotalAmount());
        order.setPaymentMethod(paymentMethod);
        order.setStatus("Pending (COD)");

        int orderId = orderService.createOrder(order, cart, user.getEmail());

        if (orderId > 0) {
            // Xóa sạch giỏ hàng sau khi đặt thành công
            cart.clear();
            resp.sendRedirect(req.getContextPath() + "/order-success?orderId=" + orderId);
        } else {
            req.setAttribute("error", "Có lỗi xảy ra trong quá trình tạo đơn hàng. Vui lòng thử lại!");
            req.setAttribute("user", user);
            req.setAttribute("cart", cart);
            req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
        }
    }
}
