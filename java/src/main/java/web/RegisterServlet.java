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
 * This servlet handles user registration and checks if a user already exists.
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // serialVersionUID for serialization

    private UsersService usersService;

    /**
     * Default constructor. Initializes the UsersService.
     */
    public RegisterServlet() {
        this.usersService = new UsersService();
    }

    /**
     * Handles HTTP GET requests for user registration.
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

        // Check if the user already exists
        Users user = usersService.searchUser(username);
        String jsonString = JSON.toJSONString(user);

        if (user == null) {
            // Register the user if they do not exist
            usersService.registerUser(username, password);
        } else {
            // Return the user data as a JSON response if the user exists
            resp.getWriter().write(jsonString);
        }
    }

    /**
     * Handles HTTP POST requests for user registration.
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
