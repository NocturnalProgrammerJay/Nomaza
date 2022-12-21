package UnitTesting.TestingDataTypes;
import DataTypes.ApplyFiveOff;
import DataTypes.ICoupon;
import DataTypes.Item;
import Model.Cart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    @Test
    void addItem() {
        Cart c = new Cart();
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        c.addItem(i,1);
        assertEquals(c.getTotal(), 33);
    }

    @Test
    void removeItem() {
        Cart c = new Cart();
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        c.addItem(i,1);
        c.removeItem(i,1);
        assertEquals(c.getTotal(), 0);
    }


    @Test
    void getTotal() {
        Cart c = new Cart();
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        c.addItem(i,1);
        assertEquals(c.getTotal(),33);
    }

    @Test
    void getTotalWithCoupons() {
        Cart c = new Cart();
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        c.addItem(i,5);
        ICoupon coupon;
        coupon = new ApplyFiveOff(c);
        c.setTotalWithCoupons(coupon.getTotal());

        assertEquals(c.getTotalWithCoupons(),coupon.getTotal());
    }

    @Test
    void setTotalWithCoupons() {
        Cart c = new Cart();
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        c.addItem(i,5);
        ICoupon coupon;
        coupon = new ApplyFiveOff(c);
        c.setTotalWithCoupons(coupon.getTotal());

        assertEquals(c.getTotalWithCoupons(),coupon.getTotal());
    }

    @Test
    void getQuantity() {
        Cart c = new Cart();
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        c.addItem(i,1);

        c.getQuantity(0);
        assertEquals(c.getQuantity(0), 1);
    }
}