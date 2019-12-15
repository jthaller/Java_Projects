package polysolver.engine;

import polysolver.PolySolver;

public class ChangeFlags implements IChangeFlag {
   private boolean wasSaved = true;
   private boolean wasValid = true;
   
   public void setChange(){
      if( wasSaved ){
         wasSaved = false;
         PolySolver.updateTitle();         
      }
      wasValid = false;
   }
   public boolean isValid(){ return wasValid; }
   public boolean isSaved(){ return wasSaved; }
   public void setValid(){ wasValid = true; }
   public void setSaved(){ wasSaved = true; }
}
