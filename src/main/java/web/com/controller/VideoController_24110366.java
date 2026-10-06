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

@WebServlet(urlPatterns = {"/video", "/product"})
public class VideoController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ICategoryService_24110366 categoryService = new CategoryServiceImpl_24110366();
    private final IVideoService_24110366 videoService = new VideoServiceImpl_24110366();
    private static final int PAGE_SIZE = 3; // Yêu cầu đề thi: 3 video / 1 trang

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Lấy danh sách tất cả Category kèm số lượng video (Giải quyết Câu 6)
        List<Category_24110366> categories = categoryService.findAllWithVideoCount();

        int selectedCategoryId = 0;
        String catIdParam = req.getParameter("categoryId");
        if (catIdParam != null && !catIdParam.trim().isEmpty()) {
            try {
                selectedCategoryId = Integer.parseInt(catIdParam.trim());
            } catch (NumberFormatException ignored) {}
        }

        // Mặc định chọn Category đầu tiên nếu chưa chọn
        if (selectedCategoryId == 0 && !categories.isEmpty()) {
            selectedCategoryId = categories.get(0).getCategoryId();
        }

        // Tìm Category đang được chọn
        Category_24110366 selectedCategory = null;
        for (Category_24110366 c : categories) {
            if (c.getCategoryId() == selectedCategoryId) {
                selectedCategory = c;
                break;
            }
        }

        // 2. Xử lý phân trang 3 video / 1 trang theo Category (Giải quyết Câu 5)
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam.trim());
            } catch (NumberFormatException ignored) {}
        }
        if (page < 1) page = 1;

        int totalVideos = 0;
        List<Video_24110366> videoList;

        if (selectedCategoryId > 0) {
            totalVideos = videoService.countByCategoryId(selectedCategoryId);
            videoList = videoService.findByCategoryId(selectedCategoryId, page, PAGE_SIZE);
        } else {
            totalVideos = videoService.countAll();
            videoList = videoService.findAll(page, PAGE_SIZE);
        }

        int totalPages = (int) Math.ceil((double) totalVideos / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        // Truyền dữ liệu sang View Sản phẩm
        req.setAttribute("categories", categories);
        req.setAttribute("selectedCategory", selectedCategory);
        req.setAttribute("selectedCategoryId", selectedCategoryId);
        req.setAttribute("videoList", videoList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalVideos", totalVideos);

        req.getRequestDispatcher("/views/web/video.jsp").forward(req, resp);
    }
}
