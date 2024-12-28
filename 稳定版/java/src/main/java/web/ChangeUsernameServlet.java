package web;

import com.alibaba.fastjson.JSON;
import pojo.User;
import service.ChatListService;
import service.SecondChatService;
import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for changing the username of a user.
 * It checks whether the new username already exists and updates accordingly.
 */
@WebServlet("/changeUsernameServlet")
public class ChangeUsernameServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    /**
     * Handles the GET request to change a user's username.
     * It checks whether the new username already exists and updates the user's information accordingly.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");

        // Create service class instances
        UsersService usersService = new UsersService();
        ChatListService chatListService = new ChatListService();
        SecondChatService secondChatService = new SecondChatService();

        // Retrieve the current and new usernames from the request
        String currentUsername = req.getParameter("beforeUsername");
        String newUsername = req.getParameter("afterUsername");

        // Check if the new username already exists
        User existingUser = usersService.searchUser(newUsername);
        String jsonString = JSON.toJSONString(existingUser);

        if (existingUser == null) {
            // If the new username does not exist, update the username across the system
            usersService.changeUsername(currentUsername, newUsername);
            chatListService.changeUsername(currentUsername, newUsername);
            secondChatService.changeUsername(currentUsername, newUsername);
            secondChatService.changeCommentName(currentUsername, newUsername);
        } else {
            // If the new username exists, return the existing user information
            resp.getWriter().write(jsonString);
        }
    }

    /**
     * Handles the POST request by forwarding it to the doGet method.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);
    }
}
