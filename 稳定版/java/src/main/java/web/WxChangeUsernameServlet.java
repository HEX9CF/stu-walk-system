package web;

import service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet to handle username changes for a user across multiple services.
 * 
 * Request Method:
 * - GET
 * 
 * Request Parameters:
 * - id: The unique user ID whose username needs to be changed.
 * - afterUsername: The new username to be set for the user.
 * 
 * Response Format:
 * - JSON object with a response code and a message indicating the result of the operation.
 */
@WebServlet("/WxChangeUsernameServlet")
public class WxChangeUsernameServlet extends HttpServlet {
    /**
     * Handles the GET request to update the user's username.
     * 
     * @param req HttpServletRequest object containing the client request data.
     * @param resp HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Prepare a map to hold the response data
        Map<String, Object> responseMap = new HashMap<>();

        try {
            // Parse the user ID and the new username from the request parameters
            int user_id = Integer.parseInt(req.getParameter("id"));
            String afterUsername = req.getParameter("afterUsername");

            // Validate the new username
            if (afterUsername == null || afterUsername.trim().isEmpty()) {
                responseMap.put("code", 400); // HTTP Status 400: Bad Request
                responseMap.put("msg", "Username cannot be empty");
                resp.getWriter().write(responseMap.toString());
                return;
            }

            // Initialize the service objects for each layer of the system
            WxUsersService wxUsersService = new WxUsersService();
            WxChatListService wxChatListService = new WxChatListService();
            WxSecondChatService wxSecondChatService = new WxSecondChatService();

            // Perform the username change in each relevant system
            boolean isUserUpdated = wxUsersService.changeUsername(user_id, afterUsername);
            boolean isChatListUpdated = wxChatListService.changeUsername(user_id, afterUsername);
            boolean isSecondChatUpdated = wxSecondChatService.changeUsername(user_id, afterUsername);
            boolean isCommentNameUpdated = wxSecondChatService.changeCommentName(user_id, afterUsername);

            // Check if all updates were successful
            if (isUserUpdated && isChatListUpdated && isSecondChatUpdated && isCommentNameUpdated) {
                responseMap.put("code", 200); // HTTP Status 200: OK
                responseMap.put("msg", "Username updated successfully");
            } else {
                responseMap.put("code", 500); // HTTP Status 500: Internal Server Error
                responseMap.put("msg", "Failed to update username in one or more places");
            }

        } catch (NumberFormatException e) {
            // Handle invalid user ID format
            responseMap.put("code", 400);
            responseMap.put("msg", "Invalid user ID");
        } catch (Exception e) {
            // Handle other errors
            responseMap.put("code", 500);
            responseMap.put("msg", "An error occurred while updating the username");
            e.printStackTrace();  // Log the exception for debugging
        }

        // Send the response as a JSON string
        resp.getWriter().write(responseMap.toString());
    }

    /**
     * Handles the POST request by delegating to the doGet method.
     * 
     * @param req HttpServletRequest object containing the client request data.
     * @param resp HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);
    }
}
