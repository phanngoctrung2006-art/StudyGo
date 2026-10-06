package web.com.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CartItem_24110366 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Video_24110366 video;
    private int quantity;
    private double price;

    public CartItem_24110366() {
    }

    public CartItem_24110366(Video_24110366 video, int quantity, double price) {
        this.video = video;
        this.quantity = quantity;
        this.price = price;
    }

    public Video_24110366 getVideo() {
        return video;
    }

    public void setVideo(Video_24110366 video) {
        this.video = video;
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
