package polysolver.tabs;

import javax.swing.JPanel;

import polysolver.engine.Puzzle;


public abstract class MyTab extends JPanel
{
   private String tooltip, label;

   protected MyTab(String lb, String tt){
      tooltip = tt;
      label = lb;
   }

   public String getLabel(){ return label; }
   public String getTooltip(){ return tooltip; }

   protected Puzzle puz=null;
   public void setPuzzle(Puzzle p){
      puz=p;
      init();
   }
   public abstract void init();
   public abstract void exit();
   public abstract void initColors();
}