package web;

import com.alibaba.fastjson.JSON;
import pojo.GoodsDetail;
import service.GoodsDetailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling search requests related to goods details.
 * It takes a search query as input and returns a list of goods that match the query.
 */
@WebServlet("/searchServlet")
public class SearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        // Get the search query from the request parameters
        String searchQuery = req.getParameter("index");

        // Create service instance and perform the search
        GoodsDetailService goodsDetailService = new GoodsDetailService();
        List<GoodsDetail> searchResults = goodsDetailService.searchLike(searchQuery);

        // Convert the list of goods to JSON format
        String jsonString = JSON.toJSONString(searchResults);

        // Write the JSON response
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
