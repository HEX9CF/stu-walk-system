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
import java.util.List;
import java.util.Map;

/**
 * Servlet to handle search for chats based on a search term.
 * 
 * Request Method:
 * - GET
 * 
 * Request Parameters:
 * - index: The search term to filter chat messages.
 * 
 * Response:
 * - A JSON object containing status code, message, and (if successful) the list of matching chats.
 */
@WebServlet("/WxChatSearchServlet")
public class WxChatSearchServlet extends HttpServlet {

    /**
     * Handles GET requests to search for chats based on the provided search term.
     * 
     * @param req The HttpServletRequest object containing client request data.
     * @param resp The HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Prepare response map for feedback
        Map<String, Object> responseMap = new HashMap<>();

        // Retrieve the search term
        String index = req.getParameter("index");

        // Validate input
        if (index == null || index.trim().isEmpty()) {
            responseMap.put("code", 400);  // Bad Request
            responseMap.put("msg", "Search term cannot be empty");
            resp.getWriter().write(JSON.toJSONString(responseMap));
            return;
        }

        // Initialize the service layer to handle the search
        WxChatListService wxChatListService = new WxChatListService();

        try {
            // Search for chats that match the search term
            List<Chat> data = wxChatListService.searchLike(index);

            // If no data found
            if (data == null || data.isEmpty()) {
                responseMap.put("code", 404);  // Not Found
                responseMap.put("msg", "No chats found matching the search term");
            } else {
                responseMap.put("code", 200);  // OK
                responseMap.put("msg", "Search successful");
                responseMap.put("data", data);  // Include search results
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions
            responseMap.put("code", 500);  // Internal Server Error
            responseMap.put("msg", "An error occurred while searching for chats");
            e.printStackTrace();  // Log the exception for debugging
        }

        // Send the response back as a JSON string
        resp.getWriter().write(JSON.toJSONString(responseMap));
    }

    /**
     * Handles POST requests by delegating to the doGet method.
     * 
     * @param req The HttpServletRequest object containing client request data.
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
