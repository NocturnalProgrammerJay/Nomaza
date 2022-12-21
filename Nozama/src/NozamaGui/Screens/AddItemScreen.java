package NozamaGui.Screens;

import DataTypes.Item;
import DataTypes.SellerAccount;
import Model.NozamaSystem;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents Screen that uses a form to be generated in JSwing. Extends JDialog
 */
public class AddItemScreen extends JDialog
{
    private JPanel mainPanel;
    private JTextField nameTextField;
    private JTextField invoicePriceTextField;
    private JTextField sellPriceTextField;
    private JTextField descriptionTextField;
    private JSpinner quantitySpinner;
    private JTextField vendorTextField;
    private JButton addItemButton;
    private JLabel infoText;

    public AddItemScreen(SellerAccount account, SellerDashboard accountDashboard, Boolean doBundle, AddBundleScreen bundleScreen)
    {
        setTitle("Add Item");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(500, 700);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Exit without saving?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    accountDashboard.setIsEditing(false);
                    dispose();
                }
            }

        };
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(exitListener);


        NozamaSystem instance = NozamaSystem.getInstance();

        vendorTextField.setText(account.getUserName());
        quantitySpinner.setValue(1);

        quantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) quantitySpinner.getValue() < 1)
                    quantitySpinner.setValue(1);
            }
        });

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                Integer temp = Integer.parseInt(instance.getLastID()) + 1;
                Item item = new Item(String.format("%03d", temp),
                        nameTextField.getText(),
                        invoicePriceTextField.getText(),
                        sellPriceTextField.getText(),
                        descriptionTextField.getText(),
                        quantitySpinner.getValue().toString(),
                        vendorTextField.getText());

                if (doBundle)
                {
                    bundleScreen.itemsForBundle.add(item);
                    dispose();
                }
                else
                {
                    instance.getInventory().add(item);
                    instance.updateInventoryJson();
                    SellerDashboard screen = new SellerDashboard(account);
                    dispose();
                    accountDashboard.dispose();
                }


            }
        });

        instance.notifyObservers(AddItemScreen.this);

    }

}
