import java.util.Scanner;

public class main {
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Please login with your credentials to proceed");

        System.out.print("Enter your username: ");
        String username = input.nextLine();
        System.out.println("Your username is " + username);

        //  TODO:   Login System (Censor Password on input)
        System.out.print("Password: ");
        String password = input.nextLine();

        //  TODO:   Login Verification / Input Validation
        System.out.println("    -   Login SUCCESSFUL   -\n");
        System.out.println("Welcome to the Vending Machine Portal");

        menu();
    }

    private static void menu() {
        System.out.println("What would you like to do?");
        System.out.println("1. View overall Vending Machine Stats\n" +
                "2. View Statistics for a specific Vending Machine\n" +
                "3. Log out and exit");
        System.out.println("Please enter your choice: ");
        int choice = input.nextInt();

        //  Input Validation for menu choice
        //  TODO:   Input Validation if user inputs string
        while (0 > choice || choice > 3) {
            System.out.println("Please reenter your choice: ");
            choice = input.nextInt();
        }

        switch (choice) {
            case 1:
                System.out.println("Displaying overall Vending Machine Stats");
                //  TODO:   View overall Vending Machine Stats
                break;
            case 2:
                System.out.print("Enter the ID of the vending machine: ");
                int machine_id = input.nextInt();
                System.out.println("\t-   ESTABLISHING A CONNECTION WITH MACHINE " + machine_id + "   -");
                vending_machine_menu(machine_id);
                //  TODO:   View Statistics for a specific Vending Machine
                break;
            case 3:
                //  Exit the program
                logout();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
    }

    private static void vending_machine_menu(int machine_id) {
        System.out.println("\t-   CONNECTED TO VENDING MACHINE " + machine_id + "   -");
        System.out.println("What would you like to do with this vending machine");
        System.out.println("1. Check Statistics\n" +
                "2. Check Inventory\n" +
                "3. Modify Vending Machine Items\n" +
                "4. Go Back to the main menu\n");
        System.out.println("Please enter your choice: ");
        int vm_choice = input.nextInt();

        //  Input Validation for vending machine menu
        //  TODO:   Input Validation if user inputs string
        while (0 > vm_choice || vm_choice > 4) {
            System.out.println("Please reenter your choice: ");
            vm_choice = input.nextInt();
        }

        switch (vm_choice) {
            case 1:
                System.out.println("Displaying Statistics");
                //  TODO:   Display Vending Machine Stats
                break;
            case 2:
                System.out.println("Displaying the inventory");
                //  TODO:   Display the inventory
                break;
            case 3:
                System.out.println("Which item would you like to modify");
                //  TODO:   Modify the inventory
                break;
            case 4:
                System.out.println("Going back to the main menu...");
                menu();
            default:
                throw new IllegalStateException("Unexpected value: " + vm_choice);
        }
    }

    private static void logout() {
        System.out.println("\t-   Logging Off   -");
    }
}
