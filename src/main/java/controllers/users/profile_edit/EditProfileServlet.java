package controllers.users.profile_edit;

import dao.impl.MySQLUserDAO;
import errors.ValidationError;
import model.AppUser;
import security.PasswordHasher;
import services.AppUserService;
import services.impl.AppUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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
        String userLoginFromSession = getUserLoginFromSession(req);
        AppUser user = service.getUserByLogin(userLoginFromSession);

        if (req.getParameter(USER_NAME) != null) {
            String newUserName = req.getParameter(USER_NAME);
            service.changeName(user, newUserName);
        }
        if (req.getParameter(USER_SURNAME) != null) {
            String newUserSurname = req.getParameter(USER_SURNAME);
            service.changeLastName(user, newUserSurname);
        }
        if (req.getParameter(USER_EMAIL) != null) {
            String newUserEmail = req.getParameter(USER_EMAIL);
            Optional<ValidationError> validationError = service.validateEmail(newUserEmail);
            if (validationError.isPresent()){
                req.setAttribute(ERRORS_ATTRIBUTE_NAME, validationError.get());
                req.setAttribute(PROFILE_EDIT, USER_EMAIL);
                req.setAttribute(PROFILE_EDIT_TYPE, TYPE_TEXT);
                req.setAttribute(USER, user);
                req.getRequestDispatcher("/profileEdit.jsp").forward(req, resp);
            } else {
                service.changeEmail(user, newUserEmail);
            }
        }
        if (req.getParameter(USER_PASSWORD) != null) {
            String newUserPassword = req.getParameter(USER_PASSWORD);
            String hash = PasswordHasher.hash(newUserPassword);
            service.changePassword(user, hash);
        }
        if (req.getParameter(USER_LOGIN) != null) {
            String newUserLogin = req.getParameter(USER_LOGIN);

            Optional<ValidationError> validationError = service.validateLogin(newUserLogin);
            if (validationError.isPresent()) {
                req.setAttribute(ERRORS_ATTRIBUTE_NAME, validationError.get());
                req.setAttribute(PROFILE_EDIT, USER_LOGIN);
                req.setAttribute(PROFILE_EDIT_TYPE, TYPE_TEXT);
                req.setAttribute(USER, user);
                req.getRequestDispatcher("/profileEdit.jsp").forward(req, resp);

            } else {
                service.changeLogin(user, newUserLogin);
                req.getSession().setAttribute(USER_LOGIN, newUserLogin);
            }

        }
        doGet(req, resp);
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
        req.getRequestDispatcher("/profileEdit.jsp").forward(req, resp);

    }

}
