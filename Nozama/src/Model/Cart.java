package Model;
import DataTypes.*;


import java.util.ArrayList;

/**
 * Represents container for instances of any class implementing IItem interface. Implements ICoupon
 * @author Eyan
 */
public class Cart implements ICoupon
{
  /**
   * Constructor
   */
  public Cart(){
    CartContainer = new ArrayList<>();
    CartQuantities = new ArrayList<>();
  }

  /**
   * @return ArrayList<IItem> that represents the Items
   */
  public ArrayList<IItem> getCart(){
    return CartContainer;
  }

  /**
   * Given an Item and quantity of how many to add, adds quantity of IItems to cart
   * @param newItem
   * @param quantity
   */
  public void addItem(IItem newItem, int quantity){

    if (CartContainer.contains(newItem))
    {
      int index = CartContainer.indexOf(newItem);
      int updatedQuantity = quantity + CartQuantities.get(index);

      if (updatedQuantity > newItem.getQuantity())
      {
        cartMessage = "Cannot Add more than given quantity";
      }
      else
      {
        cartMessage = "Cart updated";
        CartQuantities.set(index, updatedQuantity);
      }

    }
    else
    {
      cartMessage = "Item Added";
      CartContainer.add(newItem);
      CartQuantities.add(quantity);
    }

  }

  /**
   * Given an Item to remove and the quantity to delete, removes a quantity of IItems from the Cart
   * @param removedItem
   * @param quantity
   */
  public void removeItem(IItem removedItem, int quantity)
  {
    if (CartContainer.contains(removedItem))
    {
      int index = CartContainer.indexOf(removedItem);
      int currentQuantity = CartQuantities.get(index);

      if (currentQuantity == quantity)
      {
        CartContainer.remove(index);
        CartQuantities.remove(index);
      }
      else if (currentQuantity > quantity)
      {
        CartQuantities.set(index, currentQuantity - quantity);
      }
    }
  }

  /**
   * Prints Cart to screen
   */
  public void viewCart(){
    for(IItem item : CartContainer){
      System.out.println(item.toString());
    }
  }

  /**
   * @return Grand total for each IItem in cart.
   * @Author: Jamar
   */
  public float getTotal(){
    total = 0;
    int index = 0;
    for(IItem item : CartContainer){
      total += Float.parseFloat(item.getSellPrice()) * CartQuantities.get(index);
      index += 1;
    }
    return total;
  }

  /**
   * @return float representing new total with coupons applied
   */
  public float getTotalWithCoupons(){
    if (totalWithCoupons == 0){
      totalWithCoupons = total;
    }
    return totalWithCoupons;
  }

  /**
   * Changes the total after coupons are applied using the given value
   * @param value
   */
  public void setTotalWithCoupons(float value)
  {
    totalWithCoupons = value;
  }

  /**
   * @param index
   * @return Integer representation of an IItem's quantity in the cart given an index
   */
  public int getQuantity(int index)
  {
    return CartQuantities.get(index);
  }

  protected ArrayList<IItem> CartContainer;
  protected ArrayList<Integer> CartQuantities;
  private float total;
  private float totalWithCoupons;
  public String cartMessage;


}