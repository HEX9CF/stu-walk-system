package web;

import com.alibaba.fastjson.JSON;
import pojo.Chat;
import service.ChatListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for fetching a paginated list of chat records.
 * It retrieves the chat records for the requested page number and returns them in JSON format.
 */
@WebServlet("/chatListServlet")
public class ChatListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    /**
     * Handles the GET request to fetch a paginated list of chat records.
     * It calculates the correct offset based on the requested page number and returns the chat records in JSON format.
     *
     * @param req the HttpServletRequest
     * @param resp the HttpServletResponse
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the page number and calculate the offset
        int pageNumber = Integer.parseInt(req.getParameter("pageNum"));
        pageNumber *= 10; // 10 records per page

        // Create service class instance
        ChatListService chatListService = new ChatListService();

        // Retrieve the chat records for the specified page number
        List<Chat> chatRecords = chatListService.selectLimit(pageNumber);

        // Convert the chat records list to JSON format
        String jsonResponse = JSON.toJSONString(chatRecords);

        // Write the JSON response back to the client
        resp.getWriter().write(jsonResponse);
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
