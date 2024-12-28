package pojo;

/**
 * Chat represents a chat message in the system.
 * It contains the details of a chat message including the user, content, date, and other metadata.
 */
public class Chat {
    private Integer id;
    private Integer userId;       // user_id -> userId (camelCase)
    private String userName;       // user_name -> userName (camelCase)
    private String content;
    private String date;
    private Integer count;
    private Integer sex;
    private String headPic;        // head_pic -> headPic (camelCase)

    // Getters and Setters

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", count=" + count +
                ", sex=" + sex +
                ", headPic='" + headPic + '\'' +
                '}';
    }
}
