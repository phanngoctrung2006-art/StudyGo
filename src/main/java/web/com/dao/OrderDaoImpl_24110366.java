package web.com.dao;

import web.com.connection.DBConnection_24110366;
import web.com.model.CartItem_24110366;
import web.com.model.Cart_24110366;
import web.com.model.OrderDetail_24110366;
import web.com.model.Order_24110366;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl_24110366 implements IOrderDao_24110366 {

    @Override
    public int createOrder(Order_24110366 order, Cart_24110366 cart) {
        String sqlOrder = "INSERT INTO Orders (Username, Fullname, Phone, Address, Note, TotalAmount, PaymentMethod, Status) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO OrderDetails (OrderId, VideoId, Quantity, Price) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        ResultSet rs = null;

        try {
            conn = DBConnection_24110366.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Thêm Order
            psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setString(1, order.getUsername());
            psOrder.setString(2, order.getFullname());
            psOrder.setString(3, order.getPhone());
            psOrder.setString(4, order.getAddress());
            psOrder.setString(5, order.getNote());
            psOrder.setDouble(6, order.getTotalAmount());
            psOrder.setString(7, order.getPaymentMethod() != null ? order.getPaymentMethod() : "COD");
            psOrder.setString(8, order.getStatus() != null ? order.getStatus() : "Pending");

            int affectedRows = psOrder.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return -1;
            }

            rs = psOrder.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                conn.rollback();
                return -1;
            }

            // 2. Thêm OrderDetails
            psDetail = conn.prepareStatement(sqlDetail);
            for (CartItem_24110366 item : cart.getItems()) {
                psDetail.setInt(1, orderId);
                psDetail.setString(2, item.getVideo().getVideoId());
                psDetail.setInt(3, item.getQuantity());
                psDetail.setDouble(4, item.getPrice());
                psDetail.addBatch();
            }
            psDetail.executeBatch();

            conn.commit(); // Hoàn tất transaction
            return orderId;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psOrder != null) psOrder.close();
                if (psDetail != null) psDetail.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Order_24110366 findById(int orderId) {
        String sqlOrder = "SELECT * FROM Orders WHERE OrderId = ?";
        String sqlDetails = "SELECT d.*, v.Title, v.Poster FROM OrderDetails d " +
                            "JOIN Videos v ON d.VideoId = v.VideoId WHERE d.OrderId = ?";

        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement psOrder = conn.prepareStatement(sqlOrder)) {
            psOrder.setInt(1, orderId);
            try (ResultSet rsOrder = psOrder.executeQuery()) {
                if (rsOrder.next()) {
                    Order_24110366 order = new Order_24110366();
                    order.setOrderId(rsOrder.getInt("OrderId"));
                    order.setUsername(rsOrder.getString("Username"));
                    order.setOrderDate(rsOrder.getTimestamp("OrderDate"));
                    order.setFullname(rsOrder.getString("Fullname"));
                    order.setPhone(rsOrder.getString("Phone"));
                    order.setAddress(rsOrder.getString("Address"));
                    order.setNote(rsOrder.getString("Note"));
                    order.setTotalAmount(rsOrder.getDouble("TotalAmount"));
                    order.setPaymentMethod(rsOrder.getString("PaymentMethod"));
                    order.setStatus(rsOrder.getString("Status"));

                    // Lấy chi tiết đơn
                    try (PreparedStatement psDetails = conn.prepareStatement(sqlDetails)) {
                        psDetails.setInt(1, orderId);
                        try (ResultSet rsDetails = psDetails.executeQuery()) {
                            List<OrderDetail_24110366> list = new ArrayList<>();
                            while (rsDetails.next()) {
                                OrderDetail_24110366 d = new OrderDetail_24110366();
                                d.setOrderDetailId(rsDetails.getInt("OrderDetailId"));
                                d.setOrderId(rsDetails.getInt("OrderId"));
                                d.setVideoId(rsDetails.getString("VideoId"));
                                d.setQuantity(rsDetails.getInt("Quantity"));
                                d.setPrice(rsDetails.getDouble("Price"));
                                d.setVideoTitle(rsDetails.getString("Title"));
                                d.setVideoPoster(rsDetails.getString("Poster"));
                                list.add(d);
                            }
                            order.setDetails(list);
                        }
                    }
                    return order;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order_24110366> findByUsername(String username) {
        List<Order_24110366> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE Username = ? ORDER BY OrderDate DESC";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order_24110366 order = new Order_24110366();
                    order.setOrderId(rs.getInt("OrderId"));
                    order.setUsername(rs.getString("Username"));
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setFullname(rs.getString("Fullname"));
                    order.setPhone(rs.getString("Phone"));
                    order.setAddress(rs.getString("Address"));
                    order.setNote(rs.getString("Note"));
                    order.setTotalAmount(rs.getDouble("TotalAmount"));
                    order.setPaymentMethod(rs.getString("PaymentMethod"));
                    order.setStatus(rs.getString("Status"));
                    list.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
