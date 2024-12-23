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

/**
 * Servlet for handling product detail requests.
 */
@WebServlet("/detailServlet")
public class DetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON with UTF-8 encoding
        resp.setContentType("application/json; charset=utf-8");

        // Create an instance of the goods detail service
        GoodsDetailService searchService = new GoodsDetailService();

        // Retrieve the product ID from the request parameter
        String id = req.getParameter("id");
        if (id != null) {
            try {
                // Convert the ID to an integer and search for the product details
                GoodsDetail data = searchService.select(Integer.parseInt(id));
                // Convert the product details to a JSON string
                String jsonString = JSON.toJSONString(data);
                // Write the JSON string to the response
                resp.getWriter().write(jsonString);
            } catch (NumberFormatException e) {
                // Handle the case where the ID is not a valid integer
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
            }
        } else {
            // Handle the case where the ID parameter is missing
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the request character encoding to UTF-8
        req.setCharacterEncoding("utf-8");
        // Delegate the POST request to the doGet method
        doGet(req, resp);
    }
}