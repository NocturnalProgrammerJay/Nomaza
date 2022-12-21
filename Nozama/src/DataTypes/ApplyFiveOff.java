package DataTypes;

/**
 *
 * Applies 5% off total cart cost. Extends CouponDecorator
 * @author Jamar
 */
public class ApplyFiveOff extends CouponDecorator {



    /**
     * Constructor: Defines instance of ICoupon
     * @param cart ICoupon Instance
     */
    public ApplyFiveOff(ICoupon cart)
    {
        this.cart = cart;
    }

    /**
     * Calculates 5% coupon of current total
     * @return float representation of the new total
     */
    @Override
    public float getTotal() {
        return (float) (cart.getTotal() - cart.getTotal() * 0.05);
    }
}
