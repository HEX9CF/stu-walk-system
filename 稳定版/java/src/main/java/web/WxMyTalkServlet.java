package web;

import com.alibaba.fastjson.JSON;
import pojo.Chat;
import service.WxChatListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This servlet handles the retrieval of chat messages associated with a specific user.
 * It retrieves the chat messages from the database for a user based on the provided user ID.
 * <p>
 * This servlet supports GET requests where the user ID is passed as a query parameter ('id').
 */
@WebServlet("/WxMyTalkServlet")
public class WxMyTalkServlet extends HttpServlet {

    /**
     * Handles GET requests to retrieve the chat messages for a specific user.
     * The user ID is provided as a query parameter ('id'). The servlet fetches the corresponding 
     * chat messages from the service and returns them in JSON format.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object used to send the response back to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to JSON
        resp.setContentType("text/json; charset=utf-8");

        // Extract user ID from the request
        int id = Integer.parseInt(req.getParameter("id"));

        // Initialize the service class to interact with the database
        WxChatListService wxChatListService = new WxChatListService();

        // Retrieve the list of chats for the specified user
        List<Chat> chats = wxChatListService.selectByUserId(id);

        // Convert the list of chats to JSON format
        String jsonString = JSON.toJSONString(chats);

        // Send the JSON response to the client
        resp.getWriter().write(jsonString);
    }

    /**
     * Handles POST requests by calling the doGet method to process the request.
     * This method is included to ensure that the servlet can handle both GET and POST requests.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object used to send the response back to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set request encoding to UTF-8 for proper handling of non-ASCII characters
        req.setCharacterEncoding("utf-8");

        // Call the doGet method to handle the POST request
        this.doGet(req, resp);
    }
}
