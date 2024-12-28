package web;

import com.alibaba.fastjson.JSON;
import util.EDraw;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * This servlet handles requests to simulate the walking system for a given range of floors.
 * It generates a random number and uses the EDraw utility to simulate work activities for the given floor range.
 * The result is returned as a JSON response.
 */
@WebServlet("/E_walkSystemServlet")
public class EWalkSystemServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; // Recommended to add serialVersionUID

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON with UTF-8 encoding
        resp.setContentType("text/json; charset=utf-8");

        // Retrieve the start and end floor numbers from the request parameters
        int startFloor = Integer.parseInt(req.getParameter("st"));
        int endFloor = Integer.parseInt(req.getParameter("ed"));

        // Create a random number generator and generate a random number between 0 and 10000
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(10001);

        // Use the EDraw utility to simulate work for the given range of floors and random number
        List<String> workItems = EDraw.work(startFloor, endFloor, randomNumber);

        // Convert the work items list to JSON and send it in the response
        resp.getWriter().write(JSON.toJSONString(workItems));

        // Optional: If the floor data is needed, use the following code to read the floor data
        // double[][] floorDistances;
        // whole_floor_ReadSheet3 readSheet = new whole_floor_ReadSheet3();
        // floorDistances = readSheet.read();
        // resp.getWriter().write(JSON.toJSONString(floorDistances));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the character encoding for the request
        req.setCharacterEncoding("utf-8");

        // Delegate the handling of POST requests to doGet
        this.doGet(req, resp);
    }
}
