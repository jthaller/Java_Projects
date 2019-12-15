package polysolver.tabs;
/*

Two states:
   empty hand:
      click on filled tile on board to pick tile up (tile returned to side view, shadow tile picked up)
      click on filled tile on side view to pick (shadow) tile up
   filled:
       Always have floating shadow outline, moves with mouse
       outline colour indicates whether it can be placed or not when clicked tile.
       click: place tile on board if placable at mouse, else return it to side view.

side view shows:
     Always shows current picked up piece
     Also shown amount remaining (excluding picked up piece).
     Current orientation, selectable. If currently carrying piece, piece is rotated
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import polysolver.PolySolver;
import polysolver.engine.Board;
import polysolver.engine.Coord;
import polysolver.engine.CoordOri;
import polysolver.engine.Polyomino;



public final class TabPlay extends MyTab
   implements ActionListener, ChangeListener
{
   private GridEditPanel editPanel, viewPanel;
   private JButton clearButton;
   private JLabel polyAmount;
   private MySpinner polyIndex, polyOrient;
   private boolean carrying;
   private int floatHandle=0;
   private JTextField description = new JTextField();

   public TabPlay(){
      super("Place", "Place shapes on or remove them from the board.");
      setLayout(new BorderLayout());

      add(description, BorderLayout.NORTH);
      description.setEditable(false);

      editPanel = new GridEditPanel(false,true);
      add(editPanel);

      viewPanel = new GridEditPanel(false,false);

      polyIndex = new MySpinner();
      polyOrient = new MySpinner();
      polyAmount = new JLabel();

      clearButton = new JButton("Clear All");

      JPanel controlPanel = new JPanel();
      controlPanel.setLayout(new GridLayout(3,2));
      controlPanel.add(new JLabel("Piece:"));
      controlPanel.add(polyIndex);
      controlPanel.add(new JLabel("Orientation:"));
      controlPanel.add(polyOrient);
      controlPanel.add(clearButton);
      controlPanel.add(polyAmount);

      JPanel southPanel = new JPanel();
      southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.X_AXIS));
      southPanel.add(viewPanel);
      southPanel.add(controlPanel);

      JPanel cv = new JPanel();
      cv.setLayout(new BoxLayout(cv,BoxLayout.Y_AXIS));
      cv.add(Box.createVerticalStrut(10));
      cv.add(southPanel);
      add(cv, BorderLayout.SOUTH);
   }
   public void init(){

      int i=puz.getNumPolyomino();
      boolean en = i>0;
      polyIndex.removeChangeListener(this);
      polyIndex.setMaximum(en?i:1);
      polyIndex.setMinimum(1);
      polyIndex.setTail(i);
      polyIndex.setValue(1);
      polyIndex.setEnabled(en);
      polyOrient.setEnabled(en);
      clearButton.setEnabled(en);

      en&=!puz.prepareSolve();

      description.setText(puz.getDescription());

      editPanel.setNumColours(puz.getNumPieces());
      editPanel.setBoard(puz.getBoard(), puz.getGridType());
      viewPanel.setGridType( puz.getGridType());

      viewPanel.setForeground(Color.CYAN);
      viewPanel.setBackground(getBackground());

      if(en){
         updatePolyIndex();
         updatePolyAmount();
         updatePolyOrient();
         polyIndex.addChangeListener(this);
         polyOrient.addChangeListener(this);
         clearButton.addActionListener(this);
         editPanel.addActionListener(this);
         viewPanel.addActionListener(this);
      }else{
         viewPanel.setBlockList();
      }
   }
   public void exit(){
      puz.prepareSolve();
      puz.clearBoard();
   }
   public void initColors(){
      Color fillColor      = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyFgColour);
      Color bgColor        = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyBgColour);
      Color edgeDarkColor  = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeDarkColour);
      Color edgeLightColor = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeLightColour);
      Color selColor       = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeSelectionColour);

      viewPanel.setColors(fillColor, getBackground(), edgeDarkColor, edgeLightColor, selColor);
      editPanel.setColors(fillColor, bgColor, edgeDarkColor, edgeLightColor, selColor);
   }
   // get currently selected Polyomino
   private Polyomino getPoly(){
      int i=polyIndex.getIntValue()-1;
      return i>=0? puz.getPolyomino(i) : null;
   }
   // done when polyIndex changed
   private void updatePolyIndex(){
      // drop carried poly
      if( carrying ) dropIt();
      // use first orient for new chosen orient.
      Polyomino poly = getPoly();
      if(poly==null){
         polyIndex.setEnabled(false);
         polyOrient.setEnabled(false);
      }else{
         int i=poly.getNumOrient();
         polyOrient.removeChangeListener(this);
         polyOrient.setMinimum(1);
         polyOrient.setMaximum(i);
         polyOrient.setTail(i);
         polyOrient.setValue(1);
         polyOrient.addChangeListener(this);
      }
   }
   private void updatePolyAmount(){
      int i = getPoly().getAvailable();
      polyAmount.setText("Amount:" +(carrying? i-1 : i));
   }
   // done when polyOrient changed
   private void updatePolyOrient(){
      Polyomino poly = getPoly();
      int n=polyOrient.getIntValue()-1;
      Coord[] c = poly.getOrient(n);
      viewPanel.setBlockList(c,1);
      if( carrying ) carryIt();
   }
   private void carryIt(){
      Polyomino poly = getPoly();
      if( poly.getAvailable()>0 ){
         int n=polyOrient.getIntValue()-1;
         Coord[] c = poly.getOrient(n);
         editPanel.setFloat(c,floatHandle);
         viewPanel.setFloat(c,floatHandle);
         carrying=true;
      }
   }
   private void dropIt(){
      editPanel.setFloat(null,0);
      viewPanel.setFloat(null,0);
      carrying =false;
   }
   public void stateChanged(ChangeEvent ce){
      if(ce.getSource() == polyIndex ){
         boolean c=carrying;
         updatePolyIndex();
         updatePolyOrient();
         floatHandle=0;
         if(c) carryIt();
         updatePolyAmount();
      }else if(ce.getSource() == polyOrient ){
         updatePolyOrient();
      }
   }
   private void placementsChanged(){
      puz.setStartPosition(puz.getSolution(0));
   }
   public void actionPerformed(ActionEvent ae){
      if(ae.getSource() == clearButton ){
         // clear puz board
         puz.clearBoard();
         placementsChanged();
         editPanel.getBoard().clearPieces();
         editPanel.reset();
         updatePolyIndex();
         updatePolyOrient();
         updatePolyAmount();
      }else if(ae.getSource() == viewPanel ){
         // clicked on viewPanel
         if( carrying ) dropIt();
         else {
            Coord c = viewPanel.getClickedCoord();
            int ori = polyOrient.getIntValue()-1;
            Coord[] cl = getPoly().getOrient(ori);
            int i=0;
            for(i=0; i<cl.length; i++){
               if( c.equals(cl[i]) ) break;
            }
            floatHandle = i<cl.length ? i : 0;
            carryIt();
         }
         updatePolyAmount();
      }else if(ae.getSource() == editPanel ){
         int act = ae.getID();
         Coord c = editPanel.getClickedCoord();
         if( act==2 ){
            System.out.println("clicked "+c);
            // tried to place carried piece
            Polyomino poly = getPoly();
            CoordOri co = new CoordOri(c, polyOrient.getIntValue()-1);
            Board b = puz.getBoard();
            if( poly.canPlace(b,co)){
               poly.place(b,co);
               poly.placeS(co);
               dropIt();
               updatePolyAmount();
               placementsChanged();
            }
         }else if( act<0 ){
            act=-act;
            // find out which piece it is
            Polyomino poly = null;
            int ix;
            for( ix=0; ix<puz.getNumPolyomino(); ix++){
               poly = puz.getPolyomino(ix);
               if( act>=poly.getId() && act<poly.getId()+poly.getMaxAmount() )
                  break;
            }
            if( poly==null ) return;
            act -= poly.getId();

            // retrieve current position/orientation
            CoordOri pc = poly.getPlacement(act);
            int ori = pc.ori;
            c.sub(pc.coord);
            // find which cell of the piece is the clicked one
            Coord[] cls = poly.getOrient(ori);
            int i;
            for( i=0; i<cls.length; i++){
               if( cls[i].equals(c) ) break;
            }
            floatHandle = (i>=cls.length) ? 0 : i;

            // remove from board
            Board b = puz.getBoard();
            poly.remove(b,act);
            placementsChanged();

            // make piece current
            polyIndex.removeChangeListener(this);
            polyIndex.setValue(ix+1);
            polyIndex.addChangeListener(this);
            updatePolyIndex();
            polyOrient.removeChangeListener(this);
            polyOrient.setValue(ori+1);
            polyOrient.addChangeListener(this);
            updatePolyOrient();

            // carry piece
            carryIt();
            updatePolyAmount();
         }
      }
   }
}