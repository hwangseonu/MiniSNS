package me.mocha.blog.controller.post;

import me.mocha.blog.model.dto.PostDTO;
import me.mocha.blog.model.dto.UserDTO;
import me.mocha.blog.model.service.PostService;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            PostDTO post = postService.getPost(id);

            post.addViews(1);
            postService.update(post);

            Parser parser = Parser.builder().build();
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String parsedContent = renderer.render(parser.parse(post.getContent()));

            req.setAttribute("id", post.getId());
            req.setAttribute("title", post.getTitle());
            req.setAttribute("content", parsedContent);
            req.setAttribute("username", post.getUsername());
            req.setAttribute("views", post.getViews() + 1);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/post.jsp");
            dispatcher.forward(req, res);
        } catch (Exception e) {
            log.warning(e.getMessage());
            res.sendRedirect(req.getContextPath() + "/");
        }
    }

    private boolean strEmpty(String msg) {
        return msg == null || msg.isEmpty();
    }

}
