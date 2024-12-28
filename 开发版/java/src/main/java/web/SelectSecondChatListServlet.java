package web;

import com.alibaba.fastjson.JSON;
import pojo.SecondChat;
import service.SecondChatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for fetching the list of second-level chats by comment ID.
 */
@WebServlet("/SelectSecondChatListServlet")
public class SelectSecondChatListServlet extends HttpServlet {

    /**
     * Handles GET requests to fetch the second-level chat list for a specific comment.
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
        SecondChatService chatService = new SecondChatService();

        // Parse comment ID and fetch chat list
        int commentId = Integer.parseInt(req.getParameter("comment_id"));
        List<SecondChat> secondChats = chatService.selectChatList(commentId);

        // Convert result to JSON and write to response
        String jsonString = JSON.toJSONString(secondChats);
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
