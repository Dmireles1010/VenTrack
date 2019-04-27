import java.sql.*;
import java.util.Date;

public class Database {





    private static String dbURL1 = "jdbc:mysql://vmdatabase.ckyea7tyjwjn.us-west-2.rds.amazonaws.com:3306/vmdatabase";
    private static String userName = "vmdatabase";
    private static String password = "vmdatabase";


    public static void main(String args[]) {



        try {
            Connection con=getRemoteConnection();
            
            //getting price
            //System.out.println(getPrice( con, "F4", 1));
            
            //when updating an item quantity by -1
            //updateAmount(con,"F4",1);
            
            //when updating the quantity
//            updateQuantity( con, "F4", 1, 3);
            
            //when buying item
//            Date date=new Date();
//            buyItem( con, "A2", 1, new java.sql.Date(date.getTime()),getRecentPurchase(con),getPrice( con, "A2", 1));
            
 
            
            
//            System.out.println( getRecentPurchase( con));

            
			con.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}

    }
    public static Connection getRemoteConnection() {

        try {

            System.out.println("Getting remote connection with connection string");
            Connection con = DriverManager.getConnection(dbURL1,userName,password);
            System.out.println("Remote connection successfull");
            return con;
        }

        catch (SQLException ex) {System.out.println("SQLException: "+ex.getMessage());
            System.out.println("SQLState: "+ex.getSQLState()); }
		return null;


    }

    
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

            System.out.println("Updated ID:"+itemID+",Quantitiy:"+originalQuant);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Error 1");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }
    }
    
    public static void buyItem(Connection con,String itemID,Integer vmID,Date date,Integer purchaseID,Double price) {
    	try {
        	String query = " insert into PURCHASE (PURCHASE_ID, DATE_OF_PURCHASE, PRICE, ITEM_ID, VENDING_MACHINE_ID)"
        	        + " values (?, ?, ?, ?, ?)";
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
    
    public static void addItem(Connection con, String itemID,Integer vmID, String name ,Double price,Integer quant,String pCompany) {
        try{
        	String query = " insert into ITEM (ITEM_ID, NAME, PRODUCTION_COMPANY, PRICE, QUANTITY,VENDING_MACHINE_ID)"
        	        + " values (?, ?, ?, ?, ?, ?)";
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

    public static Double getPrice(Connection con,String itemID,Integer vmID) {
        try{
            String query = "SELECT PRICE FROM ITEM WHERE ITEM_ID=? AND VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt=con.prepareStatement(query);
            preparedStmt.setString(1,itemID);
            preparedStmt.setInt(2,vmID);

            //https://stackoverflow.com/questions/4131092/mysqlsyntaxerrorexception-near-when-trying-to-execute-preparedstatement
            ResultSet rs= preparedStmt.executeQuery();
            double price=0;
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
   
    public static double getTotal(Connection con,Integer vmID) {
        try{
            String query = "SELECT * FROM VENDING_MACHINE WHERE VENDING_MACHINE_ID= ? ";
            PreparedStatement preparedStmt=con.prepareStatement(query);
            preparedStmt.setInt(1,vmID);

            ResultSet rs= preparedStmt.executeQuery();
            Integer total=0;
            while(rs.next()){
            	total=rs.getInt("TOTAL_MONEY");
            }
            return total;
        }
        catch(SQLException e){
            System.out.println("Error 8");
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
        }

        return 0;
    }
    
    public static void incTotal(Connection con,Integer vmID,Double amount) {
        try{
            String query = "update VENDING_MACHINE set TOTAL_MONEY = ? where VENDING_MACHINE_ID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            
            double originalTotal=getTotal(con,vmID);
            //Decrease Quant by 1
            //TODO: if quant is less than 0 dont decrease
            
            double newTotal=originalTotal+amount;
            
            preparedStmt.setDouble(1,newTotal);
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
}