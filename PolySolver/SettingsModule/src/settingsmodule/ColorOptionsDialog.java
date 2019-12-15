package settingsmodule;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ColorOptionsDialog
   extends JDialog
   implements ActionListener
{
   private JButton okButton = new JButton("Ok");
   private JButton cancelButton = new JButton("Cancel");
   private Icon previewIcon;
   private SettingsManager settingsManager;

   private String[] colorKeys;
   private String[] colorString;
   private Color[] colors;
   private ColorIcon[] icons;
   private JButton[] colorButtons;

   public Color getColor(String key){
      for( int i=0; i<colorKeys.length ; i++)
         if(colorKeys[i].equals(key)) return colors[i];
      return Color.BLACK;
   }

   private class ColorIcon implements Icon{
      int width = 50;
      int height = 20;
      Color colour;
      public ColorIcon() {}
      public int getIconHeight() { return height; }
      public int getIconWidth() { return width; }
      public void setColour(Color c) { colour = c; }
      public void paintIcon(Component c, Graphics g, int x, int y) {
         g.setColor(colour);
         g.fillRect(x,y,width,height);
         g.setColor(c.getForeground());
         g.drawRect(x,y,width,height);
      }
   }

   private Box previewPanel;
   private JFrame frame;
   protected void setPreviewIcon( Icon previewIcon0 ){
      previewIcon = previewIcon0;
      previewPanel.add(new JLabel(previewIcon));
      previewPanel.setVisible(true);
      pack();
      setLocationRelativeTo(frame);
   }
   protected ColorOptionsDialog(JFrame frame0, SettingsManager settingsManager0, String[] colorKeys0, String[] colorString0) {
      super(frame0, true);
      frame = frame0;
      colorKeys = colorKeys0;
      colorString = colorString0;
      settingsManager = settingsManager0;
      colors = new Color[colorKeys.length];
      icons = new ColorIcon[colorKeys.length];
      colorButtons = new JButton[colorKeys.length];

      JPanel myPanel = new JPanel(new BorderLayout());
      getContentPane().add(myPanel);

      Box buttonbox = Box.createVerticalBox();
      buttonbox.add(Box.createVerticalGlue());
      buttonbox.add(Box.createVerticalStrut(20));
      JPanel buttonpanel = new JPanel(new GridLayout(colorKeys.length,2));
      for( int i=0; i<colorKeys.length; i++){
         icons[i] = new ColorIcon();
         colorButtons[i] = new JButton(icons[i]);
         final int k = i;
         colorButtons[i].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
               editColor(k);
            }});
         buttonpanel.add(new JLabel(colorString[i]+":"));
         buttonpanel.add(colorButtons[i]);

      }
      loadColors();
      buttonbox.add(buttonpanel);
      buttonbox.add(Box.createVerticalStrut(20));
      buttonbox.add(Box.createVerticalGlue());

      previewPanel = Box.createHorizontalBox();
      previewPanel.add(Box.createHorizontalStrut(20));
      previewPanel.add(Box.createHorizontalGlue());

      Box box = Box.createHorizontalBox();
      box.add(previewPanel);
      box.add(Box.createHorizontalStrut(20));
      box.add(Box.createHorizontalGlue());
      box.add(buttonbox);
      box.add(Box.createHorizontalGlue());
      box.add(Box.createHorizontalStrut(20));
      myPanel.add(box);

      box = Box.createHorizontalBox();
      box.add(Box.createVerticalStrut(30));
      box.add(Box.createHorizontalGlue());
      box.add(cancelButton);
      box.add(Box.createHorizontalStrut(20));
      box.add(okButton);
      box.add(Box.createHorizontalStrut(20));
      myPanel.add(box,BorderLayout.SOUTH);

      okButton.addActionListener(this);
      cancelButton.addActionListener(this);
      previewPanel.setVisible(false);

      pack();
      setLocationRelativeTo(frame);
   }

   private void loadColors(){
      for( int i=0; i<colorKeys.length; i++){
         colors[i] = settingsManager.getColorSetting(colorKeys[i]);
         icons[i].setColour(colors[i]);
      }
   }
   private void saveColors(){
      for( int i=0; i<colorKeys.length; i++){
         settingsManager.setColorSetting(colorKeys[i], colors[i]);
      }
   }
   public void showDialog(){
      loadColors();
      setVisible(true);
   }

   public void actionPerformed(ActionEvent e) {
      if(cancelButton == e.getSource()) {
         setVisible(false);
      }else if(okButton == e.getSource()) {
         saveColors();
         setVisible(false);
      }else if(okButton == e.getSource()) {
         setVisible(false);
      }
   }
   public void editColor(int i){
      Color oldColor = colors[i];
      Color newColor = JColorChooser.showDialog(this, "Choose a colour", oldColor);
      if( newColor!=null && !newColor.equals(oldColor) ){
         colors[i] = newColor;
         icons[i].setColour(colors[i]);
      }
   }
}
