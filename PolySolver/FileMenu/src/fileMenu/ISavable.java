package fileMenu;


import java.io.IOException;

public interface ISavable
{
   // set/get used for loading/saving.
   String get(boolean allowLarge);
   void set(String input) throws IOException;

   // Name of the file extension, without a leading dot.
   String getFileExtension();
   // Description of the type of files with that extension.
   String getFileExtensionDesc();
}
