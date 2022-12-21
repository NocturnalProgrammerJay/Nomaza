package DataTypes;

import Model.NozamaSystem;
import ReadWrite.Json.JSONObject;
import java.util.Objects;

/**
 * Represents an Item. Implements IItem interface
 * @author Jordan
 */
public class Item implements IItem
{
    private String ID, name, invoicePrice, sellPrice, description, quantity, vendor;

    /**
     *  Constructor: Item parametrized Constructor defines an Item object properties.
     * @param id - item barcode
     * @param name - item name
     * @param invoicePrice - item price
     * @param sellPrice - item selling price
     * @param description - item details
     * @param quantity - item quantity in stock
     * @param vendor - item manufacturer
     */
    public Item(String id, String name, String invoicePrice, String sellPrice, String description, String quantity, String vendor)
    {
        this.ID = id;
        this.name = name;
        this.invoicePrice = invoicePrice;
        this.sellPrice = sellPrice;
        this.description = description;
        this.quantity = quantity;
        this.vendor = vendor;
    }

    /**
     * @return String representation of instance of class Item
     */
    @Override
    public String toString() {
        return "Item: " + name + " \n" +
                "Price: $" + sellPrice + " \n" +
                "Description: " + description + " \n" +
                "Quantity: " + quantity + " \n" +
                "Vendor: " + vendor + " \n";
    }

    /**
     * @return String representation of Item's ID
     */
    public String getID(){
        return this.ID;
    }

    /**
     * @return String representation of Item's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return JSONObject representation of instance of class Item
     */
    @Override
    public JSONObject toJSONObject() {
        JSONObject data = new JSONObject();
        data.put("name", this.name);
        data.put("invoice_price", this.invoicePrice);
        data.put("sell_price", this.sellPrice);
        data.put("description", this.description);
        data.put("quantity", this.quantity);
        data.put("vendor", this.vendor);

        JSONObject header = new JSONObject();
        header.put(this.ID, data);

        return  header;
    }

    /**
     * @return String representation of Item's sell price
     */
    public String getSellPrice(){
        return this.sellPrice;
    }

    /**
     * @return false, an Item is not a Bundle
     */
    @Override
    public boolean isBundle() {
        return false;
    }

    /**
     * @return Integer representation of the quantity of this item in stock
     */
    public int getQuantity() { return Integer.parseInt(this.quantity); }

    /**
     * @return SellerAccount representation of the vendor who sells this Item
     */
    public SellerAccount getVendor()
    {
        return NozamaSystem.getInstance().getSeller(vendor);
    }


    /**
     * @return String representation of Item's invoice price
     */
    public String getInvoicePrice() {
        return invoicePrice;
    }

    /**
     * @return String representation of Item's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Object override comparison method, object that calls this method is testing if the same as object that's passed as argument.
     * @param o - Object passed by argument
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Objects.equals(ID, item.ID) && Objects.equals(name, item.name) && Objects.equals(sellPrice, item.sellPrice) && Objects.equals(description, item.description) && Objects.equals(quantity, item.quantity);
    }

    /**
     * Gets an item's hexadecimal memory address for each of it's properties
     * @return hexadecimal digits
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID, name, sellPrice, description, quantity);
    }

    /**
     * Changes the value of Item's ID
     * @param ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Changes the value of Item's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes the value of Item's invoice price
     * @param invoicePrice
     */
    public void setInvoicePrice(String invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    /**
     * Changes the value of Item's sell price
     * @param sellPrice
     */
    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * Changes the value of Item's description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Changes the value of Item's quantity
     * @param quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Changes the value of Items's vendor
     * @param vendor
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
