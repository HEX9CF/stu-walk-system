package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.SearchData;

import java.util.List;

/**
 * This interface defines the operations for performing search queries in the database.
 * It provides a method to search for records that match a given string.
 */
public interface SearchMapper {

    /**
     * Searches for records that contain the specified string in the relevant fields.
     * @param searchTerm The string to search for in the database.
     * @return A list of `SearchData` objects that match the search criteria.
     */
    List<SearchData> searchLike(@Param("s") String searchTerm);
}
