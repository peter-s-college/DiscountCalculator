import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

            // Prepare output file to write customer discount data
            PrintWriter writer = new PrintWriter("customerdiscount.txt");

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

                // Step 4: Parse and validate class value as an integer (must be between 1 and 3)

                // Declaring variable for customerClass
                int customerClass;
                // Using try to catch error in case totalPurchase is not a valid number
                try {
                    // Parsing totalPurchase to Integer
                    customerClass = Integer.parseInt(classLine);
                    // Logging for debugging
                    System.out.println("The customerClass: " + customerClass);

                    // Validating class if it is between 1 and 3 as required
                    if (customerClass < 1 || customerClass > 3) {
                        // Logging error for user
                        System.out.println("Class must be between 1 and 3 for customer: " + firstName + " " + lastName);
                        // Using continue to proceed with the loop parsing other available data (customer) after logging the error for the user
                        continue;
                    }
                } catch (NumberFormatException e) { // Catching exception if the value is not a valid number
                    // Logging error for user
                    System.out.println("Invalid class format for customer: " + firstName + " " + lastName);
                    // Using continue to proceed with the loop parsing other available data (customer) after logging the error for the user
                    continue;
                }

                // Step 5: Parse and validate last purchase year (should be a reasonable year)
                int lastPurchaseYear;
                int currentYear = 2024; // Define current year
                // Using try to catch error in case totalPurchase is not a valid number
                try {
                    // Parsing lastPurchaseYear to Integer
                    lastPurchaseYear = Integer.parseInt(lastPurchaseLine);
                    // Logging for debugging
                    System.out.println("The lastPurchaseYear: " + lastPurchaseYear);

                    // Validating lastPurchaseYear to be a reasonable date
                    if (lastPurchaseYear < 1900 || lastPurchaseYear > currentYear) {
                        // Logging error for user
                        System.out.println("Last purchase year must be between 1900 and 2024" + " for customer: " + firstName + " " + lastName);
                        // Using continue to proceed with the loop parsing other available data (customer) after logging the error for the user
                        continue;
                    }
                } catch (NumberFormatException e) { // Catching exception if the value is not a valid number
                    // Logging error for user
                    System.out.println("Invalid year format for last purchase of customer: " + firstName + " " + lastName);
                    // Using continue to proceed with the loop parsing other available data (customer) after logging the error for the user
                    continue;
                }

                // Step 6: Calculate final value after discount by calling calculateDiscount method using validated data
                double finalValue = calculateDiscount(totalPurchase, customerClass, lastPurchaseYear);

                // Logging for debugging
                System.out.println("The final discounted amount: " + finalValue);

                // Write validated and calculated customer data to output file
                writer.println(firstName + " " + lastName);
                writer.println(finalValue);
            }

            // Close resources to prevent memory issues and ensure file written correctly
            scanner.close();
            writer.close();

        } catch (FileNotFoundException e) { // Catching error if customers.txt is not located at the path it was defined
            // Logging error for user
            System.out.println("File not found. Please ensure 'customers.txt' is in the correct location.");
        }
    }

    /**
     * Calculates the discounted purchase value based on customer class and last purchase year.
     *
     * @param purchaseValue The original purchase amount
     * @param customerClass The class of the customer (1, 2, or 3)
     * @param lastPurchaseYear The year of the last purchase
     * @return The final discounted value
     */
    public static double calculateDiscount(double purchaseValue, int customerClass, int lastPurchaseYear) {
        int currentYear = 2024; // Current year for checking last purchase
        double discountRate = 0.0; // Initialize discount rate

        // Logging for debugging
        System.out.println("The class is: " + customerClass);

        // Determine discount based on customer class and last purchase year
        if (customerClass == 1) {
            // Logging for debugging
            System.out.println("The class is validated as 1");
            if (lastPurchaseYear == currentYear) {  // last purchase in 2024
                discountRate = 0.30; // 30% discount
            } else if (lastPurchaseYear >= currentYear - 5) {  // purchase within the last 5 years
                discountRate = 0.20; // 20% discount
            } else {  // no purchase in the last 5 years
                discountRate = 0.10; // 10% discount
            }
        } else if (customerClass == 2) {
            // Logging for debugging
            System.out.println("The class is validated as 2");
            if (lastPurchaseYear == currentYear) {  // last purchase in 2024
                discountRate = 0.15; // 15% discount
            } else if (lastPurchaseYear >= currentYear - 5) {  // purchase within the last 5 years
                discountRate = 0.13; // 13% discount
            } else {  // no purchase in the last 5 years
                discountRate = 0.05; // 5% discount
            }
        } else if (customerClass == 3) {
            // Logging for debugging
            System.out.println("The class is validated as 3");
            if (lastPurchaseYear == currentYear) { // last purchase in 2024
                discountRate = 0.03; // 3% discount
            } else { // purchase before 2024
                discountRate = 0.0; // No discount
            }
        }

        // Logging for debugging
        System.out.println("Discount is: " + (discountRate * 100) + "%");
        System.out.println("Total to pay: " + (purchaseValue * (1 - discountRate)));

        // Apply discount to purchase value
        return purchaseValue * (1 - discountRate);
    }
}
