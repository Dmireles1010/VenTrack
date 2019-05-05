public class User {

    private String userName;
    private String userID;
    private String userTitle;
    //company and username
    public User(){

    }
    public User(String userName){
        this.userName=userName;
    }
    public User(String userName, String userID, String userTitle) {
        this.userName = userName;
        this.userID = userID;
        this.userTitle = userTitle;
    }
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getUserName(){
        return userName;
    }
}