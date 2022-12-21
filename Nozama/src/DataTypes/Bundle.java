package DataTypes;
import Model.NozamaSystem;
import ReadWrite.Json.JSONArray;
import ReadWrite.Json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a container for multiple instances of class Item. Implements IItem interface
 * @author Jamar
 */
public class Bundle implements IItem{

    /**
     * Constructor: Constructs Bundle with variable number of instances from class Item
     * @precondition items != null
     * @param bundleID ID
     * @param bundleName Name
     * @param vendorUsername Vendor Username
     * @param quantity Quantity of Bundle in inventory
     * @param items Collection of instances of class Item
     */
    public Bundle(String bundleID, String bundleName, String vendorUsername, String quantity,Item ... items)
    {
        this.bundleID = bundleID;
        this.bundleName = bundleName;
        this.vendorUsername = vendorUsername;
        this.quantity = quantity;

        for (Item item : items)
            itemList.add(item);

    }

    /**
     * Constructor: Constructs Bundle with no instances of class Item on construction
     * @param bundleID ID
     * @param bundleName Name
     * @param vendorUsername Vendor Username
     * @param quantity Quantity of Bundle in inventory
     */
    public Bundle(String bundleID, String bundleName, String vendorUsername, String quantity)
    {
        this.bundleID = bundleID;
        this.bundleName = bundleName;
        this.vendorUsername = vendorUsername;
        this.quantity = quantity;
    }

    /**
     * @return String representation of Bundle class instance
     */
    @Override
    public String toString()
    {
        String output = "Bundle: " + bundleName + "\n" +
                        "Vendor: " + vendorUsername + "\n" +
                        "Bundled Price: $" + String.format("%.2f", Float.parseFloat(getSellPrice())) + "\n" +
                        "Quantity of Bundled Item: " + quantity + "\n\n";

        for (Item item : itemList)
        {
            output +=   "Item: " + item.getName() + "\n" +
                        "Price: $" + item.getSellPrice() + " \n" +
                        "Description: " + item.getDescription() + " \n" +
                        "Quantity: " + item.getQuantity() + " \n\n";
        }
        return output;
    }

    /**
     * Calculates sell price of Bundle instance
     * @return String representation of sell price of Bundle instance
     */
    public String getSellPrice()
    {
        float sum = 0;
        for (Item item : itemList)
            sum += Float.parseFloat(item.getSellPrice()) * item.getQuantity();

        return Float.toString(sum);
    }

    /**
     * @return True, this class is a bundle
     */
    @Override
    public boolean isBundle() {
        return true;
    }

    /**
     * @return Integer representation of the quantity of Bundle in inventory
     */
    @Override
    public int getQuantity() {
        return Integer.parseInt(this.quantity);
    }

    /**
     * @return String representation of bundle description
     */
    @Override
    public SellerAccount getVendor() {
        return NozamaSystem.getInstance().getSeller(vendorUsername);
    }

    /**
     * @return String representation of bundle's name
     */
    @Override
    public String getName() {
        return bundleName;
    }


    /**
     * @return JSONObject representation of bundle
     */
    @Override
    public JSONObject toJSONObject() {
        JSONObject data = new JSONObject();
        data.put("bundleName", this.bundleName);
        data.put("vendor", this.vendorUsername);
        data.put("quantity", this.quantity);

        JSONArray itemArray = new JSONArray();
        for (Item item : itemList)
        {
            JSONObject temp = new JSONObject();
            temp.put("name", item.getName());
            temp.put("invoice_price", item.getInvoicePrice());
            temp.put("sell_price", item.getSellPrice());
            temp.put("description", item.getDescription());
            temp.put("quantity", String.valueOf(item.getQuantity()));

            JSONObject tempHeader = new JSONObject();
            tempHeader.put(item.getID(), temp);

            itemArray.add(tempHeader);
        }

        data.put("items", itemArray);

        JSONObject header = new JSONObject();
        header.put(this.bundleID, data);

        return header;
    }

    /**
     * Appends an instance of class Item to bundle
     */
    public void addItem(Item item)
    {
        itemList.add(item);
    }

    /**
     * @return ArrayList<Item> which represents the items this bundle
     */
    public ArrayList<Item> getItemList() {return itemList;}

    /**
     * Object override comparison method, object that calls this method is testing if the same as object that's passed as argument.
     * @param o - Object passed by argument
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bundle bundle)) return false;
        return Objects.equals(itemList, bundle.itemList) && Objects.equals(vendorUsername, bundle.vendorUsername) && Objects.equals(quantity, bundle.quantity) && Objects.equals(bundleName, bundle.bundleName);
    }

    /**
     * Gets a bundle's hexadecimal memory address for each of it's properties
     * @return hexadecimal digits
     */
    @Override
    public int hashCode() {
        return Objects.hash(itemList, vendorUsername, quantity, bundleName);
    }

    /**
     * @return bundle ID
     */
    public String getID()
    {
        return itemList.get(itemList.size() - 1).getID();
    }

    /**
     * changes the value of quantity
     * @param value String representation of value
     */
    public void setQuantity(String value)
    {
        this.quantity = value;
    }

    private ArrayList<Item> itemList = new ArrayList<>();
    private String vendorUsername;
    private String quantity;
    private String bundleName;
    private String bundleID;
}
