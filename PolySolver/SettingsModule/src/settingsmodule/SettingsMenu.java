package settingsmodule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class SettingsMenu
   extends JMenu
{
   JMenuItem saveMenuItem;
   SettingsManager settings;

   private final String fileExtension = "txt";
   private JFileChooser chooser;
   private File previousFile;

   public SettingsMenu(boolean isApplet, SettingsManager settings0){
      super("Settings");
      settings = settings0;

      setMnemonic(KeyEvent.VK_S);
      getAccessibleContext().setAccessibleDescription("The settings menu");
      JMenuItem menuItem;
      if( !isApplet ){
         menuItem = new JMenuItem("Load Settings", KeyEvent.VK_L);
         menuItem.getAccessibleContext().setAccessibleDescription("Load settings from file");
         add(menuItem);
         menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) { menuLoad(); }
         });

         menuItem = new JMenuItem("Save Settings", KeyEvent.VK_S);
         menuItem.getAccessibleContext().setAccessibleDescription("Save settings to file");
         add(menuItem);
         menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) { menuSave(); }
         });
         saveMenuItem = menuItem;

         addSeparator();
      }

      menuItem = new JMenuItem("Reset to default", KeyEvent.VK_R);
      menuItem.getAccessibleContext().setAccessibleDescription("Revert to default settings");
      add(menuItem);
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) { menuReset(); }
      });
   }

   private void setChooser(){
      chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(fileExtension+" file", fileExtension);
      chooser.setFileFilter(filter);
      chooser.setAcceptAllFileFilterUsed(false);
   }
   private File checkFileExtension( File f ){
      String fext = "."+fileExtension;
      String fn = f.getName();
      if( fn.endsWith(fext)) return f;
      return new File(f.getParent(), f.getName()+fext);
   }

   void menuReset(){
      int returnVal = JOptionPane.showConfirmDialog(this, "Do you want to reset all settings to their default values?", "Reset Settings", JOptionPane.YES_NO_OPTION);
      if( returnVal == JOptionPane.YES_OPTION)
         settings.reset();
   }

   boolean menuSave(){
      if( chooser==null ) setChooser();
      File newfile;
      while(true){
         int returnVal = chooser.showSaveDialog(this);
         if(returnVal != JFileChooser.APPROVE_OPTION) return false;
         newfile = checkFileExtension(chooser.getSelectedFile());
         chooser.setSelectedFile(newfile);
         if( newfile==previousFile || ! newfile.exists() ) break;
         returnVal = JOptionPane.showConfirmDialog(this, "Do you want to overwrite the existing file?", "Overwrite file", JOptionPane.YES_NO_CANCEL_OPTION);
         if( returnVal == JFileChooser.APPROVE_OPTION ) break;
         if( returnVal != JFileChooser.CANCEL_OPTION ) return false;
      }
      return save(newfile);
   }
   void menuLoad(){
      if( chooser==null ) setChooser();
      int returnVal = chooser.showOpenDialog(this);
      if(returnVal != JFileChooser.APPROVE_OPTION) return;
      File newfile = checkFileExtension(chooser.getSelectedFile());
      chooser.setSelectedFile(newfile);
      load(newfile);
   }

   private boolean save(File f){
      try{
         OutputStream os = new FileOutputStream(f);
         settings.exportSettings( os );
         os.close();
         previousFile = f;
         return true;
      }catch(IOException fe){
         JOptionPane.showMessageDialog(this, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
         return false;
      }
   }
   private void load(File f){
      try{
         // read in whole file
         InputStream is = new FileInputStream(f);
         settings.importSettings(is);
         is.close();
         previousFile = f;
      }catch(IOException fe){
         JOptionPane.showMessageDialog(this, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
      }
   }
}
