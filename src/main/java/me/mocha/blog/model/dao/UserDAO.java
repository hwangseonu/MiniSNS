package me.mocha.blog.model.dao;

import me.mocha.blog.exception.ConflictException;
import me.mocha.blog.exception.NotFoundException;
import me.mocha.blog.model.DBConnection;
import me.mocha.blog.model.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserDAO {

    private final Logger log = Logger.getLogger(UserDAO.class.getName());

    public boolean existsByNickname(String nickname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE nickname=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nickname);
            resultSet = pstmt.executeQuery();
            boolean result = resultSet.first();
            return result;
        } catch (SQLException ex) {
            log.warning("sql error" + ex.getMessage());
        } finally {
            closeResources(conn, pstmt);
        }
        return false;
    }

    public boolean existsByUsername(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            resultSet = pstmt.executeQuery();
            return resultSet.first();
        } catch (SQLException ex) {
            log.warning("sql error" + ex.getMessage());
        } finally {
            closeResources(conn, pstmt);
        }
        return false;
    }

    public boolean save(UserDTO userDTO) throws ConflictException {
        if (existsByUsername(userDTO.getUsername()) || existsByNickname(userDTO.getNickname())) {
            log.warning("already exists user");
            throw new ConflictException("already exists user", 409);
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO users VALUES(?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userDTO.getUsername());
            pstmt.setString(2, userDTO.getPassword());
            pstmt.setString(3, userDTO.getNickname());
            int i =  pstmt.executeUpdate();
            return i == 1;
        } catch (SQLException ex) {
            log.warning("sql error" + ex.getMessage());
        } finally {
            closeResources(conn, pstmt);
        }
        return false;
    }

    public UserDTO findByUsername(String username) throws NotFoundException {
        if (!existsByUsername(username)) {
            throw new NotFoundException("cannot found user", 404);
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            resultSet = pstmt.executeQuery();
            resultSet.first();

            username = resultSet.getString(1);
            String password = resultSet.getString(2);
            String nickname = resultSet.getString(3);
            return UserDTO.builder()
                    .username(username)
                    .password(password)
                    .nickname(nickname)
                    .build();
        } catch (SQLException ex) {
            log.warning("sql error " + ex.getMessage());
            throw new NotFoundException("cannot found user", 404);
        } finally {
            closeResources(conn, pstmt);
        }
    }

    private void closeResources(Connection conn, PreparedStatement pstmt) {
        DBConnection.closeResources(conn, pstmt);
    }

}
