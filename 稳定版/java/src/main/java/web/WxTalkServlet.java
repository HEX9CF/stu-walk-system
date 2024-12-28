package web;

import service.WxChatListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles requests to add a new chat message to the chat list.
 * It processes the data (such as user ID, user name, content, etc.) received from the client,
 * and uses the `WxChatListService` to save the chat to the database.
 */
@WebServlet("/WxTalkServlet")
public class WxTalkServlet extends HttpServlet {

    /**
     * Handles GET requests for adding a new chat message.
     * The required parameters such as user ID, user name, content, etc., are retrieved from the request.
     * The chat message is then added to the chat list via the WxChatListService.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object used to send the response back to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to JSON
        resp.setContentType("application/json; charset=utf-8");

        // Retrieve parameters from the request
        int userId = Integer.parseInt(req.getParameter("user_id"));
        String userName = req.getParameter("user_name");
        String content = req.getParameter("content");
        String date = req.getParameter("date");
        int sex = Integer.parseInt(req.getParameter("sex"));
        String headPic = req.getParameter("head_pic");

        // Create an instance of the service class to interact with the chat list
        WxChatListService wxChatListService = new WxChatListService();

        // Add the chat to the database using the service method
        wxChatListService.addChat(userId, userName, content, date, sex, headPic);
    }

    /**
     * Handles POST requests by forwarding them to the doGet method.
     * This ensures that both GET and POST requests are processed in the same way.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object used to send the response back to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the request character encoding to UTF-8 for proper handling of non-ASCII characters
        req.setCharacterEncoding("utf-8");

        // Forward the POST request to the doGet method for processing
        this.doGet(req, resp);
    }
}
