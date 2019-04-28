import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Please login with your credentials to proceed");

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        //  TODO:   Login System (Censor Password on input)
        System.out.print("Password: ");
        String password = input.nextLine();

        //  TODO:   Login Verification / Input Validation
        System.out.println("    -   Login SUCCESSFUL   -\n");
        System.out.println();
        System.out.println("Welcome to the Vending Machine Portal");

        menu();
    }

    private static void menu() {
        boolean keepGoing = true;
        while(keepGoing) {
            System.out.println("------------------------------------------------------------");
            System.out.println("What would you like to do?");
            System.out.println("1. View overall Vending Machine Stats\n" +
                    "2. View Statistics for a specific Vending Machine\n" +
                    "3. List Number of Items sold from specific Vending Machine\n" +
                    "4. Modify specific Vending Machine Items\n" +
                    "5. View Past Statistics from specific Vending Machine\n" +
                    "6. Log out and exit");
            System.out.println("------------------------------------------------------------");
            System.out.print("Please enter your choice: ");
            int choice = input.nextInt();

            //  Input Validation for menu choice
            //  TODO:   Input Validation if user inputs string
            while (0 > choice || choice > 7) {
                System.out.println("Please reenter your choice: ");
                choice = input.nextInt();
            }

            switch (choice) {
                case 1:
                    System.out.println("Displaying overall Vending Machine Stats");
                    //  TODO:   View overall Vending Machine Stats
                    break;
                case 2:
                    //  TODO:   View Statistics for a specific Vending Machine
                    break;
                case 3:
                    //  TODO:   List Number of Items for specific Vending Machine
                    break;
                case 4:
                    //  TODO:   Modify specific Vending Machine Items
                    break;
                case 5:
                    //  TODO:   View past Stats from Specific Vending Machine
                    break;
                case 6:
                    //  Exit the program
                    keepGoing = false;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        }
        logout();
    }

    private static void vending_machine_menu() {
        System.out.println("Select which Vending Machine to connect to");
        System.out.println("print list of vending machine");
        System.out.print("Please enter your choice: ");
        int vm_choice = input.nextInt();

        //  Input Validation for vending machine menu
        //  TODO:   Input Validation if user inputs string

        //System.out.println("\t-   CONNECTED TO VENDING MACHINE " + machine_id + "   -");

        //return vm;
    }

    private static void logout() {
        System.out.println("\t-   Logging Off   -");
    }
    public static void viewOverallStats(ArrayList<Integer> vmIDs){
        Connection con=Database.getRemoteConnection();
        Double total=0.0;
        Integer totalNumItems=0;

//        ArrayList<Integer> vmIDs=new ArrayList<Integer>();

        for(int i=0;i<vmIDs.size();i++){
            Integer vmID=vmIDs.get(i);
            total+=Database.getTotal(con,vmID);

            totalNumItems+=Database.getTotalItems(con,vmID);
        }

        System.out.println("Total amount of money in all vending machine: "+total);
        System.out.println("Number of items sold in all vending machines: "+totalNumItems);

//        for(int x=0;x<2;x++){
//            System.out.format("Vending Machine #%d: ",x);
//            System.out.format("moneyhere");
//        }

    }


}