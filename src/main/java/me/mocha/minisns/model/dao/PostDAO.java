package me.mocha.minisns.model.dao;

import me.mocha.minisns.exception.ConflictException;
import me.mocha.minisns.exception.NotFoundException;
import me.mocha.minisns.model.DBConnection;
import me.mocha.minisns.model.dto.PostDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class PostDAO {
    private final Logger log = Logger.getLogger(getClass().getName());

    public boolean existsById(long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM posts WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            return resultSet.first();
        } catch (SQLException ex) {
            log.warning("sql error" + ex.getMessage());
        } finally {
            closeResources(conn, pstmt);
        }
        return false;
    }

    public PostDTO findById(long id) {
        if (!existsById(id)) {
            throw new NotFoundException("cannot found post", 404);
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM posts WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            resultSet.first();

            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            String username = resultSet.getString("username");

            long views = resultSet.getLong("views");
            id = resultSet.getLong("id");

            return PostDTO.builder()
                    .title(title)
                    .content(content)
                    .views(views)
                    .username(username)
                    .id(id)
                    .build();
        } catch (SQLException ex) {
            log.warning("sql error " + ex.getMessage());
            throw new NotFoundException("cannot found post", 404);
        } finally {
            closeResources(conn, pstmt);
        }
    }

    public List<PostDTO> findAll() {
        List<PostDTO> result = new LinkedList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM posts";
            pstmt = conn.prepareStatement(sql);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                result.add(PostDTO.builder()
                        .id(resultSet.getLong("id"))
                        .title(resultSet.getString("title"))
                        .content(resultSet.getString("content"))
                        .username(resultSet.getString("username"))
                        .views(resultSet.getLong("views"))
                        .build());

            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        } finally {
            closeResources(conn, pstmt);
        }
        return result;
    }

    public boolean save(PostDTO postDTO) {
        return this.save(postDTO, true);
    }

    public boolean save(PostDTO postDTO, boolean update) throws ConflictException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            if (existsById(postDTO.getId())) {
                if (update) {
                    String sql = "UPDATE posts SET title=?, content=?, views=?, username=? WHERE id=?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, postDTO.getTitle());
                    pstmt.setString(2, postDTO.getContent());
                    pstmt.setLong(3, postDTO.getViews());
                    pstmt.setString(4, postDTO.getUsername());
                    pstmt.setLong(5, postDTO.getId());
                    int i = pstmt.executeUpdate();
                    return i == 1;
                } else {
                    log.warning("already exists post");
                    throw new ConflictException("already exists post", 409);
                }
            }

            String sql = "INSERT INTO posts(title, content, views, username) VALUES(?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, postDTO.getTitle());
            pstmt.setString(2, postDTO.getContent());
            pstmt.setLong(3, postDTO.getViews());
            pstmt.setString(4, postDTO.getUsername());
            int i = pstmt.executeUpdate();
            return i == 1;
        } catch (SQLException ex) {
            log.warning("sql error - " + ex.getMessage());
            return false;
        } finally {
            closeResources(conn, pstmt);
        }
    }

    public boolean deleteById(long id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            if (!existsById(id)) {
                return false;
            } else {
                String sql = "DELETE FROM posts WHERE id=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setLong(1, id);
                int i = pstmt.executeUpdate();
                return i == 1;
            }
        } catch (SQLException e) {
            log.warning("sql error - " + e.getMessage());
            return false;
        } finally {
            closeResources(conn, pstmt);
        }
    }

    private void closeResources(Connection conn, PreparedStatement pstmt) {
        DBConnection.closeResources(conn, pstmt);
    }

}
