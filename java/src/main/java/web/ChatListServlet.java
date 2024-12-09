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
 * Servlet for handling chat list retrieval.
 */
@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
    
    /**
     * Handles GET requests to retrieve a paginated chat list.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Retrieve and process pagination parameter
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        pageNum *= 10;

        // Fetch chat list data
        ChatListService chatListService = new ChatListService();
        List<Chat> chats = chatListService.selectLimit(pageNum);

        // Convert to JSON and write response
        String jsonString = JSON.toJSONString(chats);
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
