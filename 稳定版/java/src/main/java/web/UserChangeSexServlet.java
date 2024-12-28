package web;

import service.ChatListService;
import service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles the change of user's sex in the system.
 * It updates the user's sex both in the user table and in the chat history.
 */
@WebServlet("/userChangeSexServlet")
public class UserChangeSexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        // Get the user ID and the new sex value from the request parameters
        int userId = Integer.parseInt(req.getParameter("id"));
        int newSex = Integer.parseInt(req.getParameter("sex"));

        // Create instances of the services that will handle the update
        UsersService usersService = new UsersService();
        ChatListService chatListService = new ChatListService();

        // Update the sex in the users table and the chat table
        usersService.changeSex(userId, newSex);
        chatListService.changeSex(userId, newSex);
        
        // Respond with a success message (this can be expanded as needed)
        resp.getWriter().write("{\"message\": \"User sex updated successfully.\"}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Ensure the request encoding is set to UTF-8 for post requests
        req.setCharacterEncoding("utf-8");
        // Call the doGet method to handle the post request similarly
        this.doGet(req, resp);
    }
}
