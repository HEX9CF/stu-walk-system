package web;

import com.alibaba.fastjson.JSON;
import pojo.SecondChat;
import service.WxSecondChatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This servlet handles requests to retrieve the list of replies made by a specific user.
 * It fetches the replies from the second-level chat service based on the user ID and returns them in JSON format.
 */
@WebServlet("/WxSelectOwnReplyServlet")
public class WxSelectOwnReplyServlet extends HttpServlet {

    /**
     * Handles GET requests to fetch all replies made by a specific user.
     * The user ID is provided in the request, and the corresponding replies are retrieved from the database.
     * The replies are then returned to the client in JSON format.
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

        // Initialize the service class to interact with second-level chat data
        WxSecondChatService wxSecondChatService = new WxSecondChatService();

        // Retrieve the user ID from the request parameters
        int id = Integer.parseInt(req.getParameter("id"));

        // Fetch the list of replies made by the user from the second-level chat service
        List<SecondChat> secondChats = wxSecondChatService.selectOwnReply(id);

        // Convert the list of replies to JSON format
        String jsonString = JSON.toJSONString(secondChats);

        // Send the JSON response to the client
        resp.getWriter().write(jsonString);
    }

    /**
     * Handles POST requests by forwarding the request to the doGet method.
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
