package util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * SqlSessionFactoryUtil class provides a singleton instance of the SqlSessionFactory.
 * It is responsible for creating the MyBatis SqlSessionFactory from a specified MyBatis 
 * configuration file (mybatis-config.xml). This class provides a static method to obtain 
 * the SqlSessionFactory for interacting with the database.
 */
public class SqlSessionFactoryUtil {
    
    // The singleton instance of SqlSessionFactory
    private static final SqlSessionFactory sqlSessionFactory;
    
    static {
        String resource = "mybatis-config.xml";  // Path to the MyBatis configuration file
        InputStream inputStream = null;
        
        try {
            // Load the MyBatis configuration file as an InputStream
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            // If an exception occurs during loading, throw a runtime exception
            throw new RuntimeException("Error loading MyBatis configuration file.", e);
        }
        
        // Build the SqlSessionFactory using the loaded configuration
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * Returns the singleton instance of SqlSessionFactory.
     *
     * @return SqlSessionFactory instance.
     */
    public static SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }
}
