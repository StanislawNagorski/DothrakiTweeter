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

import static utils.ServletUtils.*;

@WebServlet(name = "EditProfile", value = "/profileEdit")
public class EditProfileServlet extends HttpServlet {

    AppUserService service;

    @Override
    public void init() throws ServletException {
        service = new AppUserServiceImpl(new MySQLUserDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLoginFromSession = getUserLoginFromSession(req);
        AppUser user = service.getUserByLogin(userLoginFromSession);

        String whatChange = req.getParameter(PROFILE_EDIT);
        String typeOfFieldToChange = req.getParameter(PROFILE_EDIT_TYPE);

        req.setAttribute(PROFILE_EDIT_TYPE, typeOfFieldToChange);
        req.setAttribute(PROFILE_EDIT, whatChange);
        req.setAttribute(USER, user);
        req.getRequestDispatcher("/profileEdit.jsp").forward(req,resp);

    }

}
