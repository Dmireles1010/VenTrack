import java.lang.String;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class VendingMachine {
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Connection con=null;
        //Connect to database:
        try {
            con=Database.getRemoteConnection();

            System.out.print("Enter which Vending Machine you are at: ");
            int vmID = input.nextInt();

            Database.showItems(con,vmID);

            System.out.print("Enter the code correlating to the item you want: ");
            String choice = input.next();

            String itemID=null;
            String inputValidate = "False";
 
            while(inputValidate == "False") {
                itemID=choice;
                boolean itemExist=Database.getBoolExist(con, choice, vmID);
                if (itemExist == true) {
                    inputValidate = "True";
                }
                else {
                    // Key might be present...
                    if (itemExist=Database.getBoolExist(con, choice, vmID)) {
                        System.out.println("There is none of this item available.");
                    } else {
                        System.out.print("Please enter a valid code: ");
                        choice = input.next();
                    }
                }
            }

            String itemName = Database.getItemName(con, itemID, vmID);

            double cost = Database.getPrice(con, itemID, vmID);
            System.out.println( itemName + " costs $" + cost);

            System.out.print("Enter your money: ");
            double money = input.nextDouble();
            inputValidate = "False";
            while(inputValidate == "False") {
                if(money < 0) {
                    System.out.print("Please enter a valid amount: ");
                    money = input.nextDouble();
                }
                else {
                    inputValidate = "True";
                }
            }

            if(money < cost) {
                System.out.println("NOT ENOUGH MONEY");
                System.out.println("You only have $" + money);
            }
            else {
                System.out.println(itemName + " dispended.");
                Database.updateAmount(con, itemID, vmID);
                //Update total in vending machine
                Database.incTotal(con, vmID, cost);
                //Add item to purchase history
                Date date=new Date();
                Database.buyItem( con, itemID, vmID, new java.sql.Date(date.getTime()),Database.getRecentPurchase(con),Database.getPrice( con, itemID, vmID));
                
            }
            con.close();
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }

}