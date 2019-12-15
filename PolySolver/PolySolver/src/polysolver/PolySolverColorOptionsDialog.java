package polysolver;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JFrame;

import settingsmodule.ColorOptionsDialog;

public class PolySolverColorOptionsDialog
   extends ColorOptionsDialog
{
   static private String[] colorKeys = {
         PolySolver.settingKeyBgColour,
         PolySolver.settingKeyFgColour,
         PolySolver.settingKeyEdgeDarkColour,
         PolySolver.settingKeyEdgeLightColour,
         PolySolver.settingKeyEdgeSelectionColour,
   };
   static private String[] colorString = {
         "Background Colour",
         "Filled in Colour",
         "Piece Outline Colour",
         "Grid Colour",
         "Selection Outline Colour",
   };
   
   private class PreviewIcon implements Icon{
      int cellSize = 20;
      int width = 8*cellSize;
      int height = 7*cellSize;
      public PreviewIcon() {}
      public int getIconHeight() { return height; }
      public int getIconWidth() { return width; }
      public void paintIcon(Component c, Graphics g, int x, int y) {
         g.setColor(getColor(PolySolver.settingKeyBgColour));
         g.fillRect(x,y,width,height);
         g.setColor(getColor(PolySolver.settingKeyFgColour));
         g.fillRect(x+2*cellSize,y+3*cellSize, 3*cellSize,cellSize);
         
         g.setColor(getColor(PolySolver.settingKeyEdgeLightColour));
         for( int i=1; i<=7; i++ )
            g.drawLine(x+i*cellSize,y+cellSize, x+i*cellSize,y+6*cellSize);
         for( int i=1; i<=6; i++ )
            g.drawLine(x+cellSize,y+i*cellSize, x+7*cellSize,y+i*cellSize);

         g.setColor(getColor(PolySolver.settingKeyEdgeDarkColour));
         g.drawRect(x+2*cellSize,y+3*cellSize, 3*cellSize,cellSize);

         g.setColor(getColor(PolySolver.settingKeyEdgeSelectionColour));
         g.drawRect(x+cellSize*4,y+cellSize*2, 2*cellSize,3*cellSize);
      }
   }   
   
   public PolySolverColorOptionsDialog(JFrame frame) {
      super(frame, PolySolver.settingsManager, colorKeys, colorString);
      setPreviewIcon(new PreviewIcon());
   }
}
