package web;

import service.ChatListService;
import service.SecondChatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for adding a chat record to the second chat list and updating the comment count.
 */
@WebServlet("/addToSecondChatListServlet")
public class AddToSecondChatListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    /**
     * Handles the GET request to add a new second chat record and updates the comment count.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the parameters from the request and convert to appropriate types
        int chatId = Integer.parseInt(req.getParameter("id"));
        int commentId = Integer.parseInt(req.getParameter("comment_id"));
        String userName = req.getParameter("user_name");
        String commentName = req.getParameter("comment_name");
        String date = req.getParameter("date");
        String content = req.getParameter("content");

        // Create service instances
        ChatListService chatListService = new ChatListService();
        SecondChatService secondChatService = new SecondChatService();

        // Add the new second chat record
        secondChatService.add(chatId, commentId, userName, commentName, date, content);

        // Retrieve the updated comment count and update the main chat list
        int updatedCommentCount = secondChatService.selectCount(commentId);
        chatListService.updateCount(updatedCommentCount, commentId);
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
