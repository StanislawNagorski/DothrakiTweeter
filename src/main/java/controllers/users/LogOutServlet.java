package controllers.users;

import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "Logout", value = "/logout")
public class LogOutServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getSession().invalidate();
        Arrays.stream(req.getCookies())
                .filter(cookie ->
                        cookie.getName().equals(ServletUtils.USER_LOGIN) || cookie.getName().equals(ServletUtils.USER_PASSWORD))
                .forEach(cookie -> {
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                });

        req.getRequestDispatcher("/login.jsp").forward(req,resp);

    }


}
