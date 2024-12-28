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
 * Servlet that handles requests to retrieve a specific chat by its unique ID.
 * It queries the chat based on the provided ID and returns the chat information as a JSON response.
 */
@WebServlet("/getSuchChatServlet")
public class GetSuchChatServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        // Get the chat ID from the request parameter
        int chatId = Integer.parseInt(req.getParameter("id"));

        // Create the service instance and fetch the chat by the provided ID
        ChatListService chatListService = new ChatListService();
        Chat chat = chatListService.selectBySuchId(chatId);

        // Convert the Chat object to JSON and send the response
        String jsonString = JSON.toJSONString(chat);
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
