package pojo;

/**
 * Represents a user in the system.
 */
public class ChaoUser {

    private Integer id;
    private String username;
    private String password;

    /**
     * Gets the user ID.
     *
     * @return the user ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the user ID.
     *
     * @param id the user ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return string representation of the user
     */
    @Override
    public String toString() {
        return "ChaoUser{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
