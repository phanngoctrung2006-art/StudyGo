package web.com.service;

import web.com.model.Video_24110366;
import java.util.List;

public interface IVideoService_24110366 {
    Video_24110366 findById(String videoId);
    Video_24110366 findVideoDetailById(String videoId);
    List<Video_24110366> findByCategoryId(int categoryId, int page, int pageSize);
    int countByCategoryId(int categoryId);
    List<Video_24110366> findAll(int page, int pageSize);
    int countAll();
    void increaseViews(String videoId);
}
