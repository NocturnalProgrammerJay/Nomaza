package DataTypes;

import NozamaGui.Screens.SellerDashboard;
import ReadWrite.Json.JSONObject;

import java.util.Objects;

/**
 * Represents a Seller owned account. Implements IAccount
 * @author Jordan Diaz
 */
public class SellerAccount implements IAccount{

    /**
     * Constructor: defines the sellers account properties
     * @param username - name
     * @param profit - earnings
     * @param revenues - summations of all sold items
     * @param costs -  summations of invoice prices for all items
     */
    public SellerAccount(String username, float profit, float revenues, float costs)
    {
        this.userName = username;
        this.profit = profit;
        this.revenues = revenues;
        this.costs = costs;
    }

    /**
     * Changes pages to Seller Dashboard
     */
    @Override
    public void OnLogIn()
    {
        System.out.println("A seller has logged in");
        SellerDashboard sellerDashboard = new SellerDashboard(this);
    }

    /**
     * @return JSONObject representation of SellerAccount
     */
    public JSONObject toJSONObject()
    {
        JSONObject data = new JSONObject();
        data.put("profit", String.valueOf(this.profit));
        data.put("revenues", String.valueOf(this.revenues));
        data.put("costs", String.valueOf(this.costs));

        JSONObject header = new JSONObject();
        header.put(this.userName, data);

        return header;
    }

    /**
     * @return String representation of Item's username
     */
    public String getUserName(){
        return userName;
    }

    /**
     * @return String representation of instance of SellerAccount
     */
    @Override
    public String toString() {
        return "SellerAccount{" +
                "userName='" + userName + '\'' +
                ", profit=" + profit +
                ", revenues=" + revenues +
                ", costs=" + costs + '}';
    }

    /**
     * @return float representation of SellerAccount's profit
     */
    public float getProfit() {
        return profit;
    }

    /**
     * @return float representation of SellerAccount's Revenues
     */
    public float getRevenues() {
        return revenues;
    }

    /**
     * @return float representation fo SellerAccount's Costs
     */
    public float getCosts() {
        return costs;
    }

    /**
     * Object override comparison method, object that calls this method is testing if the same as object that's passed as argument.
     * @param o - Object passed by argument
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellerAccount that)) return false;
        return Float.compare(that.profit, profit) == 0 && Float.compare(that.revenues, revenues) == 0 && Float.compare(that.costs, costs) == 0 && Objects.equals(userName, that.userName);
    }

    /**
     * Gets sellers hexadecimal memory address for each of it's properties
     * @return hexadecimal digits
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName, profit, revenues, costs);
    }

    /**
     * Adds "addThis" to SellerAccount's Revenues
     * @param addThis
     */
    public void addToRevenues(float addThis)
    {
        this.revenues += addThis;
    }

    /**
     * Adds "addThis" to SellerAccount's Costs
     * @param addThis
     */
    public void addToCosts(float addThis)
    {
        this.costs += addThis;
    }

    /**
     * Calculates the profit of a SellerAccount
     */
    public void calculateProfit()
    {
        this.profit = this.revenues - this.costs;
    }

    private String userName;
    private float profit;
    private float revenues;
    private float costs;
}

