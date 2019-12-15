package fileMenu;

import java.awt.Frame;
import java.io.File;

public interface IFileChooser {
   void setFileSelectionMode(int mode);
   int showSaveDialog(Frame frame);
   int showOpenDialog(Frame frame);
   int showDialog(Frame frame, String title);
   File getSelectedFile();
}
