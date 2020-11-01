package controllers;

import dao.impl.MySQLUserDAO;
import errors.ValidationError;
import model.AppUser;
import security.PasswordHasher;
import services.impl.AppUserServiceImpl;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Login", urlPatterns = {"/login", ""})
public class LoginServlet extends HttpServlet {

    private AppUserServiceImpl userService;

    @Override
    public void init() throws ServletException {
        userService = new AppUserServiceImpl(new MySQLUserDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        String password = null;

        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals(ServletUtils.USER_LOGIN)) {
                    login = cookie.getValue();
                }
                if (cookie.getName().equals(ServletUtils.USER_PASSWORD)) {
                    password = cookie.getValue();
                }
            }
        }

        if (login != null && password != null) {
            req.setAttribute(ServletUtils.USER_LOGIN, login);
            req.setAttribute(ServletUtils.USER_PASSWORD, password);
            doPost(req, resp);
            return;
        }

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userLogin = req.getParameter(ServletUtils.USER_LOGIN);
        String userPassword = req.getParameter(ServletUtils.USER_PASSWORD);
        String hashedPassword;

        if (userLogin == null && userPassword == null) {
            userLogin = (String) req.getAttribute(ServletUtils.USER_LOGIN);
            hashedPassword = (String) req.getAttribute(ServletUtils.USER_PASSWORD);
        } else {
            hashedPassword = PasswordHasher.hash(userPassword);
        }

        boolean credsInvalid = !userService.isLoginAndPasswordValid(userLogin, hashedPassword);
        if (credsInvalid) {
            ValidationError validationError = new ValidationError(ServletUtils.LOGIN_ERROR_HEADER, ServletUtils.EMAIL_ERROR_MESSAGE);
            List<ValidationError> errors = new ArrayList<>();
            errors.add(validationError);
            req.setAttribute(ServletUtils.ERRORS_ATTRIBUTE_NAME, errors);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        String remember = req.getParameter(ServletUtils.REMEMBER);
        if (isCheckboxChecked(remember)) {
            addCookies(resp, userLogin, hashedPassword);
        }
        AppUser userByLogin = userService.getUserByLogin(userLogin);
        String avatar = userByLogin.getAvatar();

        req.getSession().setAttribute(ServletUtils.USER_AVATAR, avatar);
        req.getSession().setAttribute(ServletUtils.USER_LOGIN, userLogin);

        req.getRequestDispatcher("users").forward(req, resp);
    }

    private boolean isCheckboxChecked(String remember) {
        return ServletUtils.CHECKBOX_CHECKED.equals(remember);
    }

    private void addCookies(HttpServletResponse response, String login, String hashedPassword) {
        Cookie loginCookie = new Cookie(ServletUtils.USER_LOGIN, login);
        loginCookie.setMaxAge(60 * 60);
        Cookie passCookie = new Cookie(ServletUtils.USER_PASSWORD, hashedPassword);
        passCookie.setMaxAge(60 * 60);
        response.addCookie(loginCookie);
        response.addCookie(passCookie);
    }
}
