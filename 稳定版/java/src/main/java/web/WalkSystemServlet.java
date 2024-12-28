package web;

import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import util.SimulatedAnnealing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Random;

/**
 * Servlet to calculate the optimal walking route between a start and end point, with intermediate destinations.
 * Uses simulated annealing to find the best route.
 */
@WebServlet("/walkSystemServlet")
public class WalkSystemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("application/json; charset=utf-8");

        try {
            // Parse start and end points from request parameters
            int st = Integer.parseInt(req.getParameter("st"));
            int ed = Integer.parseInt(req.getParameter("ed"));
            String jsonString = req.getParameter("wantgo");

            // Validate input parameters
            if (jsonString == null || jsonString.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"'wantgo' parameter is required.\"}");
                return;
            }

            // Parse the intermediate destinations (wantgo) into an int array
            JSONArray jsonArray = new JSONArray(jsonString);
            int[] wantGo = new int[jsonArray.length() + 2];  // Add space for start and end points

            // Add start point at the beginning and end point at the end
            wantGo[0] = st;
            wantGo[wantGo.length - 1] = ed;

            // Populate the intermediate destinations
            for (int i = 0; i < jsonArray.length(); i++) {
                wantGo[i + 1] = jsonArray.getInt(i);
            }

            // Call SimulatedAnnealing to find the optimal route
            Random random = new Random();
            int randomNumber = random.nextInt(1001);  // Generate a random number between 0 and 1000
            int[] result = SimulatedAnnealing.zuiDuanLu(wantGo, randomNumber);

            // Convert the route result to string array
            String[] stringWant = new String[result.length + 1];
            for (int i = 1; i <= result.length; i++) {
                stringWant[i] = String.valueOf(result[i - 1]);
            }

            // Set the first element to be the URL for the generated image
            String t = "https://database.ccjy16.top/data/photo_path" + randomNumber + ".png";
            stringWant[0] = t;

            // Return the route as a JSON response
            resp.getWriter().write(JSON.toJSONString(stringWant));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid number format in parameters.\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"An unexpected error occurred.\"}");
            e.printStackTrace();  // Log the error to server logs for debugging
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        this.doGet(req, resp);  // Handle POST requests the same as GET requests
    }
}
