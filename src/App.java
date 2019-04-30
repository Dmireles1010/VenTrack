import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static final Scanner input = new Scanner(System.in);
    public static Connection con = Database.getRemoteConnection();
    public static User currentUser=new User();
    final static String DATE_FORMAT = "yyyy-MM-dd";
    
    
    public static Customer customer=new Customer();
    
    public static ArrayList<Integer> vmIDs=null;
    
    
    
    public static void main(String[] args) {

        Connection con = Database.getRemoteConnection();
        System.out.println("Please login with your credentials to proceed");
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        boolean accept = Database.getBoolAccount(con, username, password);

        
        while (!accept){
            System.out.print("\nERROR: Your username and / or password is incorrect\n" +
                            "Enter your username: ");
            username = input.nextLine();
            System.out.print("Password: ");
            password = input.nextLine();
            accept = Database.getBoolAccount(con, username, password);
            currentUser.setUserName(username);
        }
        
        currentUser.setUserName(username);
        vmIDs=Database.getVMs(con,currentUser.getUserName());
        //Set customer info
        //****************TRY System.out.println(customer.getInfo())  to see the users info
        customer=Database.getAccountInfo(con, username);
        System.out.println("\nWELCOME TO VENTRACK\n");
        customer.getInfo();
        System.out.println("Your Vending Machine IDs:"+vmIDs.toString());
        System.out.println();
        
        login();
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

            //  Input Validation for menu choices
            while (0 > choice || choice > 7) {
                System.out.println("Please reenter your choice: ");
                choice = input.nextInt();
            }
//            ArrayList<Integer> vmIDs=Database.getVMs(con,currentUser.getUserName());
            Integer id=null;
            switch (choice) {
                // Menu choices
                case 1:
                    System.out.println("Displaying overall Vending Machine Stats");
                    //View overall Vending Machine Stats
                    viewOverallStats(vmIDs);
                    break;
                case 2:
                    //View Statistics for a specific Vending Machine
                    System.out.println("Your Vending Machines IDs:");
                    for(Integer vm:vmIDs){
                        System.out.println("Vending Machine ID: "+vm);
                    }
                    System.out.print("Enter vending machine ID: ");
                    id = input.nextInt();
                    while(!vmIDs.contains(id)){
                        System.out.println("Invalid Vending Machine number... Enter a valid vending machine ID:");
                        id = input.nextInt();
                    }
                    Database.showItemsStatsSpecific(con,id);
                    break;
                case 3:
                    //List Number of Items for specific Vending Machine

                    System.out.println("Your Vending Machines IDs:");
                    for(Integer vm:vmIDs){
                        System.out.println("Vending Machine ID: "+vm);
                    }

                    System.out.print("Enter vending machine ID: ");
                    id = input.nextInt();
                    while(!vmIDs.contains(id)){
                        System.out.println("Invalid Vending Machine number... Enter a valid vending machine ID:");
                        id = input.nextInt();
                    }

                    Database.showItemsSpecific(con,id);
                    break;
                case 4:
                    //Modify specific Vending Machine Items
                    Database.modifyItems(con);
                    break;

                case 5:
                    //View past Stats from Specific Vending Machine
                    System.out.print("Enter vending machine ID: ");
                    int vmID = input.nextInt();
                    System.out.print("Input start date 'yyyy-MM-dd': ");
                    String startDate = input.next();
                    boolean s = isDateValid(startDate);
                    while (!s){
                        System.out.print("Please enter start date again 'yyyy-MM-dd': ");
                        startDate = input.next();
                        s = isDateValid(startDate);
                    }
                    System.out.print("Input end date 'yyyy-MM-dd': ");
                    String endDate = input.next();
                    s = isDateValid(endDate);
                    while (!s){
                        System.out.print("Please enter end date again 'yyyy-MM-dd': ");
                        startDate = input.next();
                        s = isDateValid(startDate);
                    }
                    Database.showItemsByDate(con, vmID, startDate, endDate);
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


    private static void logout() {
        System.out.println("\t-   Logging Off   -");
    }
    private static void login() {
        System.out.println("\t-   Logging In   -\n");
    }


    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static void viewOverallStats(ArrayList<Integer> vmIDs){
        Double total=0.0;
        Integer totalNumItems=0;

        for(int i=0;i<vmIDs.size();i++){
            Integer vmID=vmIDs.get(i);
            total+=Database.getTotal(con,vmID);

            totalNumItems+=Database.getTotalItems(con,vmID);
        }

        System.out.format("Total amount of money in all vending machine: $%.2f %n",total);
        System.out.println("Number of items sold in all vending machines: "+totalNumItems);
        Database.showItemsStats(con,vmIDs);
        ;
        System.out.println();
//        }

    }


}