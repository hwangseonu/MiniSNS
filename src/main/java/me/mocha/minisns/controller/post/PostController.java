package me.mocha.minisns.controller.post;

import me.mocha.minisns.model.dto.UserDTO;
import me.mocha.minisns.model.service.PostService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/posts")
public class PostController extends HttpServlet {

    private final PostService postService = new PostService();
    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");

        if (user == null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
            req.setAttribute("message", "로그인을 먼저 해주세요.");
            dispatcher.forward(req, res);
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        if (strEmpty(title) || strEmpty(content)) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/editor.jsp");
            req.setAttribute("message",  "빈칸이 있습니다.");
            dispatcher.forward(req, res);
            return;
        }

        try {
            if (postService.createPost(user, title, content)) {
                res.sendRedirect(req.getContextPath() + "/");
            } else {
                throw new RuntimeException("post failed!");
            }
        } catch (Exception e) {
            log.warning("post error - " + e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/editor.jsp");
            req.setAttribute("message",  e.getMessage());
            dispatcher.forward(req, res);
        }
    }

    private boolean strEmpty(String msg) {
        return msg == null || msg.isEmpty();
    }

}
