package web.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.com.model.User_24110366;
import web.com.service.IUserService_24110366;
import web.com.service.UserServiceImpl_24110366;

import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24110366 userService = new UserServiceImpl_24110366();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("authMessage") != null) {
            req.setAttribute("error", session.getAttribute("authMessage"));
            session.removeAttribute("authMessage");
        }
        req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
            return;
        }

        User_24110366 user = userService.login(username.trim(), password);

        if (user == null) {
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không chính xác!");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
            return;
        }

        if (!user.isActive()) {
            req.setAttribute("error", "Tài khoản chưa được kích hoạt OTP! Vui lòng kiểm tra email.");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
            return;
        }

        // Lưu thông tin người dùng vào Session
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        // Phân quyền: Nếu là admin -> vào trang quản trị
        if (user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/admin/home");
        } else {
            // Nếu có URL đang chờ (ví dụ từ giỏ hàng / thanh toán) -> chuyển tiếp đến đó
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                session.removeAttribute("redirectUrl");
                resp.sendRedirect(redirectUrl);
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        }
    }
}
