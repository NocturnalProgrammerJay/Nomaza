package NozamaGui.Screens;

import DataTypes.Bundle;
import DataTypes.Item;
import DataTypes.SellerAccount;
import Model.NozamaSystem;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Represents Screen that uses a form to be generated in JSwing. Extends JDialog
 */
public class AddBundleScreen extends JDialog{
    private JTextField nameTextField;
    private JSpinner quantitySpinner;
    private JTextField vendorTextField;
    private JButton addItemButton;
    private JLabel infoText;
    private JPanel mainPanel;
    private JButton submitBundleButton;


    public AddBundleScreen(SellerAccount account, SellerDashboard accountDashboard)
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

                AddItemScreen screen = new AddItemScreen(account, accountDashboard, true,AddBundleScreen.this);

            }
        });

        submitBundleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Bundle bundle = new Bundle("b000", nameTextField.getText(), vendorTextField.getText(), quantitySpinner.getValue().toString());

                for (Item item : itemsForBundle)
                {
                    bundle.addItem(item);
                }

                instance.getInventory().add(bundle);
                instance.updateInventoryJson();
                SellerDashboard screen = new SellerDashboard(account);
                dispose();
                accountDashboard.dispose();
            }
        });

        instance.notifyObservers(AddBundleScreen.this);
    }

    public int numberOfItems;
    public ArrayList<Item> itemsForBundle = new ArrayList<>();
}
