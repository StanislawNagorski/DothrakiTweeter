package controllers.users.profile_edit;

import dao.impl.MySQLUserDAO;
import model.AppUser;
import services.AppUserService;
import services.impl.AppUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static utils.ServletUtils.getUserLoginFromSession;

@WebServlet(name = "DeleteProfile", value = "/deleteProfile")
public class DeleteProfile extends HttpServlet {

    AppUserService service;

    @Override
    public void init() throws ServletException {
        service = new AppUserServiceImpl(new MySQLUserDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLoginFromSession = getUserLoginFromSession(req);
        AppUser user = service.getUserByLogin(userLoginFromSession);
        service.deactivate(user);
        req.getRequestDispatcher("/logout").forward(req,resp);
    }

}
