package web.com.dao;

import web.com.connection.DBConnection_24110366;
import web.com.model.User_24110366;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl_24110366 implements IUserDao_24110366 {

    @Override
    public User_24110366 findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User_24110366 findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User_24110366> findAll() {
        List<User_24110366> list = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<User_24110366> findAll(int page, int pageSize) {
        List<User_24110366> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM Users LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToUser(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void insert(User_24110366 user) {
        String sql = "INSERT INTO Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getEmail());
            ps.setBoolean(6, user.isAdmin());
            ps.setBoolean(7, user.isActive());
            ps.setString(8, user.getImages());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm user: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(User_24110366 user) {
        String sql = "UPDATE Users SET Password = ?, Phone = ?, Fullname = ?, Email = ?, Admin = ?, Active = ?, Images = ? WHERE Username = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getEmail());
            ps.setBoolean(5, user.isAdmin());
            ps.setBoolean(6, user.isActive());
            ps.setString(7, user.getImages());
            ps.setString(8, user.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật user: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String username) {
        String sql = "DELETE FROM Users WHERE Username = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa user: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateActive(String username, boolean active) {
        String sql = "UPDATE Users SET Active = ? WHERE Username = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, active);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User_24110366 mapResultSetToUser(ResultSet rs) throws Exception {
        User_24110366 user = new User_24110366();
        user.setUsername(rs.getString("Username"));
        user.setPassword(rs.getString("Password"));
        user.setPhone(rs.getString("Phone"));
        user.setFullname(rs.getString("Fullname"));
        user.setEmail(rs.getString("Email"));
        user.setAdmin(rs.getBoolean("Admin"));
        user.setActive(rs.getBoolean("Active"));
        user.setImages(rs.getString("Images"));
        return user;
    }
}
