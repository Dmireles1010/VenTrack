import java.util.ArrayList;

public class Customer {

    private String customerName;
    private ArrayList<String> companyAddress;
    private String companyPhone;

    public Customer() {
    	
    }
    public Customer(String customerName, ArrayList<String> companyAddress) {
        this.customerName = customerName;
        this.companyAddress = companyAddress;
    }
    public Customer(String customerName, ArrayList<String> companyAddress, String companyPhone) {
        this.customerName = customerName;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
    }
    
    public void setCustomer(String customerName,String companyAddress,String companyPhone) {
    	
    }
    
    public void getInfo() {
    	System.out.println("Customer: "+customerName);
    	System.out.println("Address: "+companyAddress.toString());
    	System.out.println("Phone: "+companyPhone);
    }
}
