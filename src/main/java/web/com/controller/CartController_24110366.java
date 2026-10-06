package web.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.com.model.Cart_24110366;
import web.com.model.User_24110366;
import web.com.model.Video_24110366;
import web.com.service.IVideoService_24110366;
import web.com.service.VideoServiceImpl_24110366;

import java.io.IOException;

@WebServlet(urlPatterns = {
        "/cart",
        "/cart/add",
        "/cart/update",
        "/cart/delete",
        "/cart/clear"
})
public class CartController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IVideoService_24110366 videoService = new VideoServiceImpl_24110366();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User_24110366 user = (User_24110366) session.getAttribute("user");

        // RÀNG BUỘC: Chưa đăng nhập thì cảnh báo và chuyển đến trang đăng nhập
        if (user == null) {
            session.setAttribute("authMessage", "Vui lòng đăng nhập để sử dụng tính năng giỏ hàng và mua hàng!");
            String referer = req.getHeader("referer");
            session.setAttribute("redirectUrl", (referer != null && !referer.contains("/login")) ? referer : (req.getContextPath() + "/cart"));
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy giỏ hàng từ Session hoặc tạo mới nếu chưa có
        Cart_24110366 cart = (Cart_24110366) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart_24110366();
            session.setAttribute("cart", cart);
        }

        String path = req.getServletPath();

        switch (path) {
            case "/cart/add":
                addToCart(req, resp, cart);
                break;
            case "/cart/update":
                updateCart(req, resp, cart);
                break;
            case "/cart/delete":
                deleteCartItem(req, resp, cart);
                break;
            case "/cart/clear":
                clearCart(req, resp, cart);
                break;
            case "/cart":
            default:
                showCart(req, resp, cart);
                break;
        }
    }

    private void showCart(HttpServletRequest req, HttpServletResponse resp, Cart_24110366 cart) throws ServletException, IOException {
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/views/web/cart.jsp").forward(req, resp);
    }

    private void addToCart(HttpServletRequest req, HttpServletResponse resp, Cart_24110366 cart) throws IOException {
        String videoId = req.getParameter("videoId");
        int quantity = 1;
        String qtyParam = req.getParameter("quantity");
        if (qtyParam != null && !qtyParam.trim().isEmpty()) {
            try {
                quantity = Integer.parseInt(qtyParam.trim());
            } catch (NumberFormatException ignored) {}
        }

        if (videoId != null && !videoId.trim().isEmpty()) {
            Video_24110366 video = videoService.findById(videoId.trim());
            if (video != null) {
                String message = cart.add(video, quantity);
                req.getSession().setAttribute("cartMessage", message);
            }
        }

        String referer = req.getHeader("referer");
        if (referer != null && !referer.isEmpty()) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    private void updateCart(HttpServletRequest req, HttpServletResponse resp, Cart_24110366 cart) throws IOException {
        String videoId = req.getParameter("videoId");
        String action = req.getParameter("action"); // 'increase', 'decrease', or manual input
        int quantity = 1;

        String qtyParam = req.getParameter("quantity");
        if (qtyParam != null && !qtyParam.trim().isEmpty()) {
            try {
                quantity = Integer.parseInt(qtyParam.trim());
            } catch (NumberFormatException ignored) {}
        }

        if ("increase".equals(action)) {
            if (cart.getMap().containsKey(videoId)) {
                quantity = cart.getMap().get(videoId).getQuantity() + 1;
            }
        } else if ("decrease".equals(action)) {
            if (cart.getMap().containsKey(videoId)) {
                quantity = cart.getMap().get(videoId).getQuantity() - 1;
            }
        }

        String msg = cart.update(videoId, quantity);
        req.getSession().setAttribute("cartMessage", msg);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void deleteCartItem(HttpServletRequest req, HttpServletResponse resp, Cart_24110366 cart) throws IOException {
        String videoId = req.getParameter("videoId");
        if (videoId != null) {
            cart.remove(videoId.trim());
            req.getSession().setAttribute("cartMessage", "Đã xóa sản phẩm khỏi giỏ hàng!");
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void clearCart(HttpServletRequest req, HttpServletResponse resp, Cart_24110366 cart) throws IOException {
        cart.clear();
        req.getSession().setAttribute("cartMessage", "Đã làm trống giỏ hàng!");
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
