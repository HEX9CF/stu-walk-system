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
 * Servlet for handling requests to delete a user's own reply.
 * It deletes a specific reply and updates the comment count accordingly.
 */
@WebServlet("/deleteOwnReplyServlet")
public class DeleteOwnReplyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    /**
     * Handles the GET request to delete a user's own reply.
     * It deletes the specified reply and updates the comment count for the associated comment.
     *
     * @param req the HttpServletRequest containing the unique reply ID and comment ID
     * @param resp the HttpServletResponse to send the result back to the client
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the unique reply ID and comment ID from the request
        int uniqueReplyId = Integer.parseInt(req.getParameter("unique_id"));
        int commentId = Integer.parseInt(req.getParameter("comment_id"));

        // Create service class instances
        SecondChatService secondChatService = new SecondChatService();
        ChatListService chatListService = new ChatListService();

        // Delete the reply and update the comment count
        secondChatService.deleteOwnReply(uniqueReplyId);
        int updatedCommentCount = secondChatService.selectCount(commentId);

        // Update the comment count in the chat list
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
