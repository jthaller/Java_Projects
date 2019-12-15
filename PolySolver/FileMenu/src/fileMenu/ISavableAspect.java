package fileMenu;

public interface ISavableAspect
   extends ISavable
{
   // Description of the type of files with that extension.
   String getAspectName();

   boolean isSaveAllowed();
   boolean isSaveActive();
   boolean isLoadAllowed();
   boolean isLoadActive();
}
