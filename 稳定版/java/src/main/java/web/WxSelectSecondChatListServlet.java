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
 * This servlet handles requests to fetch the list of second-level chats (replies) 
 * associated with a specific comment.
 * It retrieves the list of replies based on the comment ID provided in the request 
 * and returns them in JSON format.
 */
@WebServlet("/WxSelectSecondChatListServlet")
public class WxSelectSecondChatListServlet extends HttpServlet {

    /**
     * Handles GET requests to retrieve the list of second-level replies 
     * for a given comment. The comment ID is passed in the request 
     * and is used to fetch the related replies from the service.
     * The replies are returned to the client as a JSON response.
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

        // Retrieve the comment ID from the request parameters
        int commentId = Integer.parseInt(req.getParameter("comment_id"));

        // Fetch the list of second-level replies related to the comment from the service
        List<SecondChat> secondChats = wxSecondChatService.selectChatList(commentId);

        // Convert the list of second-level chats to JSON format
        String jsonString = JSON.toJSONString(secondChats);

        // Send the JSON response to the client
        resp.getWriter().write(jsonString);
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
