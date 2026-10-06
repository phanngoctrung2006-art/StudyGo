package web.com.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Order_24110366 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int orderId;
    private String username;
    private Timestamp orderDate;
    private String fullname;
    private String phone;
    private String address;
    private String note;
    private double totalAmount;
    private String paymentMethod = "COD";
    private String status = "Pending";

    private List<OrderDetail_24110366> details = new ArrayList<>();

    public Order_24110366() {
    }

    public Order_24110366(int orderId, String username, Timestamp orderDate, String fullname, String phone, String address, String note, double totalAmount, String paymentMethod, String status) {
        this.orderId = orderId;
        this.username = username;
        this.orderDate = orderDate;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDetail_24110366> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail_24110366> details) {
        this.details = details;
    }

    public String getFormattedTotalAmount() {
        DecimalFormat df = new DecimalFormat("###,### VNĐ");
        return df.format(totalAmount);
    }
}
