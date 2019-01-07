package me.mocha.blog.controller.post;

import me.mocha.blog.model.dto.PostDTO;
import me.mocha.blog.model.service.PostService;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/list")
public class ListController extends HttpServlet {

    private final PostService postService = new PostService();
    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<PostDTO> posts = postService.getAllPosts();
        posts.forEach(p -> {
            String content = p.getContent();
            Parser parser = Parser.builder().build();
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            content = renderer.render(parser.parse(content));
            Document document = Jsoup.parse(content);
            p.setContent(document.body().text());
        });
        req.setAttribute("posts", posts);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/list.jsp");
        dispatcher.forward(req, res);
    }
}
