package web;

import service.ChatListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling user chat submissions.
 * This servlet processes requests to add a new chat entry to the chat list.
 * It expects user information, chat content, and submission date.
 */
@WebServlet("/talkServlet")
public class TalkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        try {
            // Retrieve parameters from the request
            int userId = Integer.parseInt(req.getParameter("user_id"));
            String userName = req.getParameter("user_name");
            String content = req.getParameter("content");
            String date = req.getParameter("date");
            int sex = Integer.parseInt(req.getParameter("sex"));

            // Create a service instance and add the chat
            ChatListService chatListService = new ChatListService();
            chatListService.addChat(userId, userName, content, date, sex);
        } catch (NumberFormatException e) {
            // Handle invalid number format exception (e.g., user_id or sex is not a valid integer)
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            resp.getWriter().write("{\"error\":\"Invalid input data\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set request character encoding to UTF-8
        req.setCharacterEncoding("utf-8");

        // Delegate POST request to doGet method for processing
        this.doGet(req, resp);
    }
}
