package web;

import com.alibaba.fastjson.JSON;
import pojo.Users;
import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to handle user login requests.
 * This servlet validates the user's credentials and returns user data if the credentials are correct.
 */
@WebServlet("/usersServlet")
public class UsersServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // serialVersionUID for serialization

    private UsersService usersService;

    /**
     * Default constructor. Initializes the UsersService.
     */
    public UsersServlet() {
        this.usersService = new UsersService();
    }

    /**
     * Handles HTTP GET requests to validate user credentials and return user information.
     *
     * @param req  the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if an error occurs during the processing of the request
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Search for user by username
        Users user = usersService.searchUser(username);
        String jsonString = JSON.toJSONString(user);

        // If user exists and password matches, return the user data as JSON
        if (user != null && password.equals(user.getPassword())) {
            resp.getWriter().write(jsonString);
        }
    }

    /**
     * Handles HTTP POST requests by delegating to the doGet method.
     *
     * @param req  the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if an error occurs during the processing of the request
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        this.doGet(req, resp);
    }
}
