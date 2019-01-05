package me.mocha.minisns.model.service;

import me.mocha.minisns.exception.ConflictException;
import me.mocha.minisns.model.dao.PostDAO;
import me.mocha.minisns.model.dto.PostDTO;
import me.mocha.minisns.model.dto.UserDTO;

import java.util.List;
import java.util.logging.Logger;

public class PostService {

    private final PostDAO postDAO = new PostDAO();
    private final Logger log = Logger.getLogger(getClass().getName());

    public boolean createPost(UserDTO user, String title, String content) throws ConflictException {
        return postDAO.save(PostDTO.builder()
                .title(title)
                .content(content)
                .views(0)
                .username(user.getUsername())
                .build(), false);
    }

    public PostDTO getPost(long id) {
        return postDAO.findById(id);
    }

    public List<PostDTO> getAllPosts() {
        return postDAO.findAll();
    }

    public boolean save(PostDTO postDTO) {
        return postDAO.save(postDTO, false);
    }

    public boolean deletePost(long id) {
        return postDAO.deleteById(id);
    }

    public boolean update(PostDTO postDTO) {
        return postDAO.save(postDTO, true);
    }

}
