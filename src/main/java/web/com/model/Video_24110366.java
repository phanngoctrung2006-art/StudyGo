package web.com.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Video_24110366 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String videoId;
    private String title;
    private String poster;
    private int views;
    private String description;
    private boolean active;
    private int categoryId;
    private double price = 150000.0; // Giá bán mặc định

    // Thuộc tính phụ trợ phục vụ hiển thị ở Câu 4 và Câu 5
    private String categoryName;
    private int likeCount;
    private int shareCount;

    public Video_24110366() {
    }

    public Video_24110366(String videoId, String title, String poster, int views, String description, boolean active, int categoryId) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.views = views;
        this.description = description;
        this.active = active;
        this.categoryId = categoryId;
    }

    public Video_24110366(String videoId, String title, String poster, int views, String description, boolean active, int categoryId, String categoryName, int likeCount, int shareCount) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.views = views;
        this.description = description;
        this.active = active;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
    }

    public Video_24110366(String videoId, String title, String poster, int views, String description, boolean active, int categoryId, double price, String categoryName, int likeCount, int shareCount) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.views = views;
        this.description = description;
        this.active = active;
        this.categoryId = categoryId;
        this.price = price;
        this.categoryName = categoryName;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("###,### VNĐ");
        return df.format(price);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                ", views=" + views +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", categoryName='" + categoryName + '\'' +
                ", likeCount=" + likeCount +
                ", shareCount=" + shareCount +
                '}';
    }
}
