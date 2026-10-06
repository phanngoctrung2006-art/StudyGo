package web.com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.com.model.Video_24110366;
import web.com.service.IVideoService_24110366;
import web.com.service.VideoServiceImpl_24110366;

import java.io.IOException;

@WebServlet(urlPatterns = {"/video/detail", "/detail"})
public class VideoDetailController_24110366 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IVideoService_24110366 videoService = new VideoServiceImpl_24110366();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoId = req.getParameter("id");
        if (videoId == null || videoId.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Video_24110366 video = videoService.findVideoDetailById(videoId.trim());
        if (video == null) {
            req.setAttribute("error", "Không tìm thấy video có mã: " + videoId);
            req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("video", video);
        req.getRequestDispatcher("/views/web/video-detail.jsp").forward(req, resp);
    }
}
