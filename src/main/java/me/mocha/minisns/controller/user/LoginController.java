package me.mocha.minisns.controller.user;

import me.mocha.minisns.exception.ApplicationException;
import me.mocha.minisns.model.dto.UserDTO;
import me.mocha.minisns.model.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final UserService userService = new UserService();
    private final Logger log = Logger.getLogger(getClass().getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            UserDTO user = userService.getUser(username);

            if (strEmpty(username) || strEmpty(password)) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
                req.setAttribute("message", "빈칸이 있습니다.");
                dispatcher.forward(req, res);
                return;
            }

            if (user.verify(password)) {
                req.getSession().setAttribute("user", user);
                res.sendRedirect(req.getContextPath() + "/");
                return;
            }
        } catch (ApplicationException ex) {
            log.warning("login error : " + ex.getMessage());
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/login.jsp");
        req.setAttribute("message", "login failed!");
        dispatcher.forward(req, res);
    }

    private boolean strEmpty(String msg) {
        return msg == null || msg.isEmpty();
    }
}
