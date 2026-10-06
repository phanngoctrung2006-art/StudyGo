package web.com.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import web.com.model.User_24110366;
import web.com.service.IUserService_24110366;
import web.com.service.UserServiceImpl_24110366;
import web.com.utils.Constant_24110366;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@WebServlet(urlPatterns = {
        "/admin/users",
        "/admin/users/create",
        "/admin/users/edit",
        "/admin/users/delete"
})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,      // 1 MB
        maxFileSize = 1024 * 1024 * 10,       // 10 MB
        maxRequestSize = 1024 * 1024 * 20     // 20 MB
)
public class UserController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IUserService_24110366 userService = new UserServiceImpl_24110366();
    private static final int PAGE_SIZE = 6; // Yêu cầu đề thi: 6 user / 1 trang

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User_24110366 currentUser = (session != null) ? (User_24110366) session.getAttribute("user") : null;
        if (currentUser == null || !currentUser.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();

        switch (path) {
            case "/admin/users/create":
                showCreateForm(req, resp);
                break;
            case "/admin/users/edit":
                showEditForm(req, resp);
                break;
            case "/admin/users/delete":
                deleteUser(req, resp);
                break;
            case "/admin/users":
            default:
                listUsers(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        User_24110366 currentUser = (session != null) ? (User_24110366) session.getAttribute("user") : null;
        if (currentUser == null || !currentUser.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();

        switch (path) {
            case "/admin/users/create":
                createUser(req, resp);
                break;
            case "/admin/users/edit":
                updateUser(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/admin/users");
                break;
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {}
        }
        if (page < 1) page = 1;

        int totalUsers = userService.count();
        int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        List<User_24110366> userList = userService.findAll(page, PAGE_SIZE);

        req.setAttribute("userList", userList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalUsers", totalUsers);
        req.setAttribute("pageSize", PAGE_SIZE);

        req.getRequestDispatcher("/views/admin/users/list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("isEdit", false);
        req.setAttribute("user", new User_24110366());
        req.getRequestDispatcher("/views/admin/users/form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        User_24110366 user = userService.findByUsername(username);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        req.setAttribute("isEdit", true);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/views/admin/users/form.jsp").forward(req, resp);
    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        boolean admin = req.getParameter("admin") != null;
        boolean active = req.getParameter("active") != null;

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ các trường bắt buộc!");
            req.setAttribute("isEdit", false);
            req.getRequestDispatcher("/views/admin/users/form.jsp").forward(req, resp);
            return;
        }

        if (userService.findByUsername(username) != null) {
            req.setAttribute("error", "Username '" + username + "' đã tồn tại!");
            req.setAttribute("isEdit", false);
            req.getRequestDispatcher("/views/admin/users/form.jsp").forward(req, resp);
            return;
        }

        String imageName = handleFileUpload(req);
        if (imageName == null || imageName.isEmpty()) {
            imageName = "default.png";
        }

        User_24110366 user = new User_24110366(username, password, phone, fullname, email, admin, active, imageName);
        userService.insert(user);

        resp.sendRedirect(req.getContextPath() + "/admin/users?message=create_success");
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        boolean admin = req.getParameter("admin") != null;
        boolean active = req.getParameter("active") != null;
        String existingImage = req.getParameter("existingImage");

        User_24110366 existingUser = userService.findByUsername(username);
        if (existingUser == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        String imageName = handleFileUpload(req);
        if (imageName == null || imageName.isEmpty()) {
            imageName = (existingImage != null && !existingImage.isEmpty()) ? existingImage : existingUser.getImages();
        }

        User_24110366 user = new User_24110366(username, password, phone, fullname, email, admin, active, imageName);
        userService.update(user);

        resp.sendRedirect(req.getContextPath() + "/admin/users?message=update_success");
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        if (username != null && !username.trim().isEmpty()) {
            userService.delete(username);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users?message=delete_success");
    }

    private String handleFileUpload(HttpServletRequest req) {
        try {
            Part part = req.getPart("imageFile");
            if (part != null && part.getSize() > 0) {
                String originalFilename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String ext = "";
                int dotIndex = originalFilename.lastIndexOf('.');
                if (dotIndex > 0) {
                    ext = originalFilename.substring(dotIndex);
                }
                String uniqueFileName = "user_" + System.currentTimeMillis() + ext;
                File uploadDir = new File(Constant_24110366.DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                part.write(Constant_24110366.DIR + File.separator + uniqueFileName);
                return uniqueFileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
