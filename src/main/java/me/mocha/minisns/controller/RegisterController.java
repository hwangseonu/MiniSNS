package me.mocha.minisns.controller;

import me.mocha.minisns.exception.ApplicationException;
import me.mocha.minisns.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/register"})
public class RegisterController extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");
        try {
            if (userService.createUser(username, password, nickname)) {
                res.addCookie(new Cookie("username", username));
                res.addCookie(new Cookie("password", password));
                res.sendRedirect(req.getContextPath() + "/");
            }
            res.sendError(401);
        } catch (ApplicationException ex) {
            res.sendError(ex.getStatus());
        }
    }
}
