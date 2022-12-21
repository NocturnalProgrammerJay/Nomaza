package DataTypes;

import ReadWrite.Json.JSONObject;

/**
 * Contract that requires the following methods for any classes inheriting
 * @author Jordan
 */
public interface IItem
{
    String getID();
    String getName();
    String getSellPrice();
    int getQuantity();
    SellerAccount getVendor();
    boolean isBundle();
    JSONObject toJSONObject();
}
