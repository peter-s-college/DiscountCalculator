import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DiscountCalculator {

    // Defining the main method which will run when the application is started
    public static void main(String[] args) {
        // Using try to catch error in case the customer.txt cannot be located
        try {
            // Open input file containing customer data by defining the path and the name of the file
            File inputFile = new File("customers.txt");
            Scanner scanner = new Scanner(inputFile);

            // Loop through all lines in the input file for each customer, using while as the number of loops in not known and it will iterate
            while (scanner.hasNextLine()) {
                // Step 1: Read customer details from file and save them as a String at first so can apply validations in a more clear approach at parsing
                String nameLine = scanner.nextLine();
                String purchaseLine = scanner.nextLine();
                String classLine = scanner.nextLine();
                String lastPurchaseLine = scanner.nextLine();

                // Checking if data read properly (debugging)
                System.out.println("Line of name: " + nameLine);
                System.out.println("Line of purchase: " + purchaseLine);
                System.out.println("Line of class: " + classLine);
                System.out.println("Line of last purchase: " + lastPurchaseLine);

            }

            // Close resources to prevent memory issues
            scanner.close();

        } catch (FileNotFoundException e) { // Catching error if customers.txt is not located at the path it was defined
            // Logging error for user
            System.out.println("File not found. Please ensure 'customers.txt' is in the correct location.");
        }
    }
}
