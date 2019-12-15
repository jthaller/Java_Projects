package polysolver.tabs;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

public class MySpinner extends JSpinner {
   private List<String> list = new ArrayList<String>();
   private int minimum = 0;
   private int maximum = 10;
   private int tail = -1; 

   public MySpinner() {
      super( new SpinnerListModel() );
      rebuild();
   }

   private SpinnerListModel getMyModel(){
      return (SpinnerListModel)getModel();
   }

   public void setValue(int v) {
      getMyModel().setValue(list.get(v-minimum));
   }
   public int getIntValue() {
      return minimum + list.indexOf( getMyModel().getValue() );
   }
   public void setMinimum(int min) {
      minimum = min;
      rebuild();
   }
   public void setMaximum(int max) {
      maximum = max;
      rebuild();
   }
   public void setTail(int t){
      tail = t;
      rebuild();
   }   
   private void rebuild(){
      int ix = list.indexOf( getMyModel().getValue() );
      list.clear();
      for( int i=minimum; i<=maximum; i++){
         if( tail>=0) list.add(i+"/"+tail);
         else list.add(""+i);
      }
      getMyModel().setList(list);
      
      if( ix >= list.size() ) ix = list.size()-1;
      if( ix>=0 )getMyModel().setValue(list.get(ix));
      fireStateChanged();
      repaint();
   }
   
}
