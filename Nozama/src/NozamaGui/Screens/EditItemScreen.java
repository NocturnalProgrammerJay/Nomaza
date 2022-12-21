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
public class EditItemScreen extends JDialog
{
    private JPanel mainPanel;
    private JTextField nameTextField;
    private JTextField invoicePriceTextField;
    private JTextField sellPriceTextField;
    private JTextField descriptionTextField;
    private JButton saveChangesButton;
    private JSpinner quantitySpinner;
    private JTextField vendorTextField;

    public EditItemScreen(SellerDashboard accountDashboard, Item itemToChange)
    {
        setTitle("Edit Item");
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

        accountDashboard.setIsEditing(true);
        nameTextField.setText(itemToChange.getName());
        invoicePriceTextField.setText(itemToChange.getInvoicePrice());
        sellPriceTextField.setText(itemToChange.getSellPrice());
        descriptionTextField.setText(itemToChange.getDescription());
        vendorTextField.setText(itemToChange.getVendor().getUserName());
        quantitySpinner.setValue(itemToChange.getQuantity());

        quantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if ((int) quantitySpinner.getValue() < 1)
                {
                    quantitySpinner.setValue(1);
                }
            }
        });

        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                itemToChange.setID(itemToChange.getID());
                itemToChange.setName(nameTextField.getText());
                itemToChange.setInvoicePrice(invoicePriceTextField.getText());
                itemToChange.setSellPrice(sellPriceTextField.getText());
                itemToChange.setDescription(descriptionTextField.getText());
                itemToChange.setQuantity(quantitySpinner.getValue().toString());
                itemToChange.setVendor(vendorTextField.getText());

                instance.updateInventoryJson();
                accountDashboard.setIsEditing(false);
                dispose();
            }
        });

        instance.notifyObservers(EditItemScreen.this);

    }
}
