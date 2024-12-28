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
 * Servlet that retrieves all chat records for a specific user based on their user ID.
 * It returns the chat records as a JSON response.
 */
@WebServlet("/myTalkServlet")
public class MyTalkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        // Get the user ID from the request parameter
        int userId = Integer.parseInt(req.getParameter("id"));

        // Create the service instance and retrieve the chat records for the user
        ChatListService chatListService = new ChatListService();
        List<Chat> chatRecords = chatListService.selectByUserId(userId);

        // Convert the chat records list to JSON and send the response
        String jsonString = JSON.toJSONString(chatRecords);
        resp.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the character encoding for the request
        req.setCharacterEncoding("utf-8");

        // Delegate the handling of POST requests to doGet
        this.doGet(req, resp);
    }
}
