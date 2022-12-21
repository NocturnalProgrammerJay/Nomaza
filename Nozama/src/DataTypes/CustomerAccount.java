package DataTypes;

import NozamaGui.Screens.CustomerDashboard;
import Model.Cart;

/**
 * Represents a customer owned account. Implements IAccount
 * @author Jordan Diaz
 */
public class CustomerAccount implements IAccount
{
    User accountUser;
    private Cart currCart;

    /**
     * Constructor
     * @param user account user
     */
    public CustomerAccount(User user)
    {
        this.accountUser = user;
        this.currCart = new Cart();
    }

    /**
     * Changes pages to Customer Dashboard
     */
    @Override
    public void OnLogIn()
    {
        System.out.println("A customer has logged in");
        CustomerDashboard customerDashboard = new CustomerDashboard();
    }
}
