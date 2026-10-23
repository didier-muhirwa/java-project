/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java.assignment;


    import java.util.Scanner;

public class MushroomIdentifier {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String answer1, answer2, answer3;

        System.out.println("Think of one mushroom from this list:");
        System.out.println("Agaric Jaunissant");
        System.out.println("Amanite Tue-mouche");
        System.out.println("Cepe de Bordeaux");
        System.out.println("Coprin Chevelu");
        System.out.println("Girolle");
        System.out.println("Pied Bleu");

        System.out.println("\nAnswer with yes or no.\n");

        
        System.out.print("Does your mushroom have gills? ");
        answer1 = input.next();

        if (answer1.equalsIgnoreCase("no")) {
            
            System.out.println("Your mushroom is: Cepe de Bordeaux");
        } 
        else {
            
            System.out.print("Does your mushroom grow in a forest? ");
            answer2 = input.next();

            if (answer2.equalsIgnoreCase("no")) {
                
                System.out.print("Does your mushroom have a ring? ");
                answer3 = input.next();

                if (answer3.equalsIgnoreCase("yes")) {
                    System.out.println("Your mushroom is: Agaric Jaunissant");
                } else {
                    System.out.println("Your mushroom is: Coprin Chevelu");
                }
            } 
            else {
                
                System.out.print("Does your mushroom have a convex cup? ");
                answer3 = input.next();

                if (answer3.equalsIgnoreCase("yes")) {
                    

                    System.out.print("Does your mushroom have a ring? ");
                    String answer4 = input.next();

                    if (answer4.equalsIgnoreCase("yes")) {
                        System.out.println("Your mushroom is: Amanite Tue-mouche");
                    } else {
                        System.out.println("Your mushroom is: Pied Bleu");
                    }

                } else {
                    System.out.println("Your mushroom is: Girolle");
                }
            }
        }

        input.close();
    }
}

