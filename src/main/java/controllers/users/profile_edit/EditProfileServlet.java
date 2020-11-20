package controllers.users.profile_edit;

import dao.impl.MySQLTwitterDAO;
import dao.impl.MySQLUserDAO;
import errors.ValidationError;
import model.AppUser;
import model.Tweet;
import security.PasswordHasher;
import services.AppUserService;
import services.TweetService;
import services.impl.AppUserServiceImpl;
import services.impl.TweetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static utils.ServletUtils.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5)
@WebServlet(name = "EditProfile", value = "/profileEdit")
public class EditProfileServlet extends HttpServlet {

    AppUserService service;
    TweetService tweetService;

    @Override
    public void init() throws ServletException {
        service = new AppUserServiceImpl(new MySQLUserDAO());
        tweetService = new TweetServiceImpl(new MySQLTwitterDAO());
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
            String hash = PasswordHasher.hash(userLoginFromSession, newUserPassword);
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

        String contentType = req.getContentType();
        if (!contentType.equals(NON_AVATAR_FORM_CODE)){
            changeUserAvatar(req, user);
        }

        doGet(req, resp);
    }

    private void changeUserAvatar(HttpServletRequest req, AppUser user) throws IOException, ServletException {
        String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
        Collection<Part> parts = req.getParts();
        for (Part part : parts) {
            String header = part.getHeader("content-disposition");

            if (header.contains(USER_AVATAR)) {
                String fileName = writeFileToDir(uploadPath, part);
                service.changeAvatar(user,fileName);
                req.getSession().setAttribute(USER_AVATAR,fileName);
            }

        }
    }

    private String writeFileToDir(String uploadPath, Part part) throws IOException {
        String uploadedFileName = getFileName(part);
        String fileName = uploadPath + File.separator + uploadedFileName;
        part.write(fileName);
        return UPLOAD_DIRECTORY + File.separator + uploadedFileName;
    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return "";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLoginFromSession = getUserLoginFromSession(req);
        AppUser user = service.getUserByLogin(userLoginFromSession);

        String fieldToChange = req.getParameter(PROFILE_EDIT);
        String typeOfFieldToChange = req.getParameter(PROFILE_EDIT_TYPE);

        List<Tweet> userTweets = tweetService.getUserTweets(user);
        req.setAttribute(USER_TWEETS, userTweets);

        req.setAttribute(PROFILE_EDIT_TYPE, typeOfFieldToChange);
        req.setAttribute(PROFILE_EDIT, fieldToChange);
        req.setAttribute(USER, user);
        req.getRequestDispatcher("/profileEdit.jsp").forward(req, resp);

    }

}
