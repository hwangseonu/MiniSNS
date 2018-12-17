package me.mocha.minisns.model.dao;

import me.mocha.minisns.exception.ConflictException;
import me.mocha.minisns.exception.NotFoundException;
import me.mocha.minisns.model.DBConnection;
import me.mocha.minisns.model.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserDAO {

    private final Connection db = DBConnection.getConnection();
    private final Logger log = Logger.getLogger(UserDAO.class.getName());

    public boolean existsByNickname(String nickname) {
        try {
            String sql = "SELECT * FROM users WHERE nickname=?";
            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setString(1, nickname);
            return pstmt.executeQuery().first();
        } catch (SQLException ex) {
            log.warning("sql error" + ex.getMessage());
        }
        return false;
    }

    public boolean existsByUsername(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setString(1, username);
            return pstmt.executeQuery().first();
        } catch (SQLException ex) {
            log.warning("sql error" + ex.getMessage());
        }
        return false;
    }

    public boolean save(UserDTO userDTO) throws ConflictException {
        if (existsByUsername(userDTO.getUsername()) || existsByNickname(userDTO.getNickname())) {
            log.warning("already exists user");
            throw new ConflictException("already exists user", 409);
        }
        try {
            String sql = "INSERT INTO users VALUES(?, ?, ?)";
            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setString(1, userDTO.getUsername());
            pstmt.setString(2, userDTO.getPassword());
            pstmt.setString(3, userDTO.getNickname());
            int i =  pstmt.executeUpdate();
            return i == 1;
        } catch (SQLException ex) {
            log.warning("sql error" + ex.getMessage());
        }
        return false;
    }

    public UserDTO findByUsername(String username) throws NotFoundException {
        if (!existsByUsername(username)) {
            throw new NotFoundException("cannot found user", 404);
        }
        try {
            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement pstmt = db.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.first();
            return UserDTO.builder()
                    .username(resultSet.getString(1))
                    .password(resultSet.getString(2))
                    .nickname(resultSet.getString(3))
                    .build();
        } catch (SQLException ex) {
            log.warning("sql error " + ex.getMessage());
            throw new NotFoundException("cannot found user", 404);
        }
    }


}
