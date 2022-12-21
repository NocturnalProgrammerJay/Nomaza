package NozamaGui.Screens;

import Model.Cart;
import Model.NozamaSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents Screen that uses a form to be generated in JSwing. Extends JDialog
 */
public class CheckoutScreen extends JDialog {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JLabel totalPriceLabel;
    private JButton submitButton;
    private JButton cancelButton;

    public CheckoutScreen(Cart cart)
    {
        setTitle("Checkout");
        setContentPane(panel1);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        NozamaSystem instance = NozamaSystem.getInstance();

        totalPriceLabel.setText("Total: $" + String.format("%.02f", instance.getCart().getTotalWithCoupons()));

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CartScreen screen = new CartScreen(NozamaSystem.getInstance().getCart());
                CheckoutScreen.this.dispose();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                NozamaSystem.getInstance().checkout();

                instance.updateInventoryJson();
                instance.updateProfitsJSON();
                JOptionPane.showMessageDialog(CheckoutScreen.this,
                        "Thank You for Shopping at NOZAMA!",
                        "Transaction Complete", JOptionPane.INFORMATION_MESSAGE);

                dispose();
                LoginScreen screen = new LoginScreen(null);
            }
        });

        instance.notifyObservers(CheckoutScreen.this);

    }
}
