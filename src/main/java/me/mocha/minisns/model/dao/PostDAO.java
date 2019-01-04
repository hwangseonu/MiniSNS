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

    private final Connection db = DBConnection.getConnection();
    private final Logger log = Logger.getLogger(getClass().getName());

    public boolean existsById(long id) {
        try {
            String sql = "SELECT * FROM posts WHERE id=?";
            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setLong(1, id);
            return pstmt.executeQuery().first();
        } catch (SQLException ex) {
            log.warning("sql error" + ex.getMessage());
        }
        return false;
    }

    public PostDTO findById(long id) {
        if (!existsById(id)) {
            throw new NotFoundException("cannot found user", 404);
        }
        try {
            String sql = "SELECT * FROM posts WHERE id=?";
            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.first();
            return PostDTO.builder()
                    .title(resultSet.getString("title"))
                    .content(resultSet.getString("content"))
                    .views(resultSet.getLong("views"))
                    .username(resultSet.getString("username"))
                    .id(resultSet.getLong("id"))
                    .build();
        } catch (SQLException ex) {
            log.warning("sql error " + ex.getMessage());
            throw new NotFoundException("cannot found post", 404);
        }
    }

    public List<PostDTO> findAll() {
        List<PostDTO> result = new LinkedList<>();
        try {
            String sql = "SELECT * FROM posts";
            PreparedStatement pstmt = db.prepareStatement(sql);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                result.add(PostDTO.builder()
                        .id(resultSet.getLong("id"))
                        .title(resultSet.getString("title"))
                        .content(resultSet.getString("content"))
                        .username(resultSet.getString("username"))
                        .views(resultSet.getLong("view"))
                        .build());

            }
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
        return result;
    }

    public boolean save(PostDTO postDTO) {
        return this.save(postDTO, true);
    }

    public boolean save(PostDTO postDTO, boolean update) throws ConflictException {
        try {
            if (existsById(postDTO.getId())) {
                if (update) {
                    String sql = "UPDATE posts SET title=?, content=?, views=?, username=? WHERE id=?";
                    PreparedStatement pstmt = db.prepareStatement(sql);
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
            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setString(1, postDTO.getTitle());
            pstmt.setString(2, postDTO.getContent());
            pstmt.setLong(3, postDTO.getViews());
            pstmt.setString(4, postDTO.getUsername());
            int i = pstmt.executeUpdate();
            return i == 1;
        } catch (SQLException ex) {
            log.warning("sql error - " + ex.getMessage());
            return false;
        }
    }

}
