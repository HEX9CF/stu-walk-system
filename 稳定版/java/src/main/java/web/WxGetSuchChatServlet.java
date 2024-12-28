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
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet for retrieving a specific chat by its ID.
 * 
 * Request Method: GET
 * Request Parameters:
 * - id: The ID of the chat to retrieve.
 * 
 * Response:
 * - A JSON response containing status code, message, and the retrieved chat data (if found).
 */
@WebServlet("/WxGetSuchChatServlet")
public class WxGetSuchChatServlet extends HttpServlet {

    /**
     * Handles GET requests to retrieve a chat by its ID.
     * 
     * @param req The HttpServletRequest object containing the client's request data.
     * @param resp The HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Prepare the response map for feedback
        Map<String, Object> responseMap = new HashMap<>();
        WxChatListService wxChatListService = new WxChatListService();

        // Retrieve and validate the chat ID
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            responseMap.put("code", 400);
            responseMap.put("msg", "Chat ID is required");
            resp.getWriter().write(JSON.toJSONString(responseMap));
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            responseMap.put("code", 400);
            responseMap.put("msg", "Invalid chat ID format");
            resp.getWriter().write(JSON.toJSONString(responseMap));
            return;
        }

        try {
            // Fetch the chat using the provided ID
            Chat chat = wxChatListService.selectBySuchId(id);

            // Check if the chat was found
            if (chat == null) {
                responseMap.put("code", 404);
                responseMap.put("msg", "Chat not found for the given ID");
            } else {
                responseMap.put("code", 200);
                responseMap.put("msg", "Chat retrieved successfully");
                responseMap.put("data", chat);
            }
        } catch (Exception e) {
            // Handle any unexpected errors during the retrieval process
            responseMap.put("code", 500);
            responseMap.put("msg", "An error occurred while retrieving the chat");
            e.printStackTrace(); // Log the exception for debugging
        }

        // Send the response as a JSON string
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
