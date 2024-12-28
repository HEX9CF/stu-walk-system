package service;

import mapper.SearchMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import pojo.SearchData;
import util.SqlSessionFactoryUtil;

import java.util.List;

/**
 * SearchService class provides methods for searching data in the database.
 * It uses MyBatis for SQL query execution and returns the result of the search.
 */
public class SearchService {
    // SqlSessionFactory to manage MyBatis sessions
    SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtil.getSqlSessionFactory();

    /**
     * Searches for records that match the given string (case-insensitive).
     *
     * @param searchString The search term to look for in the database.
     * @return List of SearchData that matches the search string.
     */
    public List<SearchData> searchLike(String searchString) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SearchMapper searchMapper = sqlSession.getMapper(SearchMapper.class);
        List<SearchData> searchResults = searchMapper.searchLike(searchString);
        sqlSession.close();
        return searchResults;
    }
}
