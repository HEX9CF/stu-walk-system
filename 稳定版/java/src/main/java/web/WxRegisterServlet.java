package web;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import pojo.WxUsers;
import service.WxUsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles the user registration process using a token for authentication.
 * The token is verified, and then the user information (name and profile picture) is registered.
 * Upon successful registration, the user data is returned as a JSON response.
 */
@WebServlet("/WxRegisterServlet")
public class WxRegisterServlet extends HttpServlet {

    /**
     * Handles GET requests for user registration.
     * Verifies the token, extracts user data (name and profile picture), and registers the user.
     * The user's data is then returned as a JSON response.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object used to send the response back to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type to JSON
        resp.setContentType("application/json; charset=utf-8");

        // Initialize the service class to interact with user data
        WxUsersService wxUsersService = new WxUsersService();

        // Retrieve parameters from the request
        String token = req.getParameter("token");
        String name = req.getParameter("name");
        String headPic = req.getParameter("head_pic");

        // Verify the token to get the openid
        String openid = verifyToken(token);

        // Register the user with the openid, name, and head_pic
        wxUsersService.registerUser(openid, name, headPic);

        // Fetch the registered user data using openid
        WxUsers wxUsers = wxUsersService.seekOpenid(openid);

        // Set the openid in the user object and send the response as JSON
        wxUsers.setOpenid(token);
        resp.getWriter().write(JSON.toJSONString(wxUsers));
    }

    /**
     * Handles POST requests by calling the doGet method to process the request.
     * This method ensures that the servlet can handle both GET and POST requests.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object used to send the response back to the client
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set request encoding to UTF-8 for proper handling of non-ASCII characters
        req.setCharacterEncoding("utf-8");

        // Call the doGet method to handle the POST request
        this.doGet(req, resp);
    }

    // Secret key for JWT token validation
    private static final String SECRET_KEY = "uVfG6qzK3D9XpZs5jY5Rf3PbX9GvJ7y9e0k4Tm+7q8I=";

    /**
     * Verifies the JWT token and extracts the openid.
     * If the token is valid, the openid associated with the token is returned.
     *
     * @param token the JWT token to be verified
     * @return the openid extracted from the token, or null if the token is invalid
     */
    private String verifyToken(String token) {
        try {
            // Create a JWT verification algorithm using the secret key
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            // Verify the token and decode it
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("winter") // The issuer of the token
                    .build()
                    .verify(token);

            // Return the openid from the decoded JWT
            return jwt.getSubject();
        } catch (Exception e) {
            // Print the exception for debugging purposes
            e.printStackTrace();
            return null; // Return null if verification fails
        }
    }
}
