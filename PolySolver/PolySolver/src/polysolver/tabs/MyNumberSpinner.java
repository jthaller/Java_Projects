package polysolver.tabs;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MyNumberSpinner extends JSpinner {

   public MyNumberSpinner() {
      super( new SpinnerNumberModel(1,0,null,1) );
   }

   private SpinnerNumberModel getMyModel(){
      return (SpinnerNumberModel)getModel();
   }

   public void setValue(int v) {
      getMyModel().setValue(v);
   }
   public int getIntValue() {
      return (Integer)getMyModel().getValue();
   }
   public void setMinimum(int min) {
      getMyModel().setMinimum(min);
   }
   public void setMaximum(int max) {
      getMyModel().setMaximum(max);
   }
}
