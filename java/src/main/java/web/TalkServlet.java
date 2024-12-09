package web;

import service.ChatListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for adding a chat message to the chat list.
 */
@WebServlet("/TalkServlet")
public class TalkServlet extends HttpServlet {

    /**
     * Handles GET requests to add a new chat message.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Parse parameters from the request
        int userId = Integer.parseInt(req.getParameter("user_id"));
        String userName = req.getParameter("user_name");
        String content = req.getParameter("content");
        String date = req.getParameter("date");
        int sex = Integer.parseInt(req.getParameter("sex"));

        // Add chat message using the service
        ChatListService chatService = new ChatListService();
        chatService.addChat(userId, userName, content, date, sex);
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
