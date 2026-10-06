package web.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.com.model.Category_24110366;
import web.com.model.Video_24110366;
import web.com.service.CategoryServiceImpl_24110366;
import web.com.service.ICategoryService_24110366;
import web.com.service.IVideoService_24110366;
import web.com.service.VideoServiceImpl_24110366;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ICategoryService_24110366 categoryService = new CategoryServiceImpl_24110366();
    private final IVideoService_24110366 videoService = new VideoServiceImpl_24110366();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy danh sách danh mục kèm số lượng video
        List<Category_24110366> categories = categoryService.findAllWithVideoCount();

        // 2. Lấy danh sách video nổi bật cho trang chủ (tối đa 6 video)
        List<Video_24110366> featuredVideos = videoService.findAll(1, 6);
        int totalVideos = videoService.countAll();

        req.setAttribute("categories", categories);
        req.setAttribute("featuredVideos", featuredVideos);
        req.setAttribute("totalVideos", totalVideos);

        req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
    }
}
