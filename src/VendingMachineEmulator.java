import java.util.Scanner;
import java.lang.String;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class VendingMachineEmulator {
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
//        Map<String, Item> itemMap = new HashMap<String, Item>();
//
//        addItem(itemMap);
//        printItem(itemMap);

        
        //getting price
        //System.out.println(getPrice( con, "F4", 1));
        
        //when updating an item quantity by -1
        //updateAmount(con,"F4",1);
        
        //when updating the quantity
//        updateQuantity( con, "F4", 1, 3);
        
        //when buying item
//        Date date=new Date();
//        buyItem( con, "A2", 1, new java.sql.Date(date.getTime()),getRecentPurchase(con),getPrice( con, "A2", 1));

//        System.out.println( getRecentPurchase( con));

		
    	
    	
    	Connection con=null;
    	Integer vmID=null;
    	//Connect to database:
        try {
            con=Database.getRemoteConnection();
            vmID=1;
            
            System.out.print("Enter the code correlating to the item you want: ");
            String choice = input.next();

//            Item item = null;
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

            double total = 0;   //get total from database
            total=Database.getTotal( con, vmID);
            if(money < cost) {
                System.out.println("NOT ENOUGH MONEY");
                System.out.println("You only have $" + money);
            }
            else {
                System.out.println(itemName + " dispended.");
                Database.updateAmount(con, itemID, vmID);
//                item.quantity = item.quantity-1;
//                itemMap.replace(choice, item);
//                total += cost;
                Database.incTotal(con, vmID, cost);
            }
//            printItem(itemMap);
            con.close();
		} 
        catch (SQLException e) {	
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
		}

    	
 
        
        

			

    }

    private static void addItem(Map<String,Item> itemMap) {
        Item item1 = new Item("Candy",2.50,2);
        Item item2 = new Item("Chips",3.00,6);
        Item item3 = new Item("Drink",1.00,5);
        Item item4 = new Item("Apple",2.00,7);
        Item item5 = new Item("Orange",2.00,3);

        itemMap.put("A1",item1);
        itemMap.put("A2",item2);
        itemMap.put("A3",item3);
        itemMap.put("A4",item4);
        itemMap.put("A5",item5);
    }

    private static void printItem(Map<String, Item> itemMap) {
        System.out.println("-------------------------------Item List-------------------------------");

        System.out.print(String.format("%-20s", "Key"));
        System.out.print(String.format("%-20s", "Name"));
        System.out.print(String.format("%-20s", "Price"));
        System.out.print(String.format("%-20s", "Quantity"));
        System.out.println();

        for(Map.Entry<String, Item> entry:itemMap.entrySet()) {
            String key = entry.getKey();
            Item item = entry.getValue();
            System.out.print(String.format("%-20s", key));
            System.out.print(String.format("%-20s", item.itemName));
            System.out.print(String.format("%-20s", item.price));
            System.out.print(String.format("%-20s", item.quantity));
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------");
    }

}
