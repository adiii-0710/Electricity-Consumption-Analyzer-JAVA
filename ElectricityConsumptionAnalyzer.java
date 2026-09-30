import java.util.Scanner;

/**
 * Case Study 184: Electricity Consumption Analyzer
 * 
 * Required Java Implementation:
 * - One-dimensional arrays (customerIds, customerNames, consumption)
 * - Scanner for monthly consumption input
 * - Loops to process customer records
 * - Operators for calculating total and average consumption
 * - if-else statements to identify high-consumption accounts
 * - Methods to modularize analysis components
 * - Searching to locate customer records
 * 
 * Expected Modules:
 * 1. Consumption Entry
 * 2. Average Calculation
 * 3. Highest Consumption
 * 4. High Usage Detection
 * 5. Analysis Report
 */
public class ElectricityConsumptionAnalyzer {

    // Default high consumption threshold in kWh
    private static final double DEFAULT_HIGH_THRESHOLD = 500.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("   ELECTRICITY CONSUMPTION ANALYZER SYSTEM        ");
        System.out.println("==================================================");

        // Pre-loaded sample data for 10 customers (IDs 101 to 110)
        int[] customerIds = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110};
        String[] customerNames = {
            "Alice Smith", "Bob Jones", "Charlie Brown", "Diana Prince", "Evan Wright",
            "Fiona Gallagher", "George Clark", "Hannah Abbott", "Ian Malcolm", "Julia Roberts"
        };
        double[] consumption = {450.50, 680.00, 320.00, 890.25, 510.00, 210.75, 750.50, 430.00, 920.00, 380.25};

        System.out.println("[Info] Initialized system with 10 sample customer records (IDs 101-110).");

        boolean exit = false;

        while (!exit) {
            printMenu();
            System.out.print("Enter your choice (1-7): ");
            int choice = getValidIntInput(scanner);

            switch (choice) {
                case 1:
                    // Module 1: Consumption Entry (Enter custom data)
                    System.out.print("\nEnter number of customers to record (will overwrite existing data): ");
                    int count = getValidIntInput(scanner);
                    if (count <= 0) {
                        System.out.println("Number of customers must be greater than 0.");
                        break;
                    }

                    customerIds = new int[count];
                    customerNames = new String[count];
                    consumption = new double[count];

                    enterConsumptionData(scanner, customerIds, customerNames, consumption);
                    break;

                case 2:
                    // Module 2: Average Calculation
                    if (!isDataAvailable(customerIds)) break;
                    double avg = calculateAverage(consumption);
                    System.out.println("\n-------------------------------------------");
                    System.out.printf("Average Monthly Consumption: %.2f kWh\n", avg);
                    System.out.println("-------------------------------------------");
                    break;

                case 3:
                    // Module 3: Highest Consumption
                    if (!isDataAvailable(customerIds)) break;
                    findHighestConsumption(customerIds, customerNames, consumption);
                    break;

                case 4:
                    // Module 4: High Usage Detection
                    if (!isDataAvailable(customerIds)) break;
                    System.out.print("\nEnter high consumption threshold in kWh (Default: " + DEFAULT_HIGH_THRESHOLD + "): ");
                    double threshold = getValidDoubleInput(scanner);
                    identifyHighUsage(customerIds, customerNames, consumption, threshold);
                    break;

                case 5:
                    // Module 5: Analysis Report
                    if (!isDataAvailable(customerIds)) break;
                    generateAnalysisReport(customerIds, customerNames, consumption);
                    break;

                case 6:
                    // Searching Customer Record
                    if (!isDataAvailable(customerIds)) break;
                    searchCustomer(scanner, customerIds, customerNames, consumption);
                    break;

                case 7:
                    System.out.println("\nThank you for using Electricity Consumption Analyzer. Goodbye!");
                    exit = true;
                    break;

                default:
                    System.out.println("\n[Error] Invalid choice! Please select an option between 1 and 7.");
            }
        }

        scanner.close();
    }

    /**
     * Displays the main menu options to the user.
     */
    private static void printMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println("1. Consumption Entry (Add/Overwrite Customer Data)");
        System.out.println("2. Calculate Average Consumption");
        System.out.println("3. Find Highest Consumption Customer");
        System.out.println("4. Identify High-Consumption Accounts");
        System.out.println("5. Generate Full Analysis Report");
        System.out.println("6. Search Customer Record");
        System.out.println("7. Exit");
        System.out.println("-------------------------------------------");
    }

    /**
     * Module 1: Consumption Entry
     * Accepts customer ID, name, and monthly consumption via Scanner.
     */
    public static void enterConsumptionData(Scanner scanner, int[] customerIds, String[] customerNames, double[] consumption) {
        System.out.println("\n--- Module 1: Enter Customer Consumption Data ---");
        for (int i = 0; i < customerIds.length; i++) {
            System.out.println("\nCustomer #" + (i + 1) + ":");

            System.out.print("Enter Customer ID: ");
            customerIds[i] = getValidIntInput(scanner);

            scanner.nextLine(); // Consume newline character
            System.out.print("Enter Customer Name: ");
            customerNames[i] = scanner.nextLine().trim();

            System.out.print("Enter Monthly Consumption (kWh): ");
            double usage = getValidDoubleInput(scanner);
            while (usage < 0) {
                System.out.print("Consumption cannot be negative. Re-enter kWh: ");
                usage = getValidDoubleInput(scanner);
            }
            consumption[i] = usage;
        }
        System.out.println("\n[Success] " + customerIds.length + " customer records entered successfully!");
    }

    /**
     * Module 2: Average Calculation
     * Calculates average consumption using arithmetic operators (+, /) and loops.
     */
    public static double calculateAverage(double[] consumption) {
        double totalSum = 0.0;
        for (int i = 0; i < consumption.length; i++) {
            totalSum = totalSum + consumption[i]; // Addition operator
        }
        double average = totalSum / consumption.length; // Division operator
        return average;
    }

    /**
     * Module 3: Highest Consumption
     * Finds and displays customer with maximum electricity consumption.
     */
    public static void findHighestConsumption(int[] customerIds, String[] customerNames, double[] consumption) {
        System.out.println("\n--- Module 3: Highest Consumption Analysis ---");
        int maxIndex = 0;
        double maxConsumption = consumption[0];

        for (int i = 1; i < consumption.length; i++) {
            if (consumption[i] > maxConsumption) {
                maxConsumption = consumption[i];
                maxIndex = i;
            }
        }

        System.out.println("Customer with Highest Consumption:");
        System.out.println("  Customer ID   : " + customerIds[maxIndex]);
        System.out.println("  Customer Name : " + customerNames[maxIndex]);
        System.out.printf("  Monthly Usage : %.2f kWh\n", maxConsumption);
    }

    /**
     * Module 4: High Usage Detection
     * Uses if-else statements to identify accounts exceeding a threshold.
     */
    public static void identifyHighUsage(int[] customerIds, String[] customerNames, double[] consumption, double threshold) {
        System.out.println("\n--- Module 4: High-Consumption Accounts (Threshold: " + threshold + " kWh) ---");
        boolean foundHighUsage = false;
        int highCount = 0;

        System.out.printf("%-12s %-20s %-18s %-12s\n", "Customer ID", "Customer Name", "Consumption (kWh)", "Status");
        System.out.println("-------------------------------------------------------------------------");

        for (int i = 0; i < consumption.length; i++) {
            // Conditional if-else statement to check high usage
            if (consumption[i] >= threshold) {
                System.out.printf("%-12d %-20s %-18.2f %-12s\n", customerIds[i], customerNames[i], consumption[i], "HIGH USAGE");
                foundHighUsage = true;
                highCount++;
            } else {
                System.out.printf("%-12d %-20s %-18.2f %-12s\n", customerIds[i], customerNames[i], consumption[i], "Normal");
            }
        }

        System.out.println("-------------------------------------------------------------------------");
        if (foundHighUsage) {
            System.out.println("Total high-consumption accounts detected: " + highCount);
        } else {
            System.out.println("No customer exceeded the threshold of " + threshold + " kWh.");
        }
    }

    /**
     * Module 5: Analysis Report
     * Displays a complete formatted summary report for all records.
     */
    public static void generateAnalysisReport(int[] customerIds, String[] customerNames, double[] consumption) {
        System.out.println("\n=========================================================================");
        System.out.println("                    ELECTRICITY ANALYSIS REPORT                          ");
        System.out.println("=========================================================================");
        System.out.printf("%-12s %-20s %-18s\n", "Customer ID", "Customer Name", "Consumption (kWh)");
        System.out.println("-------------------------------------------------------------------------");

        double totalConsumption = 0.0;
        int maxIndex = 0;

        for (int i = 0; i < customerIds.length; i++) {
            System.out.printf("%-12d %-20s %-18.2f\n", customerIds[i], customerNames[i], consumption[i]);
            totalConsumption = totalConsumption + consumption[i];

            if (consumption[i] > consumption[maxIndex]) {
                maxIndex = i;
            }
        }

        double averageConsumption = totalConsumption / customerIds.length;

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("SUMMARY METRICS:");
        System.out.println("  Total Customers Processed : " + customerIds.length);
        System.out.printf("  Total Consumption        : %.2f kWh\n", totalConsumption);
        System.out.printf("  Average Consumption      : %.2f kWh\n", averageConsumption);
        System.out.printf("  Highest Consumption      : %s (ID: %d) - %.2f kWh\n", 
                customerNames[maxIndex], customerIds[maxIndex], consumption[maxIndex]);
        System.out.println("=========================================================================");
    }

    /**
     * Searching Module: Linear search by ID or Name to locate specific records.
     */
    public static void searchCustomer(Scanner scanner, int[] customerIds, String[] customerNames, double[] consumption) {
        System.out.println("\n--- Searching: Locate Customer Record ---");
        System.out.println("1. Search by Customer ID");
        System.out.println("2. Search by Customer Name");
        System.out.print("Select search type (1-2): ");
        int searchType = getValidIntInput(scanner);

        boolean found = false;

        if (searchType == 1) {
            System.out.print("Enter Customer ID to search: ");
            int searchId = getValidIntInput(scanner);

            // Linear search algorithm using loop
            for (int i = 0; i < customerIds.length; i++) {
                if (customerIds[i] == searchId) {
                    printSearchResult(customerIds[i], customerNames[i], consumption[i]);
                    found = true;
                    break;
                }
            }
        } else if (searchType == 2) {
            scanner.nextLine(); // Consume newline
            System.out.print("Enter Customer Name to search: ");
            String searchName = scanner.nextLine().trim();

            // Linear search algorithm using loop
            for (int i = 0; i < customerNames.length; i++) {
                if (customerNames[i].equalsIgnoreCase(searchName)) {
                    printSearchResult(customerIds[i], customerNames[i], consumption[i]);
                    found = true;
                    break;
                }
            }
        } else {
            System.out.println("[Error] Invalid search option.");
            return;
        }

        if (!found) {
            System.out.println("\n[Not Found] No customer record matches your search query.");
        }
    }

    private static void printSearchResult(int id, String name, double usage) {
        System.out.println("\n[Record Found]");
        System.out.println("  Customer ID   : " + id);
        System.out.println("  Customer Name : " + name);
        System.out.printf("  Consumption   : %.2f kWh\n", usage);
    }

    /**
     * Checks if customer data has been initialized.
     */
    private static boolean isDataAvailable(int[] customerIds) {
        if (customerIds == null || customerIds.length == 0) {
            System.out.println("\n[Warning] No customer records found! Please select Option 1 to enter data first.");
            return false;
        }
        return true;
    }

    /**
     * Utility method for robust integer input parsing.
     */
    private static int getValidIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input! Please enter a valid integer: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Utility method for robust double input parsing.
     */
    private static double getValidDoubleInput(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input! Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
