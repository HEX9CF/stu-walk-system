package web;

import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import util.Simulated_Annealing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Servlet for processing the walk system requests using Simulated Annealing.
 */
@WebServlet("/WalkSystemServlet")
public class WalkSystemServlet extends HttpServlet {

    /**
     * Handles GET requests to calculate the shortest path and return the result as JSON.
     *
     * @param req  the HttpServletRequest object
     * @param resp the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");

        // Parse input parameters
        int startPoint = Integer.parseInt(req.getParameter("st"));
        int endPoint = Integer.parseInt(req.getParameter("ed"));
        String jsonString = req.getParameter("wantgo");

        // Convert JSON array to int array
        JSONArray jsonArray = new JSONArray(jsonString);
        int[] desiredPath = new int[jsonArray.length() + 2];
        for (int i = 0; i < jsonArray.length(); i++) {
            desiredPath[i + 1] = jsonArray.getInt(i);
        }
        desiredPath[0] = startPoint;
        desiredPath[desiredPath.length - 1] = endPoint;

        // Generate random seed and calculate the shortest path
        Random random = new Random();
        int randomSeed = random.nextInt(1001); // Generate a random integer between 0 and 1000
        int[] shortestPath = Simulated_Annealing.zuiDuanLu(desiredPath, randomSeed);

        // Prepare the response array
        String[] responseArray = new String[shortestPath.length + 1];
        for (int i = 1; i <= shortestPath.length; i++) {
            responseArray[i] = String.valueOf(shortestPath[i - 1]); // Convert int to String
        }

        // Add the photo path as the first element in the response
        String photoPath = "https://database.ccjy16.top/data/photo_path" + randomSeed + ".png";
        responseArray[0] = photoPath;

        // Write the response as JSON
        resp.getWriter().write(JSON.toJSONString(responseArray));
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
