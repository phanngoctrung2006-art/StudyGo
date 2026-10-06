package web.com.dao;

import web.com.connection.DBConnection_24110366;
import web.com.model.Video_24110366;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VideoDaoImpl_24110366 implements IVideoDao_24110366 {

    @Override
    public Video_24110366 findById(String videoId) {
        String sql = "SELECT * FROM Videos WHERE VideoId = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Video_24110366 v = new Video_24110366();
                    v.setVideoId(rs.getString("VideoId"));
                    v.setTitle(rs.getString("Title"));
                    v.setPoster(rs.getString("Poster"));
                    v.setViews(rs.getInt("Views"));
                    v.setDescription(rs.getString("Description"));
                    v.setActive(rs.getBoolean("Active"));
                    v.setCategoryId(rs.getInt("CategoryId"));
                    try {
                        double price = rs.getDouble("Price");
                        if (!rs.wasNull() && price > 0) {
                            v.setPrice(price);
                        }
                    } catch (Exception ignored) {}
                    return v;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Video_24110366 findVideoDetailById(String videoId) {
        String sql = "SELECT v.VideoId, v.Title, v.Poster, v.Views, v.Description, v.Active, v.CategoryId, " +
                     "c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount " +
                     "FROM Videos v " +
                     "LEFT JOIN Category c ON v.CategoryId = c.CategoryId " +
                     "WHERE v.VideoId = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVideo(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Video_24110366> findByCategoryId(int categoryId, int page, int pageSize) {
        List<Video_24110366> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT v.VideoId, v.Title, v.Poster, v.Views, v.Description, v.Active, v.CategoryId, " +
                     "c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount " +
                     "FROM Videos v " +
                     "LEFT JOIN Category c ON v.CategoryId = c.CategoryId " +
                     "WHERE v.CategoryId = ? " +
                     "LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToVideo(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countByCategoryId(int categoryId) {
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

    @Override
    public List<Video_24110366> findAll(int page, int pageSize) {
        List<Video_24110366> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String sql = "SELECT v.VideoId, v.Title, v.Poster, v.Views, v.Description, v.Active, v.CategoryId, " +
                     "c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount " +
                     "FROM Videos v " +
                     "LEFT JOIN Category c ON v.CategoryId = c.CategoryId " +
                     "LIMIT ? OFFSET ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToVideo(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Videos";
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
    public int countLikes(String videoId) {
        String sql = "SELECT COUNT(*) FROM Favorites WHERE VideoId = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
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

    @Override
    public int countShares(String videoId) {
        String sql = "SELECT COUNT(*) FROM Shares WHERE VideoId = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
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

    @Override
    public void increaseViews(String videoId) {
        String sql = "UPDATE Videos SET Views = Views + 1 WHERE VideoId = ?";
        try (Connection conn = DBConnection_24110366.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Video_24110366 mapResultSetToVideo(ResultSet rs) throws Exception {
        Video_24110366 v = new Video_24110366();
        v.setVideoId(rs.getString("VideoId"));
        v.setTitle(rs.getString("Title"));
        v.setPoster(rs.getString("Poster"));
        v.setViews(rs.getInt("Views"));
        v.setDescription(rs.getString("Description"));
        v.setActive(rs.getBoolean("Active"));
        v.setCategoryId(rs.getInt("CategoryId"));
        try {
            double price = rs.getDouble("Price");
            if (!rs.wasNull() && price > 0) {
                v.setPrice(price);
            }
        } catch (Exception ignored) {}
        v.setCategoryName(rs.getString("Categoryname"));
        v.setLikeCount(rs.getInt("LikeCount"));
        v.setShareCount(rs.getInt("ShareCount"));
        return v;
    }
}
