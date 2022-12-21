package UnitTesting.TestingDataTypes;

import DataTypes.ApplyFiveOff;
import DataTypes.ICoupon;
import DataTypes.Item;
import Model.Cart;
import Model.NozamaSystem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplyFiveOffTest {

    @Test
    void getTotal() {
        Cart c = new Cart();
        Item i = new Item("99", "carrots", "35","33","carrotse bad", "1", "");
        c.addItem(i,5);
        ICoupon coupon;
        coupon = new ApplyFiveOff(c);

        assertEquals(156.75,coupon.getTotal());
    }
}