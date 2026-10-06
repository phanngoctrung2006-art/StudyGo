package web.com.model;

import java.io.Serializable;

public class Category_24110366 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int categoryId;
    private String categoryname;
    private String categorycode;
    private String images;
    private boolean status;
    
    // Thuộc tính phụ trợ phục vụ Câu 6 (đếm số lượng video trong category)
    private int videoCount;

    public Category_24110366() {
    }

    public Category_24110366(int categoryId, String categoryname, String categorycode, String images, boolean status) {
        this.categoryId = categoryId;
        this.categoryname = categoryname;
        this.categorycode = categorycode;
        this.images = images;
        this.status = status;
    }

    public Category_24110366(int categoryId, String categoryname, String categorycode, String images, boolean status, int videoCount) {
        this.categoryId = categoryId;
        this.categoryname = categoryname;
        this.categorycode = categorycode;
        this.images = images;
        this.status = status;
        this.videoCount = videoCount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryname='" + categoryname + '\'' +
                ", categorycode='" + categorycode + '\'' +
                ", images='" + images + '\'' +
                ", status=" + status +
                ", videoCount=" + videoCount +
                '}';
    }
}
