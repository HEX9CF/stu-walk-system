package web;

import com.alibaba.fastjson.JSON;
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
 * Servlet for handling the deletion of a user's message from both the main chat list
 * and the secondary chat list.
 * 
 * The message will be removed from both the lists using the provided message ID.
 * 
 * Request Method: GET
 * Request Parameters:
 * - id: The message ID to be deleted.
 * 
 * Response:
 * - A JSON response containing status code, message, and any relevant data.
 */
@WebServlet("/WxDeleteMyTalkServlet")
public class WxDeleteMyTalkServlet extends HttpServlet {

    /**
     * Handles GET requests to delete a user's message.
     * 
     * @param req The HttpServletRequest object containing the client's request data.
     * @param resp The HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Prepare response map for feedback
        Map<String, Object> responseMap = new HashMap<>();

        // Retrieve and validate the message ID parameter
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            responseMap.put("code", 400);
            responseMap.put("msg", "Message ID is required");
            resp.getWriter().write(JSON.toJSONString(responseMap));
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            responseMap.put("code", 400);
            responseMap.put("msg", "Invalid message ID format");
            resp.getWriter().write(JSON.toJSONString(responseMap));
            return;
        }

        // Initialize service layers
        WxChatListService wxChatListService = new WxChatListService();
        WxSecondChatService wxSecondChatService = new WxSecondChatService();

        try {
            // Attempt to delete the message from the main chat list
            boolean mainDeleteSuccess = wxChatListService.deleteMyTalk(id);
            // Attempt to delete the message from the secondary chat list
            boolean secondaryDeleteSuccess = wxSecondChatService.deleteSecondChat(id);

            // Check if both deletions were successful
            if (mainDeleteSuccess && secondaryDeleteSuccess) {
                responseMap.put("code", 200);
                responseMap.put("msg", "Message successfully deleted");
            } else {
                responseMap.put("code", 500);
                responseMap.put("msg", "Failed to delete message from one or both lists");
            }
        } catch (Exception e) {
            // Handle any unexpected errors during deletion
            responseMap.put("code", 500);
            responseMap.put("msg", "An error occurred while deleting the message");
            e.printStackTrace(); // For debugging purposes, you should log this in production
        }

        // Send the response as JSON
        resp.getWriter().write(JSON.toJSONString(responseMap));
    }

    /**
     * Handles POST requests by delegating to the doGet method.
     * 
     * @param req The HttpServletRequest object containing the client's request data.
     * @param resp The HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);  // Delegate POST to GET
    }
}
