package pojo;

import lombok.Data;

/**
 * Point represents a geographical or Cartesian point with a name and coordinates (x, y).
 */
@Data
public class Point {

    private String name;   // The name of the point (e.g., location name)
    private Double x;      // The x-coordinate of the point
    private Double y;      // The y-coordinate of the point

    // Constructor is automatically generated by Lombok's @Data annotation
    // Lombok will generate getters, setters, toString, equals, and hashCode methods

}