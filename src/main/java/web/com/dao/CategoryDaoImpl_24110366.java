package web.com.dao;

import web.com.connection.DBConnection_24110366;
import web.com.model.Category_24110366;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl_24110366 implements ICategoryDao_24110366 {

    @Override
    public List<Category_24110366> findAll() {
        List<Category_24110366> list = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category_24110366 c = new Category_24110366();
                c.setCategoryId(rs.getInt("CategoryId"));
                c.setCategoryname(rs.getString("Categoryname"));
                c.setCategorycode(rs.getString("Categorycode"));
                c.setImages(rs.getString("Images"));
                c.setStatus(rs.getBoolean("Status"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Category_24110366> findAllWithVideoCount() {
        List<Category_24110366> list = new ArrayList<>();
        String sql = "SELECT c.CategoryId, c.Categoryname, c.Categorycode, c.Images, c.Status, COUNT(v.VideoId) AS VideoCount " +
                     "FROM Category c " +
                     "LEFT JOIN Videos v ON c.CategoryId = v.CategoryId " +
                     "GROUP BY c.CategoryId, c.Categoryname, c.Categorycode, c.Images, c.Status";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category_24110366 c = new Category_24110366();
                c.setCategoryId(rs.getInt("CategoryId"));
                c.setCategoryname(rs.getString("Categoryname"));
                c.setCategorycode(rs.getString("Categorycode"));
                c.setImages(rs.getString("Images"));
                c.setStatus(rs.getBoolean("Status"));
                c.setVideoCount(rs.getInt("VideoCount"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Category_24110366 findById(int id) {
        String sql = "SELECT * FROM Category WHERE CategoryId = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category_24110366 c = new Category_24110366();
                    c.setCategoryId(rs.getInt("CategoryId"));
                    c.setCategoryname(rs.getString("Categoryname"));
                    c.setCategorycode(rs.getString("Categorycode"));
                    c.setImages(rs.getString("Images"));
                    c.setStatus(rs.getBoolean("Status"));
                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int countVideosByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Videos WHERE CategoryId = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
