package me.mocha.minisns.controller.post;

import me.mocha.minisns.model.dto.PostDTO;
import me.mocha.minisns.model.dto.UserDTO;
import me.mocha.minisns.model.service.PostService;
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

@WebServlet("/posts/delete")
public class DeleteController extends HttpServlet {

    private final PostService postService = new PostService();
    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        log.info("delete " + req.getParameter("id"));
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            UserDTO user = (UserDTO) req.getSession().getAttribute("user");
            PostDTO post = postService.getPost(id);

            if (!post.getUsername().equals(user.getUsername())) {
                sendError(req, res, post, "자신의 게시글이 아닙니다.");
                return;
            }

            if (!postService.deletePost(post.getId())) {
                sendError(req, res, post, "failed delete post");
                return;
            }
        } catch (NumberFormatException e) {
            log.warning(e.getMessage());
        }
        res.sendRedirect(req.getContextPath() + "/");
    }

    private void sendError(HttpServletRequest req, HttpServletResponse res, PostDTO post, String msg) throws IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/post.jsp");
        req.setAttribute("message", msg);

        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String parsedContent = renderer.render(parser.parse(post.getContent()));

        req.setAttribute("id", post.getId());
        req.setAttribute("title", post.getTitle());
        req.setAttribute("content", parsedContent);
        req.setAttribute("username", post.getUsername());
        req.setAttribute("views", post.getViews() + 1);
        dispatcher.forward(req, res);
    }

}
