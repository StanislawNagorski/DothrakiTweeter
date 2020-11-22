package controllers.users.googleAuth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import dao.impl.MySQLUserDAO;
import errors.ValidationError;
import model.AppUser;
import security.LoginBuilderForGoogle;
import security.PasswordHasher;
import services.AppUserService;
import services.impl.AppUserServiceImpl;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static utils.ServletUtils.*;

@WebServlet(name = "GoogleLogin", urlPatterns = "/googleLogin")
public class GoogleLogin extends HttpServlet {
    AppUserService service;

    @Override
    public void init() throws ServletException {
        service = new AppUserServiceImpl(new MySQLUserDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String idToken = req.getParameter("id_token");
        GoogleIdToken.Payload payLoad = null;
        try {
            payLoad = IdTokenVerifierAndParser.getPayload(idToken);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fullName = (String) payLoad.get("name");
        String name = (String) payLoad.get("given_name");
        String familyName = (String) payLoad.get("family_name");
        String email = payLoad.getEmail();
        String image = (String) payLoad.get("picture");

        String login = LoginBuilderForGoogle.build(name, email);

        Optional<ValidationError> validationError = service.validateLogin(login);

        boolean userIsNotInDataBase = validationError.isEmpty();
        if (userIsNotInDataBase) {
            AppUser userFromGoogle = AppUser.UserBuilder.getBuilder()
                    .login(login)
                    .name(name)
                    .lastName(familyName)
                    .email(email)
                    .avatar(image)
                    .build();
            service.register(userFromGoogle);
        }

//            List<ValidationError> errors = service.validateUser(login, email);
//            if (!errors.isEmpty()){
//                req.setAttribute(ServletUtils.ERRORS_ATTRIBUTE_NAME, errors);
//                req.getRequestDispatcher("/login.jsp").forward(req, resp);
//            }

        req.getSession().setAttribute(USER_LOGIN, login);
        req.getSession().setAttribute(USER_AVATAR, image);
        req.getRequestDispatcher("users").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


}
