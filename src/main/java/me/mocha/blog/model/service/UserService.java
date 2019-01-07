package me.mocha.blog.model.service;

import me.mocha.blog.exception.ConflictException;
import me.mocha.blog.model.dao.UserDAO;
import me.mocha.blog.model.dto.UserDTO;

import java.util.logging.Logger;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final Logger log = Logger.getLogger(getClass().getName());

    public boolean createUser(String username, String password, String nickname) throws ConflictException {
        return userDAO.save(UserDTO.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build());
    }

    public UserDTO getUser(String username) {
        return userDAO.findByUsername(username);
    }

}
