package web.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.com.model.User_24110366;
import web.com.service.EmailService_24110366;
import web.com.service.IUserService_24110366;
import web.com.service.UserServiceImpl_24110366;
import web.com.utils.OTPUtils_24110366;

import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24110366 userService = new UserServiceImpl_24110366();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        // Validate cơ bản
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng điền đầy đủ các thông tin bắt buộc!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        username = username.trim();
        email = email.trim();

        // Kiểm tra tồn tại
        if (userService.findByUsername(username) != null) {
            req.setAttribute("error", "Tên đăng nhập '" + username + "' đã tồn tại!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        if (userService.findByEmail(email) != null) {
            req.setAttribute("error", "Email '" + email + "' đã được đăng ký!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        try {
            // Sinh mã OTP 6 số
            String otp = OTPUtils_24110366.generate6DigitOTP();

            // Gửi OTP qua mail
            EmailService_24110366.sendEmailOTP(email, fullname != null ? fullname : username, otp);

            // Lưu tài khoản với Active = false
            User_24110366 newUser = new User_24110366(username, password, phone, fullname, email, false, false, "default.png");
            userService.insert(newUser);

            // Lưu thông tin OTP vào Session (hạn 5 phút)
            HttpSession session = req.getSession();
            session.setAttribute("otp", otp);
            session.setAttribute("otpTime", System.currentTimeMillis());
            session.setAttribute("pendingUsername", username);
            session.setAttribute("pendingEmail", email);

            resp.sendRedirect(req.getContextPath() + "/verify-otp");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Không thể gửi email OTP: " + e.getMessage());
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
        }
    }
}
