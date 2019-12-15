package fileMenu;

import java.awt.Component;
import java.awt.Frame;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class FileChooserProxy
   extends JFileChooser
   implements IFileChooser
{
   public FileChooserProxy(File defaultDirectory, FileSystemView fileSystemView) {
      super(defaultDirectory, fileSystemView);
   }

   @Override
   public int showSaveDialog(Frame frame) {
      return showSaveDialog((Component)frame);
   }

   @Override
   public int showOpenDialog(Frame frame) {
      return showOpenDialog((Component)frame);
   }

   @Override
   public int showDialog(Frame frame, String title) {
      return showDialog((Component)frame, title);
   }
}
