package polysolver;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import settingsmodule.SettingsManager;
import settingsmodule.SettingsMenu;

import fileMenu.FileMenu;
import fileMenu.ISavableDocument;

public class PolySolver extends JApplet
{
   static public SettingsManager settingsManager;
   static public final String settingKeyBgColour = "bgcolor";
   static public final String settingKeyFgColour = "fgcolor";
   static public final String settingKeyEdgeSelectionColour = "edgeselcolor";
   static public final String settingKeyEdgeLightColour = "edgelightcolor";
   static public final String settingKeyEdgeDarkColour = "edgedarkcolor";

   public void init() {
      // the look&feel must be set before any swing objects are made.
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) { }
      settingsManager = SettingsManager.createSettingsManager(this,"polyslvr");
      MainPanel mainPanel = new MainPanel();
      getContentPane().add(mainPanel);
      setJMenuBar(getMenu(true, mainPanel));
   }

   public String getAppletInfo() {
      return "PolySolver, by Jaap Scherphuis";
  }


   
   static JFrame frame;
   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) { }

      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

      settingsManager = SettingsManager.createSettingsManager(PolySolver.class);
      final MainPanel mainPanel = new MainPanel();
      frame.getContentPane().add(mainPanel);
      frame.setJMenuBar(getMenu(false, mainPanel));

      frame.addWindowListener(new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
             attemptClose(mainPanel);
          }
      });

      frame.pack();
      frame.setVisible(true);
      updateTitle();
   }
   static private FileMenu fileMenu;
   private static JMenuBar getMenu(boolean isApplet, ISavableDocument savable){
      fileMenu = new FileMenu(isApplet, savable, frame);
      JMenuBar menuBar = new JMenuBar();
      menuBar.add(fileMenu);
      JMenu smenu = new SettingsMenu(isApplet, settingsManager);
      initialiseSettingsMenu(smenu);
      menuBar.add(smenu);

      return menuBar;
   }

   private static void initialiseSettingsMenu(JMenu smenu){
      settingsManager.registerColorSetting(settingKeyBgColour, Color.WHITE);
      settingsManager.registerColorSetting(settingKeyFgColour, Color.LIGHT_GRAY);
      settingsManager.registerColorSetting(settingKeyEdgeSelectionColour, Color.RED);
      settingsManager.registerColorSetting(settingKeyEdgeLightColour, Color.GRAY);
      settingsManager.registerColorSetting(settingKeyEdgeDarkColour, Color.BLACK);
      
      smenu.addSeparator();
      JMenuItem menuItem = new JMenuItem("Edit colours", KeyEvent.VK_C);
      smenu.add(menuItem);
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) { editColours(); }
      });
   }

   static PolySolverColorOptionsDialog colorOptionsDialog;
   static void editColours(){
      if( colorOptionsDialog==null ) colorOptionsDialog = new PolySolverColorOptionsDialog(frame);
      colorOptionsDialog.showDialog();
   }
   
   public static void updateTitle(){
      if( frame !=null ){
         frame.setTitle("PolySolver: " + fileMenu.getFileString());
      }
   }
   
   static void attemptClose(MainPanel mainPanel){
      if( !mainPanel.canClose()) return;
      fileMenu.menuExit();
   }
}
