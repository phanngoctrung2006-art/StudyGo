package web.com.service;

import web.com.dao.IVideoDao_24110366;
import web.com.dao.VideoDaoImpl_24110366;
import web.com.model.Video_24110366;

import java.util.List;

public class VideoServiceImpl_24110366 implements IVideoService_24110366 {

    private final IVideoDao_24110366 videoDao;

    public VideoServiceImpl_24110366() {
        this.videoDao = new VideoDaoImpl_24110366();
    }

    public VideoServiceImpl_24110366(IVideoDao_24110366 videoDao) {
        this.videoDao = videoDao;
    }

    @Override
    public Video_24110366 findById(String videoId) {
        return videoDao.findById(videoId);
    }

    @Override
    public Video_24110366 findVideoDetailById(String videoId) {
        videoDao.increaseViews(videoId);
        return videoDao.findVideoDetailById(videoId);
    }

    @Override
    public List<Video_24110366> findByCategoryId(int categoryId, int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 3;
        return videoDao.findByCategoryId(categoryId, page, pageSize);
    }

    @Override
    public int countByCategoryId(int categoryId) {
        return videoDao.countByCategoryId(categoryId);
    }

    @Override
    public List<Video_24110366> findAll(int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 3;
        return videoDao.findAll(page, pageSize);
    }

    @Override
    public int countAll() {
        return videoDao.countAll();
    }

    @Override
    public void increaseViews(String videoId) {
        videoDao.increaseViews(videoId);
    }
}
