import java.util.ArrayList;

public class Customer {

    private String customerName;
    private ArrayList<String> companyAddress;

    public Customer() {
    	
    }
    public Customer(String customerName, ArrayList<String> companyAddress) {
        this.customerName = customerName;
        this.companyAddress = companyAddress;
    }
    public Customer(String customerName, ArrayList<String> companyAddress, String companyPhone) {
        this.customerName = customerName;
        this.companyAddress = companyAddress;
    }
    
    public void setCustomer(String customerName,String companyAddress,String companyPhone) {
    	
    }
    
    public void getInfo() {
    	System.out.println("Customer Name: "+customerName);
    	System.out.println("Your Vending Machine Addresses: "+companyAddress.toString());
    }
}
