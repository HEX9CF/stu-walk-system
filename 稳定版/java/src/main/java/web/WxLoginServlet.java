package web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

/**
 * This servlet handles the WeChat login process using the WeChat JSAPI.
 * It receives the authorization code from the client, retrieves the OpenID from the WeChat server,
 * and generates a JWT token for session management.
 * <p>
 * It supports POST requests that include the 'code' from the client-side WeChat login.
 * The OpenID is then used to generate a JWT token that is returned to the client.
 */
@WebServlet("/WxLoginServlet")
public class WxLoginServlet extends HttpServlet {
    
    // WeChat App ID and Secret (used to request user information from WeChat)
    private static final String APPID = "wxe2d464ef27db900e";
    private static final String SECRET = "882b2dd20ff48da9e1f27f8c76dfb635";

    // Secret key used to sign the JWT (should be stored securely in real applications)
    private static final String SECRET_KEY = "uVfG6qzK3D9XpZs5jY5Rf3PbX9GvJ7y9e0k4Tm+7q8I=";
    
    // JWT expiration time (10 years)
    private static final long EXPIRATION_TIME = 2L * 60 * 60 * 1000 * 40000; // 10 years

    /**
     * Handles POST requests for logging in via WeChat.
     * It processes the authorization code, requests the OpenID from WeChat, and returns a JWT token to the client.
     *
     * @param req  the HttpServletRequest object containing the request data
     * @param resp the HttpServletResponse object for sending the response
     * @throws ServletException if a servlet-related error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type to JSON
        resp.setContentType("application/json; charset=utf-8");

        // Read the request body and extract the 'code' parameter
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String requestBody = sb.toString();

        // Parse the JSON request
        HashMap<String, Object> params = JSON.parseObject(requestBody, HashMap.class);
        String code = (String) params.get("code");

        // Log the received code for debugging (Do not log sensitive data in production)
        System.out.println("Received code: " + code);

        // Validate that the code is provided
        if (code == null || code.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing 'code' parameter\"}");
            return;
        }

        // Retrieve the OpenID from WeChat by calling the WeChat API
        String openid = getOpenidFromWeChat(code);
        if (openid == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Failed to get OpenID from WeChat\"}");
            return;
        }

        // Generate a JWT token using the OpenID
        String token = generateToken(openid);

        // Send the token as a response to the client
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("token", token);
        String jsonString = JSON.toJSONString(responseMap);
        resp.getWriter().write(jsonString);
    }

    /**
     * Requests the OpenID from the WeChat API using the provided authorization code.
     * 
     * @param code the authorization code obtained from the client-side WeChat login
     * @return the OpenID retrieved from WeChat, or null if an error occurred
     */
    private String getOpenidFromWeChat(String code) {
        try {
            // URL to request the OpenID from WeChat's jscode2session API
            String urlStr = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID +
                    "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Read the response from WeChat API
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder responseSb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseSb.append(line);
            }
            is.close();

            // Parse the WeChat API response
            String result = responseSb.toString();
            System.out.println("WeChat response: " + result);

            HashMap<String, Object> resultMap = JSON.parseObject(result, HashMap.class);
            return (String) resultMap.get("openid"); // Return OpenID if available
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if an error occurred during the API request
        }
    }

    /**
     * Generates a JWT token using the OpenID.
     * 
     * @param openid the OpenID to be used as the subject in the JWT
     * @return the generated JWT token
     */
    private String generateToken(String openid) {
        // Set the current time and expiration time for the token
        Date now = new Date();
        Date expireTime = new Date(now.getTime() + EXPIRATION_TIME);

        // Use HMAC algorithm to sign the token
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        // Create and sign the JWT token
        return JWT.create()
                .withIssuer("winter")
                .withSubject(openid)
                .withIssuedAt(now)
                .withExpiresAt(expireTime)
                .sign(algorithm);
    }

    /**
     * Verifies the JWT token and extracts the OpenID from it.
     * 
     * @param token the JWT token to be verified
     * @return the OpenID extracted from the token, or null if verification fails
     */
    private String verifyToken(String token) {
        try {
            // Use HMAC algorithm to verify the token
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer("winter")
                    .build()
                    .verify(token);

            // Return the OpenID (subject) from the verified token
            return jwt.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if the token verification fails
        }
    }
}
