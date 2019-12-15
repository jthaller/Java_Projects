package fileMenu;

public interface ISavableDocument
   extends ISavable
{
   // flag used to know if it needs to be saved
   boolean hasUnsavedChanges();
   // called after successful save, should clear the flag till savable contents change.
   void setNoUnsavedChanges();

   // clear all savable content. Called when the New menu option chosen.
   void clear();
   
   // name of the server directory that this object is allowed to write to/read from. null means no server access allowed.
   String getServerDir();

   // get other parts of document that are individually savable
   ISavableAspect[] getSavables();
}

