package web.com.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class OrderDetail_24110366 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int orderDetailId;
    private int orderId;
    private String videoId;
    private int quantity;
    private double price;

    // Thuộc tính phụ trợ hiển thị chi tiết sản phẩm
    private String videoTitle;
    private String videoPoster;

    public OrderDetail_24110366() {
    }

    public OrderDetail_24110366(int orderDetailId, int orderId, String videoId, int quantity, double price) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.videoId = videoId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoPoster() {
        return videoPoster;
    }

    public void setVideoPoster(String videoPoster) {
        this.videoPoster = videoPoster;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public String getFormattedPrice() {
        DecimalFormat df = new DecimalFormat("###,### VNĐ");
        return df.format(price);
    }

    public String getFormattedTotalPrice() {
        DecimalFormat df = new DecimalFormat("###,### VNĐ");
        return df.format(getTotalPrice());
    }
}
