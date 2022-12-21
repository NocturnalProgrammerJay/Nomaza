package NozamaGui.Screens;

import DataTypes.IItem;
import DataTypes.Item;
import Model.NozamaSystem;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents Screen that uses a form to be generated in JSwing. Extends JDialog
 */
public class CustomerDashboard extends JDialog
{
    private JPanel CustomerDashboardPanel;
    private JList feed;
    private JTextArea itemDescriptionTextArea;
    private JButton addToCartButton;
    private JButton cartButton;
    private JSpinner quantitySpinner;
    private DefaultListModel feedModel;

    private IItem currentItem;


    public CustomerDashboard()
    {
        //nozamaTextPane.setText("NOZAMA\n\nWelcome: " + NozamaSystem.getInstance().getCurrentUser().getUsername());

        setTitle("Customer Dashboard");
        setContentPane(CustomerDashboardPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        NozamaSystem instance = NozamaSystem.getInstance();

        feedModel = new DefaultListModel<>();
        feed.setModel(feedModel);

        refreshFeed();

        quantitySpinner.setValue(1);
        feed.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

                if (!e.getValueIsAdjusting())
                {
                    currentItem = instance.getInventory().get(feed.getSelectedIndex());
                    itemDescriptionTextArea.setText(String.valueOf(currentItem));
                    quantitySpinner.setValue(1);
                }

            }
        });

        quantitySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                if (currentItem == null)
                {
                    quantitySpinner.setValue(1);
                    return;
                }

                int quantity = currentItem.getQuantity();

                if ((int) quantitySpinner.getValue() > quantity)
                {
                    quantitySpinner.setValue(quantity);
                }
                else if ((int) quantitySpinner.getValue() < 1)
                {
                    quantitySpinner.setValue(1);
                }

            }
        });

        // Cart Button
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CartScreen screen = new CartScreen(instance.getCart());
                CustomerDashboard.this.dispose();
            }
        });

        // Add to Cart Button
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                instance.getInstance().getCart().addItem(instance.getInventory().get(feed.getSelectedIndex()), (Integer) quantitySpinner.getValue());

                JOptionPane.showMessageDialog(CustomerDashboard.this,
                        instance.getCart().cartMessage + "\n Item: " + instance.getInventory().get(feed.getSelectedIndex()).getName() +
                                "\nQuantity: " + quantitySpinner.getValue(),
                        instance.getCart().cartMessage, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        instance.notifyObservers(CustomerDashboard.this);

    }

    public void refreshFeed()
    {
        NozamaSystem instance = NozamaSystem.getInstance();

        feedModel.removeAllElements();
        for (IItem item : instance.getInventory())
        {
            feedModel.addElement(item.getName());
        }
    }


}
