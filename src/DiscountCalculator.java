import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
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

                // Step 2: Parse and validate customer name

                // Split name line into first and last name with a space as defined being a valid format in the description
                String[] nameParts = nameLine.split(" ");
                // Logging for debugging, usin Arrays.toString() to convert array into a readable string format as trying to print the array as string would only print memory address.
                System.out.println("The nameParts: " + Arrays.toString(nameParts));
                // If the name length is not 2 mean there was not validly declared firstname and lastname, also the first name can be just letters in any case
                // The last name may include numbers as well along the letters
                if (nameParts.length != 2 || !nameParts[0].matches("[a-zA-Z]+") || !nameParts[1].matches("[a-zA-Z0-9]+")) {
                    // Logging error for user
                    System.out.println("Invalid name format for customer: " + nameLine);
                    continue; // Skip the loop to next customer if validation fails
                }
                // Instantiate variables for the firstname and last name
                String firstName = nameParts[0];
                String lastName = nameParts[1];
                // Logging for debugging
                System.out.println("The firstName: " + firstName);
                System.out.println("The lastName: " + lastName);

                // Step 3: Parse and validate total purchase amount as a double

                // Declare variable for totalPurchase
                double totalPurchase;
                // Using try to catch error in case totalPurchase is not a valid number
                try {
                    // Parsing totalPurchase to Double
                    totalPurchase = Double.parseDouble(purchaseLine);
                    // Logging for debugging
                    System.out.println("The totalPurchase: " + totalPurchase);

                    // Validating if the totalPurchase is not a negative number
                    if (totalPurchase < 0) {
                        // Logging error for user
                        System.out.println("Purchase amount must be a positive value for customer: " + firstName + " " + lastName);
                        // Using continue to proceed with the loop parsing other available data (customer) after logging the error for the user
                        continue;
                    }
                } catch (NumberFormatException e) { // Catching exception if the value is not a valid number
                    // Logging error for user
                    System.out.println("Invalid purchase amount format for customer: " + firstName + " " + lastName);
                    // Using continue to proceed with the loop parsing other available data (customer) after logging the error for the user
                    continue;
                }

            }

            // Close resources to prevent memory issues
            scanner.close();

        } catch (FileNotFoundException e) { // Catching error if customers.txt is not located at the path it was defined
            // Logging error for user
            System.out.println("File not found. Please ensure 'customers.txt' is in the correct location.");
        }
    }
}
