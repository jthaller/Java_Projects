package fileMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class FileMenu
   extends JMenu
{
   static FileMenu filemenu;
   
   JMenuItem saveMenuItem;
   ISavableDocument savable;
   JFrame frame;
   ServerFileSystemView serverFileSystemView;
   
   File localFile; // local file from which current object loaded
   File remoteFile; // remote file from which current object loaded

   private JFileChooser localchooser;
   private IFileChooser remotechooser;

   
   private class AspectsMenuItem
      extends JMenuItem
   {
      private boolean isSave;
      private ISavableAspect aspect;
      AspectsMenuItem(ISavableAspect asp, String prefix, boolean isSave0){
         super(prefix + asp.getAspectName());
         aspect = asp;
         isSave = isSave0;
      }
      void checkEnabled(){
         setEnabled((isSave && aspect.isSaveActive()) || (!isSave && aspect.isLoadActive()));
      }
   }
   
   private class AspectsMenu
      extends JMenu
   {
      private boolean isSave;
      private String prefix;
      private List<AspectsMenuItem> list = new ArrayList<AspectsMenuItem>();
      AspectsMenu(String name, String prefix0, boolean isSave0){
         super(name);
         prefix = prefix0;
         isSave = isSave0;
      }
      void addAspect(ISavableAspect asp){
         if( isSave && !asp.isSaveAllowed() ) return;
         if( !isSave && !asp.isLoadAllowed() ) return;
         AspectsMenuItem item = new AspectsMenuItem(asp, prefix, isSave);
         list.add(item);
         add(item);
         final ISavableAspect aspect = asp;
         if( isSave ){
            item.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent arg0) { menuSaveAs(aspect); }
            });
         }else{
            item.addActionListener(new ActionListener(){
               public void actionPerformed(ActionEvent arg0) { menuLoad(aspect); }
            });
         }
         
      }
      void checkEnabled(){
         for(AspectsMenuItem item  : list){
            item.checkEnabled();
         }
      }
   }

   
   
   public FileMenu(boolean isApplet, ISavableDocument savable0, JFrame frame0){
      super("File");
      savable = savable0;
      frame = frame0;
      serverFileSystemView = new ServerFileSystemView(savable.getServerDir());

      setMnemonic(KeyEvent.VK_F);
      getAccessibleContext().setAccessibleDescription("The file menu");

      addMenuListener(new MenuListener(){
         public void menuCanceled(MenuEvent arg0) {}
         public void menuDeselected(MenuEvent arg0) {}
         public void menuSelected(MenuEvent arg0) {
            if( saveMenuItem != null )
               saveMenuItem.setEnabled(localFile != null && savable.hasUnsavedChanges());
         }
      });

      JMenuItem menuItem = new JMenuItem("New", KeyEvent.VK_N);
      menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK));
      menuItem.getAccessibleContext().setAccessibleDescription("New Puzzle");
      add(menuItem);
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) { menuNew(); }
      });

      if( !isApplet ){
         menuItem = new JMenuItem("Open", KeyEvent.VK_O);
         menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK));
         menuItem.getAccessibleContext().setAccessibleDescription("Open a file");
         add(menuItem);
         menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) { menuLoad(savable); }
         });

         menuItem = new JMenuItem("Save", KeyEvent.VK_S);
         menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK));
         menuItem.getAccessibleContext().setAccessibleDescription("Save");
         add(menuItem);
         menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) { menuSave(); }
         });
         saveMenuItem = menuItem;

         menuItem = new JMenuItem("Save As", KeyEvent.VK_A);
         menuItem.getAccessibleContext().setAccessibleDescription("Save as a file");
         add(menuItem);
         menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) { menuSaveAs(savable); }
         });

         //
         ISavableAspect[] aspects = savable.getSavables();
         if( aspects !=null && aspects.length>0 ){
            final AspectsMenu saveSubMenu = new AspectsMenu("Save Part", "Save ", true);
            for( ISavableAspect aspect : aspects ){
               saveSubMenu.addAspect(aspect);
            }
            saveSubMenu.addMenuListener(new MenuListener(){
               public void menuCanceled(MenuEvent arg0) {}
               public void menuDeselected(MenuEvent arg0) {}
               public void menuSelected(MenuEvent arg0) {
                  saveSubMenu.checkEnabled();
               }
            });

            final AspectsMenu loadSubMenu = new AspectsMenu("Load Part", "Load ", false);
            for( ISavableAspect aspect : aspects ){
               loadSubMenu.addAspect(aspect);
            }
            loadSubMenu.addMenuListener(new MenuListener(){
               public void menuCanceled(MenuEvent arg0) {}
               public void menuDeselected(MenuEvent arg0) {}
               public void menuSelected(MenuEvent arg0) {
                  loadSubMenu.checkEnabled();
               }
            });
         
            add(saveSubMenu);
            add(loadSubMenu);
         }

         addSeparator();
      }

      menuItem = new JMenuItem("Download", KeyEvent.VK_D);
      menuItem.getAccessibleContext().setAccessibleDescription("Download from server");
      add(menuItem);
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) { menuDownload(); }
      });

      menuItem = new JMenuItem("Upload", KeyEvent.VK_U);
      menuItem.getAccessibleContext().setAccessibleDescription("Upload to server");
      add(menuItem);
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) { menuUpload(); }
      });

      menuItem = new JMenuItem("Remove", KeyEvent.VK_R);
      menuItem.getAccessibleContext().setAccessibleDescription("Remove from server");
      add(menuItem);
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) { menuRemove(); }
      });

      if( isApplet ){
         menuItem = new JMenuItem("Rename", KeyEvent.VK_M);
         menuItem.getAccessibleContext().setAccessibleDescription("Rename server file");
         add(menuItem);
         menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) { menuRename(); }
         });
      }
      
      
      menuItem = new JMenuItem("Change Password", KeyEvent.VK_P);
      menuItem.getAccessibleContext().setAccessibleDescription("Change Password on a server file");
      add(menuItem);
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) { menuChangePass(); }
      });

      if( !isApplet ){
         addSeparator();

         menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
         menuItem.getAccessibleContext().setAccessibleDescription("Exit");
         add(menuItem);
         menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) { menuExit(); }
         });
      }
      
      filemenu = this;
   }

   private void setLocalChooser(ISavable sav){
      if( localchooser == null ) localchooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(sav.getFileExtensionDesc(), sav.getFileExtension());
      localchooser.setFileFilter(filter);
      localchooser.setAcceptAllFileFilterUsed(false);
   }
   private void setRemoteChooser(){
      if( remotechooser == null )
         if( saveMenuItem!=null )
            remotechooser = new FileChooserProxy(serverFileSystemView.getDefaultDirectory(), serverFileSystemView);
         else
            remotechooser = new MyFileChooser(serverFileSystemView.getDefaultDirectory(), serverFileSystemView); 

      //FileNameExtensionFilter filter = new FileNameExtensionFilter(savable.getFileExtensionDesc(), savable.getFileExtension());
      //remotechooser.setFileFilter(filter);
      //remotechooser.setAcceptAllFileFilterUsed(false);
   }

   private File checkFileExtension( File f ){
      String fext = "."+savable.getFileExtension();
      String fn = f.getName();
      if( fn.endsWith(fext)) return f;
      return new File(f.getParent(), f.getName()+fext);
   }

   void menuNew(){
      if( processUnsavedData() ){
         savable.clear();
         localFile = null;
         remoteFile = null;
         savable.setNoUnsavedChanges();
         repaint();
      }
   }

   boolean menuSave(){
      if( localFile==null ) return menuSaveAs(savable);
      if( ! savable.hasUnsavedChanges() ) return true;
      return save(localFile, savable);
   }
   boolean menuSaveAs(ISavable sav){
      setLocalChooser(sav);
      File newfile;
      while(true){
         int returnVal = localchooser.showSaveDialog(frame);
         if(returnVal != JFileChooser.APPROVE_OPTION) return false;
         newfile = checkFileExtension(localchooser.getSelectedFile());
         localchooser.setSelectedFile(newfile);
         if( newfile==localFile || ! newfile.exists() ) break;
         returnVal = JOptionPane.showConfirmDialog(frame, "Do you want to overwrite the existing file?", "Overwrite file", JOptionPane.YES_NO_CANCEL_OPTION);
         if( returnVal == JFileChooser.APPROVE_OPTION ) break;
         if( returnVal != JFileChooser.CANCEL_OPTION ) return false;
      }
      return save(newfile, sav);
   }
   void menuLoad(ISavable sav){
      if( processUnsavedData() ){
         setLocalChooser(sav);
         int returnVal = localchooser.showOpenDialog(frame);
         if(returnVal != JFileChooser.APPROVE_OPTION) return;
         File newfile = checkFileExtension(localchooser.getSelectedFile());
         localchooser.setSelectedFile(newfile);
         load(newfile, sav);
      }
   }

   boolean menuUpload(){
      setRemoteChooser();
      remotechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      ServerFile newfile;
      while(true){
         int returnVal = remotechooser.showSaveDialog(frame);
         if(returnVal != JFileChooser.APPROVE_OPTION) return false;
         newfile = (ServerFile)remotechooser.getSelectedFile();
         if( newfile.equals(remoteFile) || ! newfile.exists() ) break;
         returnVal = JOptionPane.showConfirmDialog(frame, "Do you want to overwrite the existing file?", "Overwrite file", JOptionPane.YES_NO_CANCEL_OPTION);
         if( returnVal == JFileChooser.APPROVE_OPTION ) break;
         if( returnVal != JFileChooser.CANCEL_OPTION ) return false;
      }

      return save(newfile);
   }

   void menuDownload(){
      if( processUnsavedData() ){
         setRemoteChooser();
         remotechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         int returnVal = remotechooser.showOpenDialog(frame);
         if(returnVal != JFileChooser.APPROVE_OPTION) return;
         ServerFile newfile = (ServerFile)remotechooser.getSelectedFile();
         load(newfile);
      }
   }

   void menuRemove(){
      setRemoteChooser();
      remotechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      ServerFile file;
      while(true){
         int returnVal = remotechooser.showDialog(frame, "Remove");
         if(returnVal != JFileChooser.APPROVE_OPTION) return;
         file = (ServerFile) remotechooser.getSelectedFile();
         if( ! file.exists() ) return;

         String fd = file.isDirectory() ? "directory" : "file";
         returnVal = JOptionPane.showConfirmDialog(frame, "Do you want to remove the "+fd+" "+file.getName()+"?", "Remove "+fd, JOptionPane.YES_NO_CANCEL_OPTION);
         if( returnVal == JFileChooser.APPROVE_OPTION ) break;
         if( returnVal != JFileChooser.CANCEL_OPTION ) return;
      }
      remove(file);
   }

   void menuRename(){
      setRemoteChooser();
      remotechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      ServerFile file;

      int returnVal = remotechooser.showDialog(frame, "Rename");
      if(returnVal != JFileChooser.APPROVE_OPTION) return;
      file = (ServerFile) remotechooser.getSelectedFile();
      if( ! file.exists() ) return;

      String fd = file.isDirectory() ? "directory" : "file";
      String newname = JOptionPane.showInputDialog(frame, "Please type the new name for the "+fd+" \""+file.getName()+"\"." );
      String password = file.hasPassword() ? getPassword() : "";

      // rename it
      try{
         String err = ServerUtil.submit(ServerUtil.RENAMEFILE, new String[]{
            "dir1", file.getParent(),
            "dir2", file.getParent(),
            "filename1", file.getName(),
            "filename2", newname,
            "password", password,
         });
         if( !err.startsWith("Ok\n") ) throw new IOException(err);
         JOptionPane.showMessageDialog(frame, "The "+fd+" has been renamed to \""+newname+"\".", "Renamed "+fd, JOptionPane.INFORMATION_MESSAGE);
      }catch(IOException fe){
         JOptionPane.showMessageDialog(frame, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   void menuChangePass(){
      setRemoteChooser();
      remotechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      int returnVal = remotechooser.showDialog(frame, "Change Password");
      if(returnVal != JFileChooser.APPROVE_OPTION) return;
      ServerFile file = (ServerFile)remotechooser.getSelectedFile();
      if( ! file.exists() ) return;
      
      String oldpassword = file.hasPassword() ? getPassword() : "";
      if( oldpassword == null ){
         JOptionPane.showMessageDialog(frame, "Password has NOT been changed.", "Password Not Changed", JOptionPane.INFORMATION_MESSAGE);
         return;
      }
      String newpassword = getNewPassword();
      if( newpassword == null ){
         JOptionPane.showMessageDialog(frame, "Password has NOT been changed.", "Password Not Changed", JOptionPane.INFORMATION_MESSAGE);
         return;
      }
      // change it
      try{
         String err = ServerUtil.submit(ServerUtil.CHANGEPASS, new String[]{
            "dir", file.getParent(),
            "filename", file.getName(),
            "oldpassword", oldpassword,
            "newpassword", newpassword,
         });
         if( !err.startsWith("Ok\n") ) throw new IOException(err);
         JOptionPane.showMessageDialog(frame, "The password of "+file.getName()+" has been changed.", "Password changed", JOptionPane.INFORMATION_MESSAGE);
      }catch(IOException fe){
         JOptionPane.showMessageDialog(frame, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   public void menuExit(){
      if( processUnsavedData() ){
         System.exit(0);
      }
   }

   private boolean processUnsavedData(){
      if( savable.hasUnsavedChanges() ){
         boolean upload = saveMenuItem==null || ( localFile==null && remoteFile!=null );
         while(true){
            int r = JOptionPane.showConfirmDialog(frame, "There are unsaved changes. Do you want to "+(upload ? "upload" :"save")+" them?", "Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if( r == JOptionPane.CANCEL_OPTION ) return false;
            else if( r == JOptionPane.YES_OPTION ){
               if( (!upload && menuSave()) || (upload && menuUpload()) ) break;
            }
            else break;
         }
      }
      return true;
   }
   private boolean save(ServerFile f){
      try{
         String filename = f.getName();
         String dirname = f.getParent();
         String err = ServerUtil.checkFileName(filename);
         if( err!=null ) throw new IOException(err);
         String password = getPassword(f.exists() && f.hasPassword());
         if( password == null ){
            JOptionPane.showMessageDialog(frame, "File has NOT been uploaded.", "File Not Uploaded", JOptionPane.INFORMATION_MESSAGE);
            return false;
         }
         // upload
         err = ServerUtil.submit(ServerUtil.WRITEFILE, new String[]{
               "dir", dirname,
               "filename", filename,
               "password", password,
               "contents", savable.get(false)
         });
         if( !err.startsWith("Ok\n") ) throw new IOException(err);
         remoteFile = f;
         if( localFile==null ){
            savable.setNoUnsavedChanges();
         }
         JOptionPane.showMessageDialog(frame, "Succesfully uploaded to the file '"+f.getName()+"'.", "File Uploaded", JOptionPane.INFORMATION_MESSAGE);
         return true;
      }catch(IOException fe){
         JOptionPane.showMessageDialog(frame, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
         return false;
      }
   }
   private boolean save(File f, ISavable sav){
      try{
         Writer writer = new FileWriter(f);
         writer.write(sav.get(true));
         writer.close();
         if( sav instanceof ISavableDocument ){
            localFile = f;
            ((ISavableDocument)sav).setNoUnsavedChanges();
         }
         return true;
      }catch(IOException fe){
         JOptionPane.showMessageDialog(frame, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
         return false;
      }
   }
   private void load(ServerFile newfile){
      try{
         String filename = newfile.getName();
         String err = ServerUtil.checkFileName(filename);
         if( err!=null ) throw new IOException(err);
         // upload
         err = ServerUtil.submit(ServerUtil.READFILE, new String[]{
               "dir", newfile.getParent(),
               "filename", filename,
         });

         if( !err.startsWith("Ok\n") ) throw new IOException(err);

         // parse it
         remoteFile = newfile;
         savable.set(err.substring(3));
      }catch(IOException fe){
         JOptionPane.showMessageDialog(frame, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
      }
   }
   private void load(File newfile, ISavable sav){
      try{
         // read in whole file
         BufferedReader br = new BufferedReader( new FileReader(newfile) );
         StringBuilder s = new StringBuilder();
         String d = br.readLine();
         while( d!=null ){
            s.append(d).append('\n');
            d = br.readLine();
         }
         br.close();

         // parse it
         if( sav instanceof ISavableDocument ){
            localFile = newfile;
         }
         sav.set(s.toString());
      }catch(IOException fe){
         JOptionPane.showMessageDialog(frame, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
      }
   }

   private void remove(ServerFile file){
      try{
         String filename = file.getName();
         String err = ServerUtil.checkFileName(filename);
         if( err!=null ) throw new IOException(err);
         String password = file.hasPassword() ? getPassword() : "";
         if( password == null ){
            JOptionPane.showMessageDialog(frame, "File has NOT been removed.", "File Not Removed", JOptionPane.INFORMATION_MESSAGE);
            return;
         }
         // remove
         err = ServerUtil.submit(ServerUtil.DELETEFILE, new String[]{
            "dir", file.getParent(),
            "filename", filename,
            "password", password,
         });

         if( !err.startsWith("Ok\n") ) throw new IOException(err);
         String fd = file.isDirectory() ? "directory" : "file";
         JOptionPane.showMessageDialog(frame, "The "+fd+" '"+file.getName()+"' has been removed.", "File Deleted", JOptionPane.INFORMATION_MESSAGE);
      }catch(IOException fe){
         JOptionPane.showMessageDialog(frame, fe.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
      }
   }
   
   
   String getPassword(boolean exists){
      return exists ? getPassword() : getNewPassword();
   }
   String getPassword(){
      return JOptionPane.showInputDialog(frame, "Please type the file's edit password." );
   }
   String getNewPassword(){
      while(true){
         String password = JOptionPane.showInputDialog(frame, "Please type a password, or leave it empty to allow everyone to edit." );
         if( password==null || password.isEmpty() ) return password;
         String password2 = JOptionPane.showInputDialog(frame, "Please re-type the password." );
         if( password2==null || password.equals(password2) ) return password2;
         JOptionPane.showMessageDialog(frame, "The passwords did not match!\nPlease try again.", "Password mismatch", JOptionPane.ERROR_MESSAGE);
      }
   }
   
   public String getFileString(){
      String s = "";
      if( localFile!=null ){
         s += localFile.getName();
      }else if( remoteFile!=null ){
         s += remoteFile.getName();
      }
      if( s.isEmpty() ) s = "<unsaved>";
      if( savable.hasUnsavedChanges() )
         s += " *";
      return s;
   }
}
