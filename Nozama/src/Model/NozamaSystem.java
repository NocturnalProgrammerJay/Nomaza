package Model;

import DataTypes.*;
import NozamaGui.View;
import ReadWrite.JsonHandler;

import ReadWrite.Json.JSONArray;
import ReadWrite.Json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Manages all the data of the program.
 * Implements the Singleton Pattern
 * @author Jordan Diaz
 * */
public class NozamaSystem
{
    /**
     * Private constructor
     * @author Jordan Diaz
     * */

    private NozamaSystem(){
        addObserver(view);
    }

    /**
     * @return One Constant Instance of NozamaSystem
     */
    public static NozamaSystem getInstance()
    {
        if (instance == null)
            instance = new NozamaSystem();

        return instance;
    }

    /**
     * Used for Parsing JSONObject representing Vendor Data
     * @param profitObj
     */
    private void parseProfitsObject(JSONObject profitObj)
    {
        String key = profitObj.keySet().toString();
        key = key.substring(1, key.length() - 1); // removes the [], results in: "000"

        JSONObject values = (JSONObject) profitObj.get(key);

        String sprofit, srevenues, scosts;
        sprofit = (String) values.get("profit");
        srevenues = (String) values.get("revenues");
        scosts = (String) values.get("costs");

        float profit, revenues, costs;
        profit = Float.parseFloat(sprofit);
        revenues = Float.parseFloat(srevenues);
        costs = Float.parseFloat(scosts);

        vendors.add(new SellerAccount(key, profit, revenues, costs));
    }

    /**
     * Loads Vendors from Json File
     */
    private void loadVendorsFromJson()
    {
        vendors.clear();

        vendorData = jsonHandler.getJSONArrayFromJson("Nozama/testdata/profits.json");
        vendorData.forEach(vendor -> parseProfitsObject((JSONObject) vendor));
    }

    /**
     * Used for parsing User data from JSONObject
     * @param user
     */
    private void parseUserDataObject(JSONObject user)
    {
        String key = user.keySet().toString(); // gets the key in the form: "[000]"
        key = key.substring(1, key.length() - 1); // removes the [], results in: "000"

        JSONObject values = (JSONObject) user.get(key);

        String username, password, accountType;
        username = (String) values.get("username");
        password = (String) values.get("password");
        accountType = (String) values.get("accountType");

        users.add(new User(key, username, password, accountType));
    }

    /**
     * Loads Users from JSON File
     */
    private void loadUsersFromJson()
    {
        users.clear();

        userData = jsonHandler.getJSONArrayFromJson("Nozama/testdata/users.json");
        userData.forEach( user -> parseUserDataObject((JSONObject) user));
    }

    /**
     * Used to parse Bundle Items from JSONObject given a vendor username
     * @param bundleItem
     * @param vendor vendor username
     * @return
     */
    private Item parseBundleItem(JSONObject bundleItem, String vendor)
    {
        String key = bundleItem.keySet().toString();
        key = key.substring(1, key.length() - 1);

        JSONObject values = (JSONObject) bundleItem.get(key);

        String name, invoicePrice,sellPrice, description, quantity;
        name = (String) values.get("name");
        invoicePrice = (String) values.get("invoice_price");
        sellPrice = (String) values.get("sell_price");
        description = (String) values.get("description");
        quantity = (String) values.get("quantity");

        return new Item(key, name, invoicePrice, sellPrice, description, quantity, vendor);
    }

    /**
     * Used to parse Inventory Data Object from JSONObject
     * @param item
     */
    private void parseInventoryDataObject(JSONObject item)
    {
        String key = item.keySet().toString(); // gets the key in the form: "[000]"
        key = key.substring(1, key.length() - 1); // removes the [], results in: "000"

        if (key.charAt(0) == 'b')
        {
            JSONObject values = (JSONObject) item.get(key);

            String bundleName, vendor, quantity;
            bundleName = (String) values.get("bundleName");
            vendor = (String) values.get("vendor");
            quantity = (String) values.get("quantity");

            Bundle bundle = new Bundle(key, bundleName, vendor, quantity);

            JSONArray bundledItems = (JSONArray) values.get("items");

            for (Object obj : bundledItems)
                bundle.addItem(parseBundleItem((JSONObject) obj, vendor));

            inventory.add(bundle);
        }
        else
        {
            JSONObject values = (JSONObject) item.get(key);

            String name, invoicePrice,sellPrice, description, quantity, vendor;
            name = (String) values.get("name");
            invoicePrice = (String) values.get("invoice_price");
            sellPrice = (String) values.get("sell_price");
            description = (String) values.get("description");
            quantity = (String) values.get("quantity");
            vendor = (String) values.get("vendor");

            inventory.add(new Item(key, name, invoicePrice, sellPrice, description, quantity, vendor));
        }


    }

    /**
     * Loads Inventory from JSON File
     */
    private void loadInventoryFromJson()
    {
        inventory.clear();

        inventoryData = jsonHandler.getJSONArrayFromJson("Nozama/testdata/items.json");
        inventoryData.forEach( item -> parseInventoryDataObject((JSONObject) item));
    }


