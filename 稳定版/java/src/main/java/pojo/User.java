package pojo;

/**
 * User represents a user entity with basic information such as username, password, sex, introduction, and profile picture.
 */
public class User {

    private Integer id;              // The unique identifier for the user
    private String username;         // The username of the user
    private String password;         // The password of the user
    private Integer sex;             // The sex of the user (1 for male, 0 for female, etc.)
    private String introduction;     // A brief introduction of the user
    private String headPic;          // The profile picture URL of the user

    // Getter for id
    public Integer getId() {
        return id;
    }

    // Setter for id
    public void setId(Integer id) {
        this.id = id;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for sex
    public Integer getSex() {
        return sex;
    }

    // Setter for sex
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    // Getter for introduction
    public String getIntroduction() {
        return introduction;
    }

    // Setter for introduction
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    // Getter for headPic
    public String getHeadPic() {
        return headPic;
    }

    // Setter for headPic
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", introduction='" + introduction + '\'' +
                ", headPic='" + headPic + '\'' +
                '}';
    }
}
