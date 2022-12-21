package DataTypes;

/**
 * Applies 10% off total cart cost. Extends CouponDecorator
 * @author Jamar
 */
public class ApplyTenOff extends CouponDecorator{


    /**
     * Constructor: Defines instance of ICoupon
     * @param cart ICoupon Instance
     */
    public ApplyTenOff(ICoupon cart)
    {
        this.cart = cart;
    }

    /**
     * Calculates 10% coupon of current total
     * @return float representation of the new total
     */
    @Override
    public float getTotal() {
        return (float) (cart.getTotal() - cart.getTotal() * 0.1);
    }

}
