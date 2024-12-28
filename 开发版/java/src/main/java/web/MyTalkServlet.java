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
 * This servlet handles HTTP requests for chat data.
 */
@WebServlet("/myTalkServlet")
public class MyTalkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles HTTP GET requests.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException        if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");
        ChatListService chatListService = new ChatListService();
        int id = Integer.parseInt(req.getParameter("id"));
        List<Chat> chats = chatListService.selectById(id);
        String jsonString = JSON.toJSONString(chats);
        resp.getWriter().write(jsonString);
    }

    /**
     * Handles HTTP POST requests by calling doGet.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException        if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);
    }
}