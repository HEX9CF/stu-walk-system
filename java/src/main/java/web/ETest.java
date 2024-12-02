package web;

import util.*;

import java.util.List;
import java.util.Random;

/**
 * This class is used for testing the EDraw functionality by generating a random number
 * and performing certain floor-related tasks. It demonstrates the process of calling 
 * various floor draw methods and logging the results.
 */
public class ETest {

    public static void main(String[] args) {
        // Create an instance of Random class to generate random numbers
        Random randomGenerator = new Random();
        
        // Generate a random number between 0 and 10000
        int randomNumber = randomGenerator.nextInt(10001);
        
        // Define start and end values for the floor range
        int startFloor = 15;
        int endFloor = 31;

        // Uncomment the following lines to test specific floor draw methods
        // floor1_Draw.test(startFloor, endFloor, randomNumber);
        // floor2_Draw.test(startFloor, endFloor, randomNumber);
        // floor3_Draw.test(startFloor, endFloor, randomNumber);
        // floor4_Draw.test(startFloor, endFloor, randomNumber);
        // floor5_Draw.test(startFloor, endFloor, randomNumber);
        
        // Get the list of work items from the EDraw utility based on the provided range and random number
        List<String> workItems = EDraw.work(startFloor, endFloor, randomNumber);
        
        // Optional: Iterate and print each work item if needed
        // for (String workItem : workItems) {
        //     System.out.println(workItem);
        // }
    }
}
