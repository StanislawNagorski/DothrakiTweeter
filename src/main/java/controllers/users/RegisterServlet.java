package controllers.users;

import dao.impl.MySQLUserDAO;
import errors.ValidationError;
import model.AppUser;
import org.apache.commons.codec.digest.DigestUtils;
import security.PasswordHasher;
import services.impl.AppUserServiceImpl;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Register", value = "/register")
public class RegisterServlet extends HttpServlet {

    private AppUserServiceImpl userService;

    @Override
    public void init() throws ServletException {
        userService = new AppUserServiceImpl(new MySQLUserDAO());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String userLogin = req.getParameter(ServletUtils.USER_LOGIN);
        String userEmail = req.getParameter(ServletUtils.USER_EMAIL);

        List<ValidationError> errors = userService.validateUser(userLogin, userEmail);

        if (errors.isEmpty()) {

            String userPassword = req.getParameter(ServletUtils.USER_PASSWORD);
            String hashedPassword = PasswordHasher.hash(userPassword);

            AppUser appUser = AppUser.UserBuilder
                    .getBuilder()
                    .login(userLogin)
                    .name(req.getParameter(ServletUtils.USER_NAME))
                    .lastName(req.getParameter(ServletUtils.USER_SURNAME))
                    .password(hashedPassword)
                    .email(userEmail)
                    .build();

            userService.register(appUser);
            req.getRequestDispatcher("/login.jsp").forward(req,resp);

        } else {
            req.setAttribute(ServletUtils.ERRORS_ATTRIBUTE_NAME, errors);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
