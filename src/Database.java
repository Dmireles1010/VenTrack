import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Database {


    private static String dbURL1 = "jdbc:mysql://vmdatabase.ckyea7tyjwjn.us-west-2.rds.amazonaws.com:3306/vmdatabase";
    private static String userName = "vmdatabase";
    private static String password = "vmdatabase";


    
    /**
     * Creates a connection to the database
     * @return The connection of the database
     * **/
    public static Connection getRemoteConnection() {

        try {

            System.out.println("Getting remote connection with connection string");
            Connection con = DriverManager.getConnection(dbURL1,userName,password);
            System.out.println("Remote connection successfull\n");
            return con;
        }

        catch (SQLException ex) {System.out.println("SQLException: "+ex.getMessage());
            System.out.println("SQLState: "+ex.getSQLState()); }
        return null;


    }
    /**
     * Updates the Quantity of an Item by -1
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * **/
    public static void updateAmount(Connection con,String itemID,Integer vmID){

        //TODO: ALSO SPECIFIY THE VENDINGMACHINE ID
        //TODO: Figure out how to keep track of database to make view past products

        try{
            String query = "update ITEM set QUANTITY = ? where ITEM_ID = ? AND VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            Integer originalQuant=getAmount(con,itemID,vmID)-1;
            //Decrease Quant by 1
            //TODO: if quant is less than 0 dont decrease
            preparedStmt.setInt   (1,originalQuant);
            //ID
            preparedStmt.setString(2,itemID);
            preparedStmt.setInt(3,vmID);
            
            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Error 1");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }
  
    /**
     * Purchases an item from a vending machine and stores purchase history to database
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * @param date - the date it was purchase
     * @param purchaseiD - the id of the purchase item
     * @param price - the price the item costed
     * **/
    public static void buyItem(Connection con,String itemID,Integer vmID,Date date,Integer purchaseID,Double price) {
        try {
            String query = " insert into PURCHASE (PURCHASE_ID, DATE_OF_PURCHASE, PRICE, ITEM_ID, VENDING_MACHINE_ID)" + " values (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            //ID


            //TODO: GET THE LAST PURCHANSE ID AND INCREMENT IT BY ONE AND ADD IT TO THIS ONE
            //ALSO GET THE PRICE FROM ITEM TABLE

            preparedStmt.setInt(1,purchaseID+1);
            preparedStmt.setDate(2,(java.sql.Date) date);

            preparedStmt.setDouble(3,price);

            preparedStmt.setString(4,itemID);
            preparedStmt.setInt(5,vmID);
            preparedStmt.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println("Error 2");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

    }

    /**
     * @return the most latest purchaseID in the database
     * @param con - is the connection to Database
     * **/
    public static Integer getRecentPurchase(Connection con) {
        try {
            String query = "select * from PURCHASE";
            Statement st = con.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            Integer purchaseID=0;


            while (rs.next())
            {
                purchaseID = rs.getInt("PURCHASE_ID");


            }
            return (purchaseID);
            //PreparedStatement preparedStmt = con.prepareStatement(query);
            //ID
        }


        catch(SQLException e) {
            System.out.println("Error 9");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
        return 0;
    }

    /**
     * @return if an item exists in the database
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * **/
    public static boolean getBoolExist(Connection con,String itemID,Integer vmID) {
        try{
            String query = "SELECT * FROM ITEM WHERE ITEM_ID=? AND VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt=con.prepareStatement(query);
            preparedStmt.setString(1,itemID);
            preparedStmt.setInt(2,vmID);

            //https://stackoverflow.com/questions/4131092/mysqlsyntaxerrorexception-near-when-trying-to-execute-preparedstatement
            ResultSet rs= preparedStmt.executeQuery();

            return rs.next();


        }
        catch(SQLException e){
            System.out.println("Error 7");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
        return false;

    }

    /**
     * Deletes an item in the database
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * **/
    public static void deleteItem(Connection con,String itemID,Integer vmID) {
        try{
            String query="delete from ITEM where ITEM_ID=? and VENDING_MACHINE_ID=?";

            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setString(1,itemID);
            //ID
            preparedStmt.setInt(2,vmID);

            System.out.println("Updated ID:"+itemID+",Quantitiy:"+getAmount(con,itemID,vmID));

        }
        catch(SQLException e){
            System.out.println("Error 3");
        }
    }

    /**
     * Adds an item to the database
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * @param name - the name of the item
     * @param price - the cost of item
     * @param quant - the number of quantity
     * @param pCompany - the name of the production company 
     * **/
    public static void addItem(Connection con, String itemID,Integer vmID, String name ,Double price,Integer quant,String pCompany) {
        try{
            String query = " insert into ITEM (ITEM_ID, NAME, PRODUCTION_COMPANY, PRICE, QUANTITY,VENDING_MACHINE_ID)" + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            //ID
            preparedStmt.setString   (1,itemID);

            preparedStmt.setString(2,name);
            preparedStmt.setString(3,pCompany);
            preparedStmt.setDouble(4,price);
            preparedStmt.setInt(5,quant);
            preparedStmt.setInt(6,vmID);
            System.out.println("Added ID:"+itemID+",name:"+name+", to vendingmachineID:"+vmID);

        }
        catch(SQLException e){
            System.out.println("Error 4");
        }
    }
    /**
     * Updates the price of an item 
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * @param price - the price you would like to item to cost
     * **/
    public static void updateItemPrice(Connection con,String itemID,Integer vmID,Double price) {
        try{
            String query = "update ITEM set PRICE = ? where ITEM_ID = ? AND VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setDouble   (1,price);
            //ID
            preparedStmt.setString(2,itemID);
            preparedStmt.setInt(3,vmID);

            System.out.println("Updated ID:"+itemID+",Quantitiy:"+getAmount(con,itemID,vmID));

        }
        catch(SQLException e){
            System.out.println("Error 5");
        }
    }
    /**
     * Updates the quantity of an item 
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * @param quant - the amount of quantity you want the item to have
     * **/
    public static void updateQuantity(Connection con,String itemID,Integer vmID,Integer quant) {
        try{
            String query = "update ITEM set QUANTITY = ? where ITEM_ID = ? AND VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            //Decrease Quant by 1
            //TODO: if quant is less than 0 dont decrease
            preparedStmt.setInt   (1,quant);
            //ID
            preparedStmt.setString(2,itemID);
            preparedStmt.setInt(3,vmID);
            preparedStmt.executeUpdate();
            System.out.println("Updated ID:"+itemID+",Quantitiy:"+getAmount(con,itemID,vmID));

        }
        catch(SQLException e){
            System.out.println("Error 6");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }
    /**
     * @return the price of an item
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * **/
    public static Double getPrice(Connection con,String itemID,Integer vmID) {
        try{
            String query = "SELECT PRICE FROM ITEM WHERE ITEM_ID=? AND VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt=con.prepareStatement(query);
            preparedStmt.setString(1,itemID);
            preparedStmt.setInt(2,vmID);

            //https://stackoverflow.com/questions/4131092/mysqlsyntaxerrorexception-near-when-trying-to-execute-preparedstatement
            ResultSet rs= preparedStmt.executeQuery();
            Double price=0.0;
            while(rs.next()){
                price=rs.getDouble("PRICE");
            }
            return price;

        }
        catch(SQLException e){
            System.out.println("Error 7");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
        return 0.0;


    }
    
    /**
     * @return the name of an item
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * **/
    public static String getItemName(Connection con,String itemID,Integer vmID) {
        try{
            String query = "SELECT * FROM ITEM WHERE ITEM_ID= ? AND VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt=con.prepareStatement(query);
            preparedStmt.setString(1,itemID);
            preparedStmt.setInt(2,vmID);
            ResultSet rs= preparedStmt.executeQuery();
            String name="";
            while(rs.next()){
                name=rs.getString("NAME");
            }
            return name;
        }
        catch(SQLException e){
            System.out.println("Error 8");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
        return "";


    }
    
    /**
     * @return the quantity of an item
     * @param con - is the connection to Database
     * @param itemID - String of the ID of item
     * @param vmID - Integer of the ID of the vending machine
     * **/
    public static int getAmount(Connection con,String itemID,Integer vmID){
        try{
            String query = "SELECT * FROM ITEM WHERE ITEM_ID= ? AND VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt=con.prepareStatement(query);
            preparedStmt.setString(1,itemID);
            preparedStmt.setInt(2,vmID);
            ResultSet rs= preparedStmt.executeQuery();
            Integer quant=0;
            while(rs.next()){
                quant=rs.getInt("QUANTITY");
            }
            return quant;
        }
        catch(SQLException e){
            System.out.println("Error 8");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

        return 0;
    }

    /**
     * @return the total in a vending machine
     * @param con - is the connection to Database
     * @param vmID - Integer of the ID of the vending machine
     * **/
    public static Double getTotal(Connection con,Integer vmID) {
        try{
            String query = "SELECT * FROM VENDING_MACHINE WHERE VENDING_MACHINE_ID= ? ";
            PreparedStatement preparedStmt=con.prepareStatement(query);
            preparedStmt.setInt(1,vmID);

            ResultSet rs= preparedStmt.executeQuery();
            Double total=0.0;
            while(rs.next()){
                total=rs.getDouble("TOTAL_MONEY");
            }
            return total;
        }
        catch(SQLException e){
            System.out.println("Error 8");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

        return 0.0;
    }

    /**
     * Increases the amount of total in the vending machine
     * @param con - is the connection to Database
     * @param vmID - Integer of the ID of the vending machine
     * @param amount - the amount of money to be added onto the vending machine
     * **/
    public static void incTotal(Connection con,Integer vmID,Double amount) {
        try{
            String query = "update VENDING_MACHINE set TOTAL_MONEY = ? where VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

//            Double originalTotal=getTotal(con,vmID);

            Double originalTotal=Double.parseDouble(String.format("%.2f", getTotal(con,vmID)));
            
//            System.out.println(originalTotal);
            
            //Decrease Quant by 1
            //TODO: if quant is less than 0 dont decrease

            Double newTotal=originalTotal+amount;
            
            Double newTotalFormatted=Double.parseDouble(String.format("%.2f", newTotal));
//            System.out.println(newTotalFormatted);
            preparedStmt.setDouble(1,newTotalFormatted);
            //ID
            preparedStmt.setInt(2,vmID);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Error 11");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }
 
    /**
     * Print all the items in a vending machine
     * @param con - is the connection to Database
     * @param vmID - Integer of the ID of the vending machine
     * **/
    public static void showItems(Connection con,Integer vmID) {
        try{
            String query = "select * from ITEM where VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,vmID);



            // execute the java preparedstatement
            ResultSet rs= preparedStmt.executeQuery();
            
            //System.out.println("ITEM_ID| NAME | PRODUCTION_COMPANY | PRICE | QUANTITY | VENDING_MACHINE_ID");
            System.out.println("Items:\n");
            System.out.format("%-7s | %-21s | %-21s | %-5s | %-8s | %-18s\n", "ITEM_ID","NAME","PRODUCTION_COMPANY","PRICE","QUANTITY","VENDING_MACHINE_ID");
            System.out.format("-----------------------------------------------------------------------------------------------\n");
            while (rs.next())
            {

              String itemID = rs.getString("ITEM_ID");
//              String vmID = rs.getString("first_name");
              String name =  rs.getString("NAME");
              Double price = rs.getDouble("PRICE");
              Integer quant =rs.getInt("QUANTITY");
              String pCompany=rs.getString("PRODUCTION_COMPANY");
              
              // print the results
              System.out.format("%-7s | %-21s | %-21s | %-5.2f | %-8d | %-18d\n", itemID, name, pCompany, price, quant, vmID);
            }
            System.out.println();
        }
        catch(SQLException e){
            System.out.println("Error 11");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }
    
    /**
     * Print all the items by Start date and End Date
     * @param con - is the connection to Database
     * @param vmID - Integer of the ID of the vending machine
     * @param startDate - String of the start date must be formatted as (yyyy-M-dd)
     * @param endDate - String of the end date must be formatted as (yyyy-M-dd)
     * **/
    public static void showItemsByDate(Connection con,Integer vmID, String startDate, String endDate) {
        try{
        	
        	
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-M-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-M-dd");
            

        	Date startDateAct = sdf1.parse(startDate);
        	Date endDateAct=sdf2.parse(endDate);
        	
        	
            String query = "select * from PURCHASE where VENDING_MACHINE_ID=? AND DATE_OF_PURCHASE between ? and ?";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            //return results

            preparedStmt.setInt(1,vmID);
            preparedStmt.setDate(2, new java.sql.Date(startDateAct.getTime()));
            preparedStmt.setDate(3, new java.sql.Date(endDateAct.getTime()));
        	

            // execute the java preparedstatement
            ResultSet rs= preparedStmt.executeQuery();

            System.out.println();
            System.out.format("PURCHASES BETWEEN %s and %s:\n\n",startDateAct.toString().substring(4, 10)+" "+startDateAct.toString().substring(24, 28),endDateAct.toString().substring(4, 10)+" "+endDateAct.toString().substring(24, 28));

            System.out.format("%-11s | %-16s | %-5s | %-7s | %-18s \n", "PURCHASE_ID","DATE_OF_PURCHASE","PRICE","ITEM_ID","VENDING_MACHINE_ID");
            System.out.format("-----------------------------------------------------------------------------------------------\n");
            while (rs.next())
            {

              Integer pID = rs.getInt("PURCHASE_ID");

              Date pDate =  rs.getDate("DATE_OF_PURCHASE");
              Double price = rs.getDouble("PRICE");
              String itemID =rs.getString("ITEM_ID");
//              String vmID=rs.getString("VENDING_MACHINE_ID");
              
              // print the results
              System.out.format("%-11d | %-16s | %-5.2f | %-7s | %-18d \n", pID, pDate.toString(), price, itemID, vmID);
            }
        }
        catch(SQLException e){
            System.out.println("Error 11");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
}