package DataTypes;
import Model.NozamaSystem;
import ReadWrite.Json.JSONObject;

/**
 * User class defines which type of account/user the user is based on the users input.
 * @author Jordan
 */
public class User
{
    private String userID, username, password, accountType;
    private IAccount account;

    /**
     * Constructor defines the users properties.
     * @param ID - id
     * @param username - name or email
     * @param password - password
     * @param accountType - status
     */
    public User(String ID, String username, String password, String accountType)
    {
        this.userID = ID;
        this.username = username;
        this.password = password;
        this.accountType = accountType;

        if (accountType.equals("customer"))
            account = new CustomerAccount(this);
        else if (accountType.equals("seller"))
            account = NozamaSystem.getInstance().getSeller(this.username);
    }

    /**
     * @return JSONObject representation of User
     */
    public JSONObject toJSONObject()
    {
        JSONObject data = new JSONObject();
        data.put("username", this.username);
        data.put("password", this.password);
        data.put("accountType", this.accountType);

        JSONObject header = new JSONObject();
        header.put(this.userID, data);

        return header;
    }

    /**
     * @return String representation of User
     * */
    @Override
    public String toString() {
        return "DataTypes.User{" +
                "userID='" + userID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountType='" + accountType + '\'' + '}';
    }

    /**
     * @return String representation of User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return String representation of User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return String representation of User's account
     */
    public IAccount getAccount()
    {
        return account;
    }
}