    /**
     * Logs User into their respective pages based on their username and password; Implements Strategy Pattern
     * @param username
     * @param password
     * @return User representation of found user or null if not found
     */
    public User logIn(String username, String password)
    {
        loadUsersFromJson();
        loadVendorsFromJson();
        loadInventoryFromJson();
        for (User user : users)
        {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    /**
     * This Method updates the users.json file to accurately represent the users ArrayList
     * @author Jordan Diaz
     */
    public void updateUserJSON()
    {
        JSONArray output = new JSONArray();
        for (User user : users)
        {
            output.add(user.toJSONObject());
        }
        jsonHandler.writeToJson("Nozama/testdata/users.json", output);
    }

    /**
     * Updates profits.json file to accurately represent the vendor's profits
     */
    public void updateProfitsJSON()
    {
        JSONArray output = new JSONArray();
        for (SellerAccount vendor : vendors)
        {
            output.add(vendor.toJSONObject());
        }
        jsonHandler.writeToJson("Nozama/testdata/profits.json", output);
    }

    /**
     * Updates items.json file to accurately represent the items in the inventory
     */
    public void updateInventoryJson()
    {
        JSONArray output = new JSONArray();
        for (IItem item : inventory)
        {
            output.add(item.toJSONObject());
        }
        jsonHandler.writeToJson("Nozama/testdata/items.json", output);
    }

    /**
     * @return ArrayList<Item> which represents the inventory of the system
     */
    public ArrayList<IItem> getInventory()
    {
        loadInventoryFromJson();
        return inventory;
    }

    /**
     * @return ArrayList<SellerAccount> which represents a list of all vendors
     */
    public ArrayList<SellerAccount> getVendors()
    {
        loadVendorsFromJson();
        return vendors;
    }

    /**
     * Finds the seller given their Username
     * @param username
     * @return SellerAccount that belongs to the seller or null if not found
     */
    public SellerAccount getSeller(String username)
    {
        loadVendorsFromJson();
        for (SellerAccount vendor : vendors)
        {
            if (vendor.getUserName().equals(username)){
                return vendor;
            }
        }
        return null;
    }

    /**
     * @return Instance of Cart object that is specific to the system
     */
    public Cart getCart()
    {
        return cart;
    }

    /**
     * @return String representation of the last ID in the inventory or null if inventory is empty
     */
    public String getLastID(){
        if (!inventory.isEmpty())
        {
            return inventory.get(inventory.size() - 1).getID();
        }
        return null;
    }

    /**
     * Finalizes Transaction
     */
    public void checkout()
    {
        int cartIndex = 0;
        float totalInvoiceSum = 0;
        float totalSellSum = 0;
        for (IItem item : this.getCart().CartContainer)
        {
            int invIndex = inventory.indexOf(item);

            SellerAccount seller = item.getVendor();

            if (item.isBundle())
            {
                // Bundle
                Bundle b = (Bundle) item;

                float invoiceSum = 0;
                float sellSum = 0;
                for (Item bundledItem : b.getItemList())
                {
                    invoiceSum += Float.parseFloat(bundledItem.getInvoicePrice()) * bundledItem.getQuantity();
                    sellSum += Float.parseFloat(bundledItem.getSellPrice()) * bundledItem.getQuantity();
                    seller.calculateProfit();
                }
                totalInvoiceSum += invoiceSum * getCart().getQuantity(cartIndex);
                totalSellSum += sellSum * getCart().getQuantity(cartIndex);

                if (b.getQuantity() > this.getCart().getQuantity(cartIndex))
                {
                    int difference = b.getQuantity() - this.getCart().getQuantity(cartIndex);
                    b.setQuantity(String.valueOf(difference));
                    inventory.set(invIndex, b);
                }
                else if (b.getQuantity() <= this.getCart().getQuantity(cartIndex))
                {
                    inventory.remove(invIndex);
                }

            }
            else
            {
                Item i = (Item) item;

                //Add Costs and Revenues to Vendor
                totalInvoiceSum += Float.parseFloat(i.getInvoicePrice()) * getCart().getQuantity(cartIndex);
                totalSellSum += Float.parseFloat(i.getSellPrice()) * getCart().getQuantity(cartIndex);

                //Remove Purchased from existing quantities
                if (i.getQuantity() > this.getCart().getQuantity(cartIndex))
                {
                    int difference = i.getQuantity() - this.getCart().getQuantity(cartIndex);
                    i.setQuantity(String.valueOf(difference));
                    inventory.set(invIndex, i);
                }
                else if (i.getQuantity() <= this.getCart().getQuantity(cartIndex))
                {
                    inventory.remove(invIndex);
                }

            }
            seller.addToCosts(totalInvoiceSum);
            seller.addToRevenues(totalSellSum);
            seller.calculateProfit();
            cartIndex += 1;

        }

        getCart().CartQuantities.clear();
        getCart().CartContainer.clear();
    }

    /**
     * Changes the value of the current user to the value given
     * @param user
     */
    public void setCurrentUser(User user)
    {
        currentUser = user;
    }

    /**
     * @return User representation of the current user
     */
    public User getCurrentUser()
    {
        return currentUser;
    }

    public void addObserver(IObserver observer)
    {
        observers.add(observer);
    }

    public void notifyObservers(Window screen)
    {
        for (IObserver observer : observers)
        {
            observer.update(screen);
        }
    }


    private ArrayList<IObserver> observers = new ArrayList<>();
    private static NozamaSystem instance;
    private JsonHandler jsonHandler = new JsonHandler();
    private JSONArray userData;
    private JSONArray inventoryData;
    private JSONArray vendorData;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<IItem> inventory = new ArrayList<>();
    private ArrayList<SellerAccount> vendors = new ArrayList<>();
    private View view = new View();
    private User currentUser = null;
    private Cart cart = new Cart();

}
