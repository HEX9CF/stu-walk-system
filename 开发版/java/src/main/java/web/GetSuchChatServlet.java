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

/**
 * Servlet for fetching chat details by ID.
 */
@WebServlet("/GetSuchChatServlet")
public class GetSuchChatServlet extends HttpServlet {

    /**
     * Handles GET requests to fetch chat details by ID.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Create service instance
        ChatListService chatService = new ChatListService();

        // Parse ID from request and fetch the chat details
        int id = Integer.parseInt(req.getParameter("id"));
        Chat selectedChat = chatService.selectBySuchId(id);

        // Convert the result to JSON and write to response
        String jsonString = JSON.toJSONString(selectedChat);
        resp.getWriter().write(jsonString);
    }

    /**
     * Handles POST requests by forwarding them to the doGet method.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);
    }
}
