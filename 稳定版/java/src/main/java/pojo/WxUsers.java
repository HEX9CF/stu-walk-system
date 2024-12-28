package pojo;

/**
 * WxUsers represents a WeChat user with details such as openid, name, sex, introduction, and profile picture.
 */
public class WxUsers {

    private String openid;           // The unique identifier for the user in WeChat
    private String name;             // The name of the user
    private String headPic;          // The URL of the user's profile picture
    private Integer sex;             // The sex of the user (1 for male, 0 for female, etc.)
    private String introduction;     // A brief introduction of the user
    private Integer id;              // The unique identifier for the user in the system

    // Getter for openid
    public String getOpenid() {
        return openid;
    }

    // Setter for openid
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for headPic
    public String getHeadPic() {
        return headPic;
    }

    // Setter for headPic
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
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

    // Getter for id
    public Integer getId() {
        return id;
    }

    // Setter for id
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WxUsers{" +
                "openid='" + openid + '\'' +
                ", name='" + name + '\'' +
                ", headPic='" + headPic + '\'' +
                ", sex=" + sex +
                ", introduction='" + introduction + '\'' +
                ", id=" + id +
                '}';
    }
}
