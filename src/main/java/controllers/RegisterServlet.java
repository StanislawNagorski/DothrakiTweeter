package controllers;

import dao.impl.MySQLUserDAO;
import errors.ValidationError;
import model.AppUser;
import org.apache.commons.codec.digest.DigestUtils;
import services.impl.AppUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Register", value = "/register")
public class RegisterServlet extends HttpServlet {

    public static final String LOGIN = "login";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ERRORS = "errors";

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userPassword = req.getParameter(PASSWORD);
        String hashedPassword = DigestUtils.md5Hex(userPassword);

        AppUser appUser = AppUser.UserBuilder
                .getBuilder()
                .login(req.getParameter(LOGIN))
                .name(req.getParameter(NAME))
                .lastName(req.getParameter(SURNAME))
                .password(hashedPassword)
                .email(req.getParameter(EMAIL))
                .build();

        AppUserServiceImpl userService = new AppUserServiceImpl(new MySQLUserDAO());
        List<ValidationError> errors = userService.validateUser(appUser);

        if (errors.isEmpty()){
            userService.register(appUser);
        } else {
           req.setAttribute(ERRORS,errors);
           req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
