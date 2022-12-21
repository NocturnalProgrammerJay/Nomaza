package UnitTesting.TestingDataTypes;

import DataTypes.ApplyTenOff;
import DataTypes.ICoupon;
import DataTypes.Item;
import Model.Cart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplyTenOffTest {

    @Test
    void getTotal() {
        Cart c = new Cart();
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        c.addItem(i,5);
        ICoupon coupon;
        coupon = new ApplyTenOff(c);

        assertEquals(148.5,coupon.getTotal());
    }
}