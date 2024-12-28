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
 * Servlet for handling chat record search requests.
 * It performs a fuzzy search based on the query parameter and returns matching chat records in JSON format.
 */
@WebServlet("/chatSearchServlet")
public class ChatSearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    /**
     * Handles the GET request for searching chat records.
     * It retrieves the search keyword from the request, performs a fuzzy search,
     * and returns the results in JSON format.
     *
     * @param req the HttpServletRequest containing the search query
     * @param resp the HttpServletResponse to write the JSON response
     * @throws ServletException if the request could not be processed
     * @throws IOException if an input/output error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the search query parameter
        String searchQuery = req.getParameter("index");

        // Create service class instance and perform the fuzzy search
        ChatListService chatListService = new ChatListService();
        List<Chat> searchResults = chatListService.searchLike(searchQuery);

        // Convert the search results to JSON format
        String jsonResponse = JSON.toJSONString(searchResults);

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
