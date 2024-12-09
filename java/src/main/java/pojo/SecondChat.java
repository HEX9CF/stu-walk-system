package pojo;

/**
 * SecondChat represents a secondary level of chat related to a comment.
 * It contains details like user information, content, date, etc.
 */
public class SecondChat {

    private Integer id;               // The unique identifier for the second chat
    private Integer commentId;        // The identifier of the original comment
    private String userName;          // The name of the user who posted the second chat
    private String commentName;       // The name of the person who made the original comment
    private String date;              // The date the second chat was posted
    private String content;           // The content of the second chat
    private Integer uniqueId;         // The unique identifier for the second chat
    private String headPic;           // The user's profile picture URL
    private Integer commentUserId;    // The user ID of the person who made the original comment

    // Getter for id
    public Integer getId() {
        return id;
    }

    // Setter for id
    public void setId(Integer id) {
        this.id = id;
    }

    // Getter for commentId
    public Integer getCommentId() {
        return commentId;
    }

    // Setter for commentId
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    // Getter for userName
    public String getUserName() {
        return userName;
    }

    // Setter for userName
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter for commentName
    public String getCommentName() {
        return commentName;
    }

    // Setter for commentName
    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    // Getter for date
    public String getDate() {
        return date;
    }

    // Setter for date
    public void setDate(String date) {
        this.date = date;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }

    // Getter for uniqueId
    public Integer getUniqueId() {
        return uniqueId;
    }

    // Setter for uniqueId
    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    // Getter for headPic
    public String getHeadPic() {
        return headPic;
    }

    // Setter for headPic
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    // Getter for commentUserId
    public Integer getCommentUserId() {
        return commentUserId;
    }

    // Setter for commentUserId
    public void setCommentUserId(Integer commentUserId) {
        this.commentUserId = commentUserId;
    }

    @Override
    public String toString() {
        return "SecondChat{" +
                "id=" + id +
                ", commentId=" + commentId +
                ", userName='" + userName + '\'' +
                ", commentName='" + commentName + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", uniqueId=" + uniqueId +
                ", headPic='" + headPic + '\'' +
                ", commentUserId=" + commentUserId +
                '}';
    }
}
