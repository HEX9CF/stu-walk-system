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
 * Servlet to fetch a paginated list of chats.
 * 
 * Request Method:
 * - GET
 * 
 * Request Parameters:
 * - pageNum: The page number to fetch chats for. The result will be paginated by 10 chats per page.
 * 
 * Response:
 * - A JSON object with a response code, message, and (if successful) the list of chats.
 */
@WebServlet("/WxChatListServlet")
public class WxChatListServlet extends HttpServlet {

    /**
     * Handles the GET request to fetch a paginated list of chats.
     * 
     * @param req The HttpServletRequest object containing client request data.
     * @param resp The HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Prepare response map to return status and messages
        Map<String, Object> responseMap = new HashMap<>();

        try {
            // Parse the pageNum parameter from the request
            String pageNumStr = req.getParameter("pageNum");
            if (pageNumStr == null || pageNumStr.trim().isEmpty()) {
                responseMap.put("code", 400); // Bad Request
                responseMap.put("msg", "Missing or invalid page number");
                resp.getWriter().write(JSON.toJSONString(responseMap));
                return;
            }

            int pageNum = Integer.parseInt(pageNumStr);
            if (pageNum < 0) {
                responseMap.put("code", 400); // Bad Request
                responseMap.put("msg", "Page number cannot be negative");
                resp.getWriter().write(JSON.toJSONString(responseMap));
                return;
            }

            // Multiply pageNum by 10 for pagination (assuming 10 chats per page)
            pageNum *= 10;

            // Fetch chat list from the service
            WxChatListService wxChatListService = new WxChatListService();
            List<Chat> chats = wxChatListService.selectLimit(pageNum);

            // Check if the chats were retrieved successfully
            if (chats == null || chats.isEmpty()) {
                responseMap.put("code", 404); // Not Found
                responseMap.put("msg", "No chats found for the given page");
            } else {
                responseMap.put("code", 200); // OK
                responseMap.put("msg", "Chats retrieved successfully");
                responseMap.put("data", chats); // Include the chats data in the response
            }
        } catch (NumberFormatException e) {
            responseMap.put("code", 400); // Bad Request
            responseMap.put("msg", "Invalid page number format");
        } catch (Exception e) {
            responseMap.put("code", 500); // Internal Server Error
            responseMap.put("msg", "An error occurred while retrieving the chat list");
            e.printStackTrace(); // Log the exception for debugging
        }

        // Return the response as a JSON string
        resp.getWriter().write(JSON.toJSONString(responseMap));
    }

    /**
     * Handles the POST request by delegating it to the doGet method.
     * 
     * @param req The HttpServletRequest object containing client request data.
     * @param resp The HttpServletResponse object to send the response back to the client.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);
    }
}
