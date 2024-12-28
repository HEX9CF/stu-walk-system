package web;

import service.WxChatListService;
import service.WxSecondChatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class WxDeleteOwnReplyServlet
 * 
 * This servlet handles the deletion of a user's own reply in the second-level chat 
 * and updates the reply count of the corresponding comment.
 * It receives two parameters: `unique_id` (the user's reply ID) and `comment_id` (the ID of the comment being replied to).
 * 
 * Request Method:
 * - GET
 * 
 * Request Parameters:
 * - unique_id: The unique ID of the user's reply.
 * - comment_id: The ID of the comment that was replied to.
 * 
 * Response Format:
 * - JSON object with response code and message.
 */
@WebServlet("/WxDeleteOwnReplyServlet")
public class WxDeleteOwnReplyServlet extends HttpServlet {

    /**
     * Handles GET requests, deletes the user's reply, and updates the comment's reply count.
     * 
     * @param req HttpServletRequest request object.
     * @param resp HttpServletResponse response object.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Prepare the response data map
        Map<String, Object> responseMap = new HashMap<>();

        // Retrieve the request parameters
        String uniqueIdParam = req.getParameter("unique_id");
        String commentIdParam = req.getParameter("comment_id");

        // Validate that the unique_id and comment_id parameters are not empty
        if (uniqueIdParam == null || commentIdParam == null || uniqueIdParam.isEmpty() || commentIdParam.isEmpty()) {
            responseMap.put("code", 400);
            responseMap.put("msg", "Missing required parameters: unique_id and comment_id");
            resp.getWriter().write(JSON.toJSONString(responseMap));
            return;
        }

        // Attempt to parse the parameters to integers
        int uniqueId;
        int commentId;
        try {
            uniqueId = Integer.parseInt(uniqueIdParam);
            commentId = Integer.parseInt(commentIdParam);
        } catch (NumberFormatException e) {
            responseMap.put("code", 400);
            responseMap.put("msg", "Invalid parameter format: unique_id and comment_id must be integers");
            resp.getWriter().write(JSON.toJSONString(responseMap));
            return;
        }

        // Initialize the service objects
        WxChatListService wxChatListService = new WxChatListService();
        WxSecondChatService wxSecondChatService = new WxSecondChatService();

        try {
            // Delete the user's reply
            wxSecondChatService.deleteOwnReply(uniqueId);

            // Update the reply count for the comment
            int updatedCount = wxSecondChatService.selectCount(commentId);
            wxChatListService.updateCount(updatedCount, commentId);

            // Return a success response
            responseMap.put("code", 200);
            responseMap.put("msg", "Reply successfully deleted and comment reply count updated");

        } catch (Exception e) {
            // Catch any exceptions and return a failure response
            responseMap.put("code", 500);
            responseMap.put("msg", "Error occurred while deleting the reply: " + e.getMessage());
            e.printStackTrace();
        }

        // Send the response
        resp.getWriter().write(JSON.toJSONString(responseMap));
    }

    /**
     * Handles POST requests by delegating to the doGet method.
     * 
     * @param req HttpServletRequest request object.
     * @param resp HttpServletResponse response object.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);
    }
}
