package pojo;

/**
 * SearchData represents the data related to a search query, including the name and its corresponding ID.
 */
public class SearchData {

    private String name;  // The name associated with the search result
    private Integer id;   // The ID corresponding to the search result

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
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
        return "SearchData{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
