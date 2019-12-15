package polysolver;
// Tile Puzzle Solver applet

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import polysolver.engine.Puzzle;
import polysolver.tabs.MyTab;
import polysolver.tabs.TabBoard;
import polysolver.tabs.TabGenerate;
import polysolver.tabs.TabGridType;
import polysolver.tabs.TabNotes;
import polysolver.tabs.TabPlay;
import polysolver.tabs.TabSolve;
import polysolver.tabs.TabTile;


import fileMenu.ISavableAspect;
import fileMenu.ISavableDocument;

public final class MainPanel
   extends JPanel
   implements ChangeListener, ISavableDocument
{
   private int currentTab;
   /* tabs are:
    *  inputTab: for textual import/export of puzzle
    *  gridTypeTab: choose grid type
    *  tileTab: for editing the tileset
    *  generateTab: for generating tile sets
    *  boardTab: for editing the board to contain the tiles
    *  playTab: for placing one or more tiles.
    *  solveTab: for running the solver
    *  notesTab: for making notes
    */

   private MyTab[] tabPanels;
   private JTabbedPane tabSet;

   Puzzle puz=new Puzzle();

   public MainPanel() {
      setPreferredSize(new Dimension(600,500));

      tabPanels = new MyTab[]{
         new TabGridType(),
         new TabTile(),
         new TabGenerate(),
         new TabBoard(),
         new TabPlay(),
         new TabSolve(),
         new TabNotes(),
      };
      tabSet = new JTabbedPane();

      // build row of tabs
      setLayout(new BorderLayout());
      add(tabSet);

      JPanel southPanel = new JPanel(new BorderLayout());
      JLabel copy = new JLabel(" Written by Jaap Scherphuis, © 2004-2013");
      copy.setToolTipText(" email: puzzles@jaapsch.net    website: www.jaapsch.net/puzzles ");
      southPanel.add(copy,BorderLayout.EAST);
      southPanel.add(new JLabel("Version 2.65"),BorderLayout.WEST);
      add(southPanel,BorderLayout.SOUTH);

      for( int i=0; i<tabPanels.length; i++ ){
         tabSet.addTab(tabPanels[i].getLabel(),null,tabPanels[i],tabPanels[i].getTooltip());
      }
      currentTab=0;
      tabSet.setSelectedIndex(currentTab);
      tabSet.addChangeListener(this);
      tabPanels[currentTab].setPuzzle(puz);
   }

   public void stateChanged(ChangeEvent ce){
      tabPanels[currentTab].exit();
      int i=tabSet.getSelectedIndex();
      tabPanels[i].setPuzzle(puz);
      currentTab=i;
   }

   public void paintComponent(Graphics g) {
      tabPanels[currentTab].initColors();
      super.paintComponent(g);
   }

   //------------
   // ISavableDocument

   // set/get used for loading/saving.
   @Override
   public String get(boolean allowLarge) {
      return puz.textRepr(allowLarge);
   }

   @Override
   public void set(String input) throws IOException{
      tabPanels[currentTab].exit();
      Reader reader = new StringReader(input);
      Puzzle p = Puzzle.parse(reader);
      reader.close();
      puz = p;
      tabPanels[currentTab].setPuzzle(puz);
      tabSet.setSelectedIndex(5);
      PolySolver.updateTitle();
      repaint();
   }

   // flag used to know if it needs to be saved
   @Override
   public boolean hasUnsavedChanges(){
      return puz.hasUnsavedChanges();
   }

   // called after successful save, should clear the flag till savable contents change.
   @Override
   public void setNoUnsavedChanges(){
      puz.setNoUnsavedChanges();
      PolySolver.updateTitle();
   }

   // clear all savable content. Called when the New menu option chosen.
   @Override
   public void clear(){
      tabPanels[currentTab].exit();
      puz.getBoard().destroyBoard();
      while( puz.getNumPolyomino()>0 ){
         puz.removePolyomino(puz.getPolyomino(0));
      }
      puz.setDescription(null);
      puz.setNotes(null);
      puz.setNoUnsavedChanges();
      tabPanels[currentTab].init();
      PolySolver.updateTitle();
      repaint();
   }

   // name of the server directory that this object is allowed to write to/read from. null means no server access allowed.
   @Override
   public String getServerDir(){ return "polyform"; }

   // Name of the file extension.
   @Override
   public String getFileExtension(){ return "psv"; }

   // Description of the type of files with that extension.
   @Override
   public String getFileExtensionDesc(){ return "PolySolver files"; }


   public boolean canClose(){
      if( puz.isRunning()){
         int r = JOptionPane.showConfirmDialog(this, "The solver in running. Do you want to stop solving?", "Unfinished solve", JOptionPane.YES_NO_CANCEL_OPTION);
         if( r != JOptionPane.YES_OPTION ) return false;
         puz.stopSolve();
      }
      return true;
   }

   @Override
   public ISavableAspect[] getSavables() {
      return null;
   }
}
