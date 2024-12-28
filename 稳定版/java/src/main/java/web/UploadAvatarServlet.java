package web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;

import com.alibaba.fastjson.JSON;

/**
 * This servlet handles the upload of avatar images. It supports file upload through the 
 * 'multipart/form-data' content type and stores the uploaded files in a specified directory.
 */
@WebServlet("/UploadAvatarServlet")
public class UploadAvatarServlet extends HttpServlet {

    // Directory where uploaded avatar images will be stored
    private static final String UPLOAD_DIRECTORY = "/www/wwwroot/database.ccjy16.top/data/";

    // List of allowed file extensions for the avatar image
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set the response content type
        resp.setContentType("application/json; charset=utf-8");

        // Check if the request is of type multipart/form-data (used for file uploads)
        if (!ServletFileUpload.isMultipartContent(req)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"请求类型错误，必须是 multipart/form-data\"}");
            return;
        }

        // Configure the file upload
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // Set the memory threshold to store small files in memory, larger files will be stored in temporary files
        factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
        // Set the temporary file repository location
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        // Set the maximum file size for a single file (5MB)
        upload.setFileSizeMax(5 * 1024 * 1024); // 5MB
        // Set the maximum request size (10MB)
        upload.setSizeMax(10 * 1024 * 1024); // 10MB

        // Set the character encoding for the uploaded files
        upload.setHeaderEncoding("UTF-8");

        // Create the directory to store uploaded files if it does not exist
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            // Parse the request
            List<FileItem> formItems = upload.parseRequest(req);

            if (formItems != null && formItems.size() > 0) {
                String fileUrl = null;
                // Loop through each form item
                for (FileItem item : formItems) {
                    // Handle regular form fields (not files)
                    if (item.isFormField()) {
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getString("UTF-8");
                        System.out.println("普通表单字段：" + fieldName + " = " + fieldValue);
                    } else {
                        // Handle file upload fields
                        String fileName = new File(item.getName()).getName();
                        // Get the file extension
                        String fileExt = getFileExtension(fileName).toLowerCase();
                        if (!ALLOWED_EXTENSIONS.contains(fileExt)) {
                            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            resp.getWriter().write("{\"error\":\"不支持的文件类型\"}");
                            return;
                        }
                        // Generate a unique file name to avoid conflicts
                        String newFileName = UUID.randomUUID().toString() + "." + fileExt;
                        String filePath = UPLOAD_DIRECTORY + newFileName;
                        File storeFile = new File(filePath);
                        // Save the file to disk
                        item.write(storeFile);

                        // Return the URL of the uploaded file (accessed via a fixed domain)
                        fileUrl = "https://database.ccjy16.top/data/" + newFileName;
                        System.out.println("文件上传成功，访问 URL：" + fileUrl);
                    }
                }
                // Return a successful JSON response with the file URL
                Map<String, Object> data = new HashMap<>();
                data.put("fullurl", fileUrl);
                Map<String, Object> response = new HashMap<>();
                response.put("code", 0); // Custom status code, 0 means success
                response.put("msg", "上传成功");
                response.put("data", data);
                String jsonString = JSON.toJSONString(response);
                resp.getWriter().write(jsonString);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"文件上传失败\"}");
        }
    }

    /**
     * Gets the file extension from the given file name.
     *
     * @param fileName The name of the file
     * @return The file extension (e.g., "jpg", "png")
     */
    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        return index == -1 ? "" : fileName.substring(index + 1);
    }
}
