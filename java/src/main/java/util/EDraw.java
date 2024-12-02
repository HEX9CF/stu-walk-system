package util;

import java.util.ArrayList;
import java.util.List;

/**
 * The EDraw class is responsible for generating a visual representation of the shortest path 
 * between two nodes in a multi-floor environment. It uses Dijkstra's algorithm to find the 
 * shortest path, classifies the path into different floors, and then generates corresponding 
 * images for each floor, returning the URLs of these images.
 */
public class EDraw {

    /**
     * This method calculates the shortest path between the start node (st) and the end node (ed) 
     * using the Dijkstra algorithm, classifies the path into different floors, and generates 
     * floor-specific images to represent the path. The images are saved with a random number 
     * suffix to ensure uniqueness. The method returns a list of URLs for the generated images.
     *
     * @param st The starting node (1-based index)
     * @param ed The ending node (1-based index)
     * @param randomNumber A random number used to differentiate image names
     * @return A list of URLs representing the images corresponding to the drawn paths on various floors
     */
    public static List<String> work(int st, int ed, int randomNumber){
        // Get the shortest path between the start and end nodes using Dijkstra's algorithm
        List<Integer> list = EDijkstra.dijkstra(st, ed);
        
        // Print the nodes in the shortest path for debugging purposes
        for(Integer item : list){
            System.out.println(item);
        }
        
        // Initialize a list to store the URLs of the generated images
        List<String> String_list = new ArrayList<>();
        
        // Define URLs for images on each floor (the paths are saved on a remote server)
        String f1 = "https://database.ccjy16.top/data/floor1_path" + randomNumber + ".png";
        String f2 = "https://database.ccjy16.top/data/floor2_path" + randomNumber + ".png";
        String f3 = "https://database.ccjy16.top/data/floor3_path" + randomNumber + ".png";
        String f4 = "https://database.ccjy16.top/data/floor4_path" + randomNumber + ".png";
        String f5 = "https://database.ccjy16.top/data/floor5_path" + randomNumber + ".png";
        
        // Lists to store the nodes of each floor
        List<Integer> order_list = new ArrayList<>();
        List<Integer> floor1 = new ArrayList<>();
        List<Integer> floor2 = new ArrayList<>();
        List<Integer> floor3 = new ArrayList<>();
        List<Integer> floor4 = new ArrayList<>();
        List<Integer> floor5 = new ArrayList<>();
        
        // Classify the nodes into different floors
        for(Integer item : list){
            int t = judgeFloor(item); // Determine the floor of the current node
            
            // Add the floor to the order list if it's not already present
            if(order_list.isEmpty() || order_list.get(order_list.size() - 1) != t){
                order_list.add(t);
            }
            
            // Add the node to the corresponding floor list
            if(t == 1) floor1.add(item);
            else if(t == 2) floor2.add(item);
            else if(t == 3) floor3.add(item);
            else if(t == 4) floor4.add(item);
            else floor5.add(item);
        }
        
        // Filter the floors that have more than one node
        List<Integer> last_order_list = new ArrayList<>();
        for(Integer item : order_list){
            if(item == 1 && floor1.size() > 1) last_order_list.add(item);
            else if(item == 2 && floor2.size() > 1) last_order_list.add(item);
            else if(item == 3 && floor3.size() > 1) last_order_list.add(item);
            else if(item == 4 && floor4.size() > 1) last_order_list.add(item);
            else if(item == 5 && floor5.size() > 1) last_order_list.add(item);
        }
        
        // Generate and add URLs for the images of each floor in the path
        for(Integer item: last_order_list){
            if(item == 1){
                Floor1Draw.pointAndDraw(floor1, randomNumber); // Draw the path on floor 1
                String_list.add(f1);
            } else if(item == 2){
                floor2.replaceAll(integer -> integer - 121); // Adjust floor 2 node IDs
                Floor2Draw.pointAndDraw(floor2, randomNumber); // Draw the path on floor 2
                String_list.add(f2);
            } else if(item == 3){
                floor3.replaceAll(integer -> integer - 243); // Adjust floor 3 node IDs
                Floor3Draw.pointAndDraw(floor3, randomNumber); // Draw the path on floor 3
                String_list.add(f3);
            } else if(item == 4){
                floor4.replaceAll(integer -> integer - 365); // Adjust floor 4 node IDs
                Floor4Draw.pointAndDraw(floor4, randomNumber); // Draw the path on floor 4
                String_list.add(f4);
            } else if(item == 5){
                floor5.replaceAll(integer -> integer - 462); // Adjust floor 5 node IDs
                Floor5Draw.pointAndDraw(floor5, randomNumber); // Draw the path on floor 5
                String_list.add(f5);
            }
        }
        
        // Return the list of image URLs
        return String_list;
    }

    /**
     * This method determines the floor number based on the node ID.
     * The node IDs are divided into 5 ranges, each representing a different floor.
     *
     * @param number The node ID
     * @return The floor number (1-5) corresponding to the node ID
     */
    public static Integer judgeFloor(Integer number){
        if(number <= 121) return 1; // Floor 1
        else if(number <= 243) return 2; // Floor 2
        else if(number <= 365) return 3; // Floor 3
        else if(number <= 462) return 4; // Floor 4
        else return 5; // Floor 5
    }
}
