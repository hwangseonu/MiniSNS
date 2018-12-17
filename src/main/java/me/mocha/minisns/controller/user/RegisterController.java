package me.mocha.minisns.controller.user;

import me.mocha.minisns.exception.ApplicationException;
import me.mocha.minisns.model.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet({"/register"})
public class RegisterController extends HttpServlet {

    private final UserService userService = new UserService();
    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");

        if (strEmpty(username) || strEmpty(password) || strEmpty(nickname)) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/register.jsp");
            req.setAttribute("message", "빈칸이 있습니다.");
            dispatcher.forward(req, res);
            return;
        }

        try {
            if (userService.createUser(username, password, nickname)) {
                res.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }
        } catch (ApplicationException ex) {
            log.warning("register error : " + ex.getMessage());
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
        req.setAttribute("message", "register failed!");
        dispatcher.forward(req, res);
    }

    private boolean strEmpty(String msg) {
        return msg == null || msg.isEmpty();
    }

}
