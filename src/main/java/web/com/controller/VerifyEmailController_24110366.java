package web.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import web.com.service.IUserService_24110366;
import web.com.service.UserServiceImpl_24110366;

import java.io.IOException;

@WebServlet(urlPatterns = "/verify-otp")
public class VerifyEmailController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24110366 userService = new UserServiceImpl_24110366();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/web/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String inputOtp = req.getParameter("otp");
        HttpSession session = req.getSession();

        String sessionOtp = (String) session.getAttribute("otp");
        Long otpTime = (Long) session.getAttribute("otpTime");
        String pendingUsername = (String) session.getAttribute("pendingUsername");

        if (sessionOtp == null || pendingUsername == null) {
            req.setAttribute("error", "Phiên xác thực đã kết thúc hoặc không hợp lệ. Vui lòng đăng ký lại!");
            req.getRequestDispatcher("/views/web/verify-otp.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra hết hạn 5 phút (300.000 ms)
        if (otpTime != null && (System.currentTimeMillis() - otpTime > 5 * 60 * 1000)) {
            session.removeAttribute("otp");
            session.removeAttribute("otpTime");
            req.setAttribute("error", "Mã OTP đã hết hạn sau 5 phút. Vui lòng thử đăng ký lại!");
            req.getRequestDispatcher("/views/web/verify-otp.jsp").forward(req, resp);
            return;
        }

        if (inputOtp != null && inputOtp.trim().equals(sessionOtp)) {
            // Kích hoạt tài khoản thành công
            userService.updateActive(pendingUsername, true);

            // Xóa session tạm
            session.removeAttribute("otp");
            session.removeAttribute("otpTime");
            session.removeAttribute("pendingUsername");
            session.removeAttribute("pendingEmail");

            req.setAttribute("message", "Kích hoạt tài khoản thành công! Bạn có thể đăng nhập ngay bây giờ.");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Mã OTP không chính xác. Vui lòng kiểm tra lại email!");
            req.getRequestDispatcher("/views/web/verify-otp.jsp").forward(req, resp);
        }
    }
}
