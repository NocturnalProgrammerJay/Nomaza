package NozamaGui;

import DataTypes.IObserver;

import java.awt.*;

/**
 * Represents the Control for the program's visuals
 * @author Jordan
 */
public class View implements IObserver
{
  public View()
  {}
  public void showScreen(Window screen)
  {
      screen.setVisible(true);
  }
  @Override
  public void update(Window screen)
  {
    showScreen(screen);
  }
}
