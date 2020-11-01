package controllers;

import dao.impl.MySQLUserDAO;
import model.AppUser;
import services.AppUserService;
import services.impl.AppUserServiceImpl;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static utils.ServletUtils.*;

@WebServlet(name = "EditProfile", value = "/profileEdit")
public class EditProfileServlet extends HttpServlet {

    AppUserService service;

    @Override
    public void init() throws ServletException {
        service = new AppUserServiceImpl(new MySQLUserDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLoginFromSession = getUserLoginFromSession(req);
        AppUser user = service.getUserByLogin(userLoginFromSession);

        req.setAttribute(USER, user);
        req.getRequestDispatcher("/profileEdit.jsp").forward(req,resp);

    }

}
