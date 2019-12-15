package fileMenu;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

// Simple filechooser dialog as a replacement for JFileChooser
// Reason is that JFileChooser does not work properly in applet context

public class MyFileChooser
   implements IFileChooser, ActionListener, ListSelectionListener, PropertyChangeListener, ItemListener, Comparator<File>
{
   private int selectionMode;
   private File selectedFile;
   JOptionPane optionPane;
   private JDialog dialog;
   private String[] options = {"Open", "Cancel"};
   FileSystemView fileSystemView;
   File directory;
   private FileList filelist = new FileList();
   private JTextField textfield = new JTextField();
   private JComboBox<File> pathbox = new JComboBox<File>();
   private JButton newDirButton = new JButton();
   private JButton upDirButton = new JButton();

   // Display an icon and a string for each object in the list.
   class MyCellRenderer
      extends JLabel
      implements ListCellRenderer<File>
   {
       public Component getListCellRendererComponent(
         JList<? extends File> list,           // the list
         File value,            // value to display
         int index,               // cell index
         boolean isSelected,      // is the cell selected
         boolean cellHasFocus)    // does the cell have focus
       {
           String s = value.getName();
           setText(s);
           Icon icon = fileSystemView.getSystemIcon(value);
           setIcon(icon);
           if (isSelected) {
               setBackground(list.getSelectionBackground());
               setForeground(list.getSelectionForeground());
           } else {
               setBackground(list.getBackground());
               setForeground(list.getForeground());
           }
           setEnabled(list.isEnabled());
           setFont(list.getFont());
           setOpaque(true);
           return this;
       }
   }

   
   class FileList
      extends JList<File>
   {
      public FileList() {
         setCellRenderer(new MyCellRenderer());
      }
   }
   

   public MyFileChooser(File defaultDirectory0, FileSystemView fileSystemView0) {
      fileSystemView = fileSystemView0;
      directory = defaultDirectory0;

      Icon newdiricon = ServerFileSystemView.loadIcon("newdiricon.gif");
      if( newdiricon!=null ) newDirButton.setIcon(newdiricon);
      else newDirButton.setText("New Folder");
      newDirButton.setToolTipText("New Folder");

      Icon updiricon = ServerFileSystemView.loadIcon("dirupicon.gif");
      if( updiricon!=null ) upDirButton.setIcon(updiricon);
      else upDirButton.setText("Up");
      upDirButton.setToolTipText("Up Folder");

      Box topbox = Box.createHorizontalBox();
      topbox.add(pathbox);
      topbox.add(upDirButton);
      topbox.add(newDirButton);
     
      // setup the dialog
      optionPane = new JOptionPane(
            new Object[]{topbox, new JScrollPane(filelist), textfield},
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION,
            null,
            options,
            options[0]);

      filelist.addListSelectionListener(this);
      textfield.addActionListener(this);
      
      MouseListener mouseListener = new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
             if (e.getClickCount() == 2) {
                mouseDoubleClick(e);
             }
         }
     };
     filelist.addMouseListener(mouseListener);
     
     optionPane.addPropertyChangeListener(this);
     
     pathbox.addItemListener(this);
     
     newDirButton.addActionListener(this);
     upDirButton.addActionListener(this);
   }
   
   @Override
   public void setFileSelectionMode(int mode) {
      selectionMode = mode;
   }

   @Override
   public int showSaveDialog(Frame frame) {
      return showDialog(frame, "Save");
   }
   @Override
   public int showOpenDialog(Frame frame) {
      return showDialog(frame, "Open");
   }

   @Override
   public File getSelectedFile() {
      return selectedFile;
   }

   @Override
   public int showDialog(Frame frame, String command) {
      options[0] = command;
      optionPane.setOptions(options);
      optionPane.setInitialValue(options[0]);
      refreshFiles();
      refreshPath();
      dialog = new JDialog(frame, command, true);
      dialog.setContentPane(optionPane);
      dialog.pack();

      //Handle window closing correctly.
      dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      dialog.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent we) {
            optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
         }
      });

      optionPane.setValue( JOptionPane.UNINITIALIZED_VALUE );
      dialog.setVisible(true);
      
      if( optionPane.getValue() == null ) return JFileChooser.CANCEL_OPTION;
      if( optionPane.getValue().equals(options[0]) ){
         return JFileChooser.APPROVE_OPTION;
      }
      return JFileChooser.CANCEL_OPTION;
   }

   @Override
   public void actionPerformed(ActionEvent ae) {
      if( ae.getSource() == textfield ){
         // text field changed
         selectedFile = null;
      }else if( ae.getSource() == upDirButton ){
         setDirectory(directory.getParentFile());
      }else if( ae.getSource() == newDirButton ){
         try {
            File f = fileSystemView.createNewFolder(directory);
            if( f!=null ) {
               String newname = JOptionPane.showInputDialog(null, "Please type a name for the new folder." );
               boolean success = newname!=null; 
               success  = success && f.renameTo(fileSystemView.createFileObject(directory, newname));
               if( !success ){
                  f.delete();
                  JOptionPane.showMessageDialog(null, "Folder could not be created!", "File Error", JOptionPane.ERROR_MESSAGE);
               }else{
                  refreshFiles();
               }
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   @Override
   public void valueChanged(ListSelectionEvent arg0) {
      selectedFile = filelist.getSelectedValue();
      textfield.removeActionListener(this);
      textfield.setText(selectedFile==null ? null : selectedFile.getName());
      textfield.addActionListener(this);
   }

   void mouseDoubleClick(MouseEvent e) {
      int index = filelist.locationToIndex(e.getPoint());
      File f = filelist.getModel().getElementAt(index);
      if( f.isDirectory()){
         setDirectory(f);
      }else{
         selectedFile = f;
         optionPane.setValue(options[0]);
      }
   }

   @Override
   public void propertyChange(PropertyChangeEvent pce) {
      String prop = pce.getPropertyName();
      if (dialog!=null && dialog.isVisible() 
            && pce.getSource() == optionPane
            && prop.equals(JOptionPane.VALUE_PROPERTY)) {

         Object value = optionPane.getValue(); 

         //ignore reset
         if (value == JOptionPane.UNINITIALIZED_VALUE) return;
         
         if( options[0].equals(value) ){
            if( selectedFile == null ){
               selectedFile = fileSystemView.createFileObject(directory, textfield.getText());
            }
            if( selectedFile.isDirectory() && selectionMode == JFileChooser.FILES_ONLY ){
               setDirectory(selectedFile);

               //Reset the JOptionPane's value.
               //If you don't do this, then if the user presses the same button next time, no
               //property change event will be fired.
               optionPane.setValue( JOptionPane.UNINITIALIZED_VALUE );
               return;
            }
         }
         dialog.setVisible(false);
      }
   }
   
   @Override
   public void itemStateChanged(ItemEvent ie) {
      File f = (File) pathbox.getSelectedItem();
      if( f!=null ){
         setDirectory(f);
      }
   }

   //
   void setDirectory(File newDir){
      directory = newDir;
      refreshFiles();
      refreshPath();
   }
   void refreshFiles(){
      File[] files = fileSystemView.getFiles(directory, false);
      Arrays.sort(files, this);
      filelist.setListData(files);
      filelist.repaint();
   }
   void refreshPath(){
      File f = directory;
      List<File> list = new ArrayList<File>();
      while(f!=null){
         list.add(f);
         f = f.getParentFile();
      }
      pathbox.removeItemListener(this);
      pathbox.setModel(new DefaultComboBoxModel<File>(list.toArray(new File[]{})));
      pathbox.addItemListener(this);
      upDirButton.setEnabled(list.size()>1);

      pathbox.repaint();
   }

   @Override
   public int compare(File f1, File f2) {
      if( f1.isDirectory() && !f2.isDirectory()) return -1;
      if( !f1.isDirectory() && f2.isDirectory()) return 1;
      return f1.getName().compareToIgnoreCase(f2.getName());
   }
}
