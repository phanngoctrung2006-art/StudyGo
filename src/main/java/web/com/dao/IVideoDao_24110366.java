package web.com.dao;

import web.com.model.Video_24110366;
import java.util.List;

public interface IVideoDao_24110366 {
    Video_24110366 findById(String videoId);
    Video_24110366 findVideoDetailById(String videoId);
    List<Video_24110366> findByCategoryId(int categoryId, int page, int pageSize);
    int countByCategoryId(int categoryId);
    List<Video_24110366> findAll(int page, int pageSize);
    int countAll();
    int countLikes(String videoId);
    int countShares(String videoId);
    void increaseViews(String videoId);
}
