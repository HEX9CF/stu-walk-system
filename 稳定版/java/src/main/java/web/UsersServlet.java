package web;

import com.alibaba.fastjson.JSON;
import pojo.User;
import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * This servlet handles user login requests. It checks if the provided username and password match
 * an existing user, and responds with the user details if authentication is successful.
 */
@WebServlet("/usersServlet")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to JSON with UTF-8 encoding
        resp.setContentType("application/json; charset=utf-8");

        // Retrieve username and password from the request parameters
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // If either username or password is null or empty, return an error response
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Username and password must be provided.\"}");
            return;
        }

        // Create an instance of UsersService to search for the user
        UsersService usersService = new UsersService();
        User user = usersService.searchUser(username);

        // If user is found and the password matches, return the user information
        if (user != null && password.equals(user.getPassword())) {
            String jsonString = JSON.toJSONString(user);
            resp.getWriter().write(jsonString);
        } else {
            // If no match is found, return an error message
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\": \"Invalid username or password.\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Ensure the request encoding is set to UTF-8 for POST requests
        req.setCharacterEncoding("utf-8");
        // Call the doGet method to handle the post request similarly
        this.doGet(req, resp);
    }
}
