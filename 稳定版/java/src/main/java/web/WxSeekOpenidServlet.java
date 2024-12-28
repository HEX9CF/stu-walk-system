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
 * This servlet retrieves user information based on the provided JWT token.
 * The token is verified to extract the openid, which is then used to fetch
 * user details from the database and return them as a JSON response.
 */
@WebServlet("/WxSeekOpenidServlet")
public class WxSeekOpenidServlet extends HttpServlet {

    /**
     * Handles GET requests to seek user information by verifying the provided token.
     * The token is validated, and if successful, the corresponding user data is retrieved
     * from the database using the openid and sent back as a JSON response.
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

        // Initialize the service class to interact with the user data
        WxUsersService wxUsersService = new WxUsersService();

        // Retrieve the token from the request
        String token = req.getParameter("token");

        // Verify the token and extract the openid
        String openid = verifyToken(token);

        // Fetch the user information using the openid
        WxUsers wxUsers = wxUsersService.seekOpenid(openid);

        // Return the user information as a JSON response
        resp.getWriter().write(JSON.toJSONString(wxUsers));
    }

    /**
     * Handles POST requests by forwarding the request to the doGet method.
     * This ensures that both GET and POST requests are handled in the same way.
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

    // Secret key for JWT token verification
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
