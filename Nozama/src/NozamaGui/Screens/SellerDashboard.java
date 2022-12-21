package NozamaGui.Screens;

import DataTypes.Bundle;
import DataTypes.IItem;
import DataTypes.Item;
import DataTypes.SellerAccount;
import Model.NozamaSystem;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Represents Screen that uses a form to be generated in JSwing. Extends JDialog
 */
public class SellerDashboard extends JDialog
{
    private JPanel mainPanel;
    private JTree itemTree;
    private JButton addNewItemListingButton;
    private JButton addNewBundleListingButton;
    private JTextArea textArea1;
    private JButton editButton;
    private JButton deleteButton;
    private JLabel statsLabel;
    private JLabel testLabel;

    public SellerDashboard(SellerAccount vendor)
    {
        setTitle("Customer Dashboard");
        setContentPane(mainPanel);
        setMinimumSize(new Dimension(500, 429));
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        NozamaSystem instance = NozamaSystem.getInstance();

        currentVendor = vendor;

        statsLabel.setText("Total Profit: $" +
                String.format("%.2f", currentVendor.getProfit()) + "               " +
                "Revenues: $" + String.format("%.2f", currentVendor.getRevenues()) + "               " +
                "Costs: $" + String.format("%.2f", currentVendor.getCosts()) );

        DefaultTreeModel treeContents = setupTreeContents();

        itemTree.setModel(treeContents);

        itemTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e)
            {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) itemTree.getLastSelectedPathComponent();

                if (selectedNode.equals(itemTree.getModel().getRoot()))
                    return;

                if (selectedNode.getParent().equals(itemTree.getModel().getRoot()))
                {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) itemTree.getSelectionPath().getLastPathComponent();
                    textArea1.setText(String.valueOf(listings.get(selectedNode.getParent().getIndex(node))));
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Must edit one item at a time

                if (isEditing)
                    return;

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) itemTree.getLastSelectedPathComponent();

                if (selectedNode.equals(itemTree.getModel().getRoot()))
                    return;

                if (selectedNode.getParent().equals(itemTree.getModel().getRoot()))
                {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) itemTree.getSelectionPath().getLastPathComponent();

                    IItem item = listings.get(selectedNode.getParent().getIndex(node));

                    if (!item.isBundle())
                    {
                        EditItemScreen screen = new EditItemScreen(SellerDashboard.this, (Item) item);
                    }
                }

                if (selectedNode.isLeaf())
                {
                    DefaultMutableTreeNode bundleNode = (DefaultMutableTreeNode) selectedNode.getParent();

                    Bundle bundle = (Bundle) listings.get(selectedNode.getParent().getParent().getIndex(bundleNode));

                    Item subItem = bundle.getItemList().get(selectedNode.getParent().getIndex((DefaultMutableTreeNode) itemTree.getSelectionPath().getLastPathComponent()));

                    EditItemScreen screen = new EditItemScreen(SellerDashboard.this, subItem);
                }




            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                if (isEditing)
                    return;

                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) itemTree.getLastSelectedPathComponent();

                if (selectedNode.equals(itemTree.getModel().getRoot()))
                    return;

                if (selectedNode.getParent().equals(itemTree.getModel().getRoot()))
                {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) itemTree.getSelectionPath().getLastPathComponent();

                    IItem item = listings.get(selectedNode.getParent().getIndex(node));

                    instance.getInventory().remove(selectedNode.getParent().getIndex(node));
                    instance.updateInventoryJson();
                    SellerDashboard screen = new SellerDashboard(vendor);
                    dispose();

                }

                if (selectedNode.isLeaf())
                {
                    DefaultMutableTreeNode bundleNode = (DefaultMutableTreeNode) selectedNode.getParent();

                    Bundle bundle = (Bundle) listings.get(selectedNode.getParent().getParent().getIndex(bundleNode));

                    bundle.getItemList().remove(selectedNode.getParent().getIndex((DefaultMutableTreeNode) itemTree.getSelectionPath().getLastPathComponent()));

                    instance.updateInventoryJson();
                    SellerDashboard screen = new SellerDashboard(vendor);
                    dispose();

                }
            }
        });

        addNewItemListingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (isEditing)
                    return;

                AddItemScreen screen = new AddItemScreen(currentVendor,SellerDashboard.this, false,null);
            }
        });

        addNewBundleListingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isEditing)
                    return;

                AddBundleScreen screen = new AddBundleScreen(currentVendor, SellerDashboard.this);
            }
        });

        instance.notifyObservers(SellerDashboard.this);
    }



    public DefaultTreeModel setupTreeContents() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("My Listed Items:");
        NozamaSystem instance = NozamaSystem.getInstance();


        for (IItem listing : instance.getInventory())
        {
            if (listing.getVendor().equals(currentVendor))
            {
                if (listing.isBundle())
                {
                    Bundle bundle = (Bundle) listing;
                    DefaultMutableTreeNode temp = new DefaultMutableTreeNode(bundle.getName() + " (" + listing.getQuantity() + ")");
                    rootNode.add(temp);

                    for (Item bundleItem : bundle.getItemList())
                    {
                        DefaultMutableTreeNode node = new DefaultMutableTreeNode(bundleItem.getName());
                        temp.add(node);
                    }


                }
                else
                {
                    Item i = (Item) listing;
                    DefaultMutableTreeNode temp = new DefaultMutableTreeNode(i.getName() + " (" + listing.getQuantity() + ")");
                    rootNode.add(temp);
                }
                listings.add(listing);
            }


        }
        return new DefaultTreeModel(rootNode);

    }

    public void setIsEditing(Boolean mode){
        this.isEditing = mode;
    }

    private SellerAccount currentVendor;
    private ArrayList<IItem> listings = new ArrayList<>();
    private boolean isEditing = false;
}
