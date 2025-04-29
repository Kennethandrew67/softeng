package view;

import java.util.Scanner;

public abstract class View {
    protected static final Scanner scan = new Scanner(System.in);

    protected int getValidInteger(String message, int min, int max) {
        while (true) {
            System.out.print(message);
            if (scan.hasNextInt()) {
                int value = scan.nextInt();
                scan.nextLine(); // Consume the newline
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scan.next(); // Consume invalid input
            }
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }
}
