package DataTypes;

/**
 * Abstract class used by AddFive and AddTen classes to wrap discount(s) on ICoupon objects. Implements ICoupon Interface
 * @author: Jamar
 */
public abstract class CouponDecorator implements ICoupon {
    ICoupon cart;
    public abstract float getTotal();
}
