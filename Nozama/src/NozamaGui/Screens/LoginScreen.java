package NozamaGui.Screens;

import DataTypes.IAccount;
import DataTypes.User;
import Model.NozamaSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents Screen that uses a form to be generated in JSwing. Extends JDialog
 */
public class LoginScreen extends JDialog
{
    private JTextField tfUsername;
    private JButton loginButton;
    private JPanel loginPanel;
    private JPasswordField pfPassword;

    public LoginScreen(JFrame parent)
    {
        super(parent); //This line must always be first statement in constructor
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(800, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        NozamaSystem instance = NozamaSystem.getInstance();


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = tfUsername.getText();
                String passwordInput = String.valueOf(pfPassword.getPassword());

                System.out.println(usernameInput);
                System.out.println(passwordInput);


                User user = instance.logIn(usernameInput, passwordInput);

                if (user != null)
                {
                    instance.setCurrentUser(user);
                    IAccount account = user.getAccount();
                    account.OnLogIn();
                    LoginScreen.this.dispose();
                }
                else
                {
                    showErrorMessage(LoginScreen.this);
                }

            }
        });

        instance.notifyObservers(LoginScreen.this);

    }

    private void showErrorMessage(LoginScreen loginScreen)
    {
        JOptionPane.showMessageDialog(loginScreen,
                "Username or password invalid",
                "try again", JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen(null);
    }

}
