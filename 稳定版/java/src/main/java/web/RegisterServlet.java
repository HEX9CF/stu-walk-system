package web;

import com.alibaba.fastjson.JSON;
import pojo.User;
import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling user registration.
 * If the username is available, it registers the user.
 * If the username already exists, it returns the user information as a JSON response.
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        // Get the username and password from the request parameters
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Create service instance and check if the user already exists
        UsersService usersService = new UsersService();
        User existingUser = usersService.searchUser(username);

        // Convert the user object to JSON
        String jsonString = JSON.toJSONString(existingUser);

        if (existingUser == null) {
            // If the user doesn't exist, register the user
            usersService.registerUser(username, password);
        } else {
            // If the user exists, return the user information as a JSON response
            resp.getWriter().write(jsonString);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the character encoding for the request
        req.setCharacterEncoding("utf-8");

        // Delegate the handling of POST requests to doGet
        this.doGet(req, resp);
    }
}
