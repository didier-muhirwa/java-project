/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java.assignment;

/**
 *
 * @author PC
 */
import java.util.Scanner;
public class BicycleRental {
    
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int startTime;
        int endTime;
        int totalCost = 0;

        System.out.print("Enter starting time (0 - 23): ");
        startTime = input.nextInt();

        System.out.print("Enter ending time (1 - 24): ");
        endTime = input.nextInt();

        if (startTime < 0 || startTime > 23 || endTime < 1 || endTime > 24 || startTime >= endTime) {
            System.out.println("Invalid time input!");
            return;
        }

        
        for (int hour = startTime; hour < endTime; hour++) {

            if ((hour >= 0 && hour < 7) || (hour >= 21 && hour < 24)) {
                totalCost += 500;
            } 
            else if ((hour >= 7 && hour < 14) || (hour >= 19 && hour < 21)) {
                totalCost += 1000;
            } 
            else if (hour >= 14 && hour < 19) {
                totalCost += 1500;
            }
        }

        System.out.println("Total rental cost: " + totalCost + " RWF");

        input.close();
    }
}
