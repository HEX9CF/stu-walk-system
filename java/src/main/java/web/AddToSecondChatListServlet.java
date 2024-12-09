package web;

import com.alibaba.fastjson.JSON;
import service.ChatListService;
import service.SecondChatService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for adding a new entry to the second chat list.
 */
@WebServlet("/AddToSecondChatListServlet")
public class AddToSecondChatListServlet extends HttpServlet {
    
    /**
     * Handles GET requests for adding a new entry to the second chat list.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        int id = Integer.parseInt(req.getParameter("id"));
        int commentId = Integer.parseInt(req.getParameter("comment_id"));
        String userName = req.getParameter("user_name");
        String commentName = req.getParameter("comment_name");
        String date = req.getParameter("date");
        String content = req.getParameter("content");

        SecondChatService secondChatService = new SecondChatService();
        secondChatService.add(id, commentId, userName, commentName, date, content);

        int commentCount = secondChatService.selectCount(commentId);

        ChatListService chatListService = new ChatListService();
        chatListService.updateCount(commentCount, commentId);
    }

    /**
     * Handles POST requests by forwarding to the doGet method.
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
