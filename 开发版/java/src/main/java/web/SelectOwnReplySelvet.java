
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
     * This servlet handles HTTP requests for selecting own replies in a chat.
     */
    @WebServlet("/selectOwnReplyServlet")
    public class SelectOwnReplyServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        /**
         * Handles HTTP GET requests for retrieving own replies.
         *
         * @param req  the HttpServletRequest object
         * @param resp the HttpServletResponse object
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException        if an I/O error occurs
         */
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("text/json; charset=utf-8");
            SecondChatService secondChatService = new SecondChatService();
            String idParam = req.getParameter("id");
            int id = Integer.parseInt(idParam);
            List<SecondChat> secondChats = secondChatService.selectOwnReply(id);
            String jsonString = JSON.toJSONString(secondChats);
            resp.getWriter().write(jsonString);
        }

        /**
         * Handles HTTP POST requests by delegating to doGet method.
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

