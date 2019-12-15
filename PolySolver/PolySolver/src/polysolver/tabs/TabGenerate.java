package polysolver.tabs;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import polysolver.PolySolver;
import polysolver.engine.Coord;
import polysolver.engine.Polyomino;
import polysolver.gridtypes.IGridType;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public final class TabGenerate extends MyTab
   implements ActionListener, ChangeListener, DocumentListener
{
   private JButton clearListButton, addListButton, joinButton, addButton, clearTileButton, delTileButton, addNeighButton;
   private GridEditPanel editPanel, viewPanel;
   private MySpinner polyIndex;
   private JRadioButton[] rbTile, rbList;
   private JLabel boardLabel, shapeLabel, listLabel;
   private JTextField description = new JTextField();
   private ArrayList<Polyomino> polyList = new ArrayList<Polyomino>();

   public TabGenerate(){
      super( "Generate", "Generate tile sets" );
      setLayout(new BorderLayout());

      add(description, BorderLayout.NORTH);
      description.getDocument().addDocumentListener(this);

      JPanel centre = new JPanel();
      centre.setLayout(new BoxLayout(centre,BoxLayout.X_AXIS));
      editPanel = new GridEditPanel(true,false);
      editPanel.setForeground(Color.CYAN);
      editPanel.addActionListener(this);
      centre.add(editPanel);
      viewPanel = new GridEditPanel(false,false);
      viewPanel.setForeground(Color.CYAN);
      centre.add(viewPanel);
      add(centre);

      //Create the radio buttons.
      JPanel tileRadioPanel = new JPanel();
      JPanel listRadioPanel = new JPanel();
      rbTile = new JRadioButton[3];
      rbList = new JRadioButton[3];
      tileRadioPanel.setLayout(new BoxLayout(tileRadioPanel, BoxLayout.Y_AXIS));
      listRadioPanel.setLayout(new BoxLayout(listRadioPanel, BoxLayout.Y_AXIS));
      String[] rbLabel=new String[]{"None","Rotations","Reflections"};
      ButtonGroup listOriButtons = new ButtonGroup();
      ButtonGroup tileOriButtons = new ButtonGroup();
      for( int i=0; i<3; i++){
         //Create button
         rbTile[i] = new JRadioButton(rbLabel[i]);
         rbList[i] = new JRadioButton(rbLabel[i]);
         //Add button to group
         tileOriButtons.add(rbTile[i]);
         listOriButtons.add(rbList[i]);
         //Register a listener for the radio buttons.
         rbTile[i].addActionListener(this);
         rbList[i].addActionListener(this);
         // add to panel
         tileRadioPanel.add(rbTile[i]);
         listRadioPanel.add(rbList[i]);
      }
      rbList[2].setSelected(true);
      rbTile[2].setSelected(true);

      polyIndex = new MySpinner();
      polyIndex.addChangeListener(this);

      clearTileButton = new JButton("Clear");
      clearTileButton.addActionListener(this);
      delTileButton= new JButton("Delete Shape");
      delTileButton.addActionListener(this);
      addNeighButton= new JButton("Expand");
      addNeighButton.addActionListener(this);
      clearListButton= new JButton("Clear List");
      clearListButton.addActionListener(this);
      addListButton= new JButton("Add List");
      addListButton.addActionListener(this);
      joinButton= new JButton("Join");
      joinButton.addActionListener(this);
      addButton= new JButton("Add");
      addButton.addActionListener(this);

      JPanel tileButtonPanel = new JPanel();
      tileButtonPanel.setLayout(new BoxLayout(tileButtonPanel,BoxLayout.Y_AXIS));
      tileButtonPanel.add(clearTileButton);
      tileButtonPanel.add(addNeighButton);
      tileButtonPanel.add(addButton);
      tileButtonPanel.add(joinButton);

      JPanel listButtonPanel = new JPanel();
      listButtonPanel.setLayout(new BoxLayout(listButtonPanel,BoxLayout.Y_AXIS));
      listButtonPanel.add(delTileButton);
      listButtonPanel.add(clearListButton);
      listButtonPanel.add(addListButton);

      JPanel controlPanel = new JPanel();
      controlPanel.add(new JLabel("Tile:"));
      controlPanel.add(tileRadioPanel);
      controlPanel.add(tileButtonPanel);
      controlPanel.add(new JLabel("List:"));
      controlPanel.add(listRadioPanel);
      controlPanel.add(polyIndex);
      controlPanel.add(listButtonPanel);

      JPanel labelPanel = new JPanel();
      labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.Y_AXIS));
      boardLabel = new JLabel();
      shapeLabel = new JLabel();
      listLabel = new JLabel();
      labelPanel.add(boardLabel);
      labelPanel.add(shapeLabel);
      labelPanel.add(listLabel);

      JPanel southPanel = new JPanel();
      southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.Y_AXIS));
      southPanel.add(labelPanel);
      southPanel.add(controlPanel);

      add(southPanel, BorderLayout.SOUTH);
   }
   public void init(){
      IGridType gt = editPanel.getGridType();
      // check if grid has changed
      if( gt==null || gt.getClass() != puz.getGridType().getClass() ){
         gt = puz.getGridType();
         editPanel.setGridType(gt);
         viewPanel.setGridType(gt);
         editPanel.setBlockList();
         viewPanel.setBlockList();
         polyList.clear();
         polyIndex.removeChangeListener(this);
         polyIndex.setMinimum(1);
         polyIndex.addChangeListener(this);
         setPolyIndex(1);
         int d = gt.is3D() ? 1 : 2;
         rbList[d].setSelected(true);
         rbTile[d].setSelected(true);
      }

      description.getDocument().removeDocumentListener(this);
      description.setText(puz.getDescription());
      description.getDocument().addDocumentListener(this);

      String s = puz.getBoard().getNumBlocksString(puz.getGridType().getNumTileOrbits(), puz.getGridType().getTileOrbits());
      boardLabel.setText("Board contains "+s+" cells.");
      setLabel();
      updateButtons();
   }
   public void initColors(){
      Color fillColor      = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyFgColour);
      Color bgColor        = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyBgColour);
      Color edgeDarkColor  = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeDarkColour);
      Color edgeLightColor = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeLightColour);
      Color selColor       = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeSelectionColour);

      editPanel.setColors(fillColor, bgColor, edgeDarkColor, edgeLightColor, selColor);
      viewPanel.setColors(fillColor, bgColor, edgeDarkColor, edgeLightColor, selColor);
   }
   private void setLabel(){
      // display total number of blocks in shapes and in list
      int s = puz.getNumPolyomino();
      shapeLabel.setText("Puzzle has "+s+(s==1?" shape":" shapes")+" and uses "+puz.getShapesNumBlocks()+" cells.");
      s = polyList.size();
      listLabel.setText("List has "+s+(s==1?" shape":" shapes")+" and uses "+puz.getShapesNumBlocks(polyList)+" cells.");
   }
   public void exit(){}
   // get currently selected Polyomino
   private Polyomino getPoly(){
      int i = polyIndex.getIntValue()-1;
      return i>=polyList.size() ? null : polyList.get(i);
   }
   // get currently selected symmetry type
   private int getSym(JRadioButton[] rb){
      for( int i=0; i<rb.length; i++){
         if( rb[i].isSelected() ) return i;
      }
      return 2;
   }
   private int getListSym(){ return getSym(rbList); }
   private int getTileSym(){ return getSym(rbTile); }

   // update polyIndex-spinner, and all the rest as well
   private void setPolyIndex(int v){
      polyIndex.removeChangeListener(this);
      int m = polyList.size();
      polyIndex.setMaximum(m==0?1:m);
      polyIndex.setTail(m);
      polyIndex.setValue(v<=m ? v : m==0 ? 1 : m );
      polyIndex.addChangeListener(this);
      // display poly
      Polyomino poly = getPoly();
      if( poly!=null ){
         viewPanel.setBlockList(poly.getUserOrient(0),1);
      }else{
         viewPanel.setBlockList();
      }
   }
   public void stateChanged(ChangeEvent ce){
      if(ce.getSource() == polyIndex ){
         setPolyIndex(polyIndex.getIntValue());
      }
   }
   public void actionPerformed(ActionEvent ae){
      // ;
      if(ae.getSource() == clearListButton ){
         polyList.clear();
         setPolyIndex(1);
         updateButtons();
         setLabel();
      }else if(ae.getSource() == addListButton ){
         for( int i=0; i<polyList.size(); i++){
            Polyomino poly1 = polyList.get(i);

            Polyomino poly2 = puz.createPolyomino();
            poly2.addUserOrient(poly1.getUserOrient(0));
            poly2.setMaxAmount(1);
            poly2.setType(getListSym());
         }
         updateButtons();
         setLabel();
      }else if(ae.getSource() == joinButton ){
         joinTile();
         updateButtons();
         setPolyIndex(polyIndex.getIntValue());
         setLabel();
      }else if(ae.getSource() == addButton ){
         addTile();
         updateButtons();
         setPolyIndex(polyList.size());
         setLabel();
      }else if(ae.getSource() == clearTileButton ){
         editPanel.setBlockList();
         updateButtons();
      }else if(ae.getSource() == addNeighButton ){
         addNeighbours();
      }else if(ae.getSource() == delTileButton ){
         polyList.remove(getPoly());
         setPolyIndex(polyIndex.getIntValue());
         setLabel();
      }else if(ae.getSource() == editPanel ){
         // tile changed
         updateButtons();
      }else{
         // radiobutton
      }
   }
   private void updateButtons(){
      boolean e = ! editPanel.getBoard().isEmpty();
      clearTileButton.setEnabled(e);
      addNeighButton.setEnabled(e);
      addButton.setEnabled(e);
      joinButton.setEnabled(e);
      e = polyList.size()!=0;
      delTileButton.setEnabled(e);
      clearListButton.setEnabled(e);
      addListButton.setEnabled(e);
   }
   private void addTile(){
      Coord[] bl = editPanel.getBoard().getBlockArray();
      Polyomino poly2 = new Polyomino(null);
      poly2.addUserOrient(bl);
      poly2.setMaxAmount(1);
      poly2.setType(getListSym());
      poly2.initialiseOrients(puz.getGridType());
      polyList.add(poly2);
   }
   private void addNeighbours(){
      IGridType gt = puz.getGridType();
      Coord[] bl = editPanel.getBoard().getBlockArray();
      for( int i=0; i<bl.length; i++){
         int j=0;
         while(true){
            Coord c=gt.getAdjacent(bl[i],j);
            if( c==null ) break;
            editPanel.addBlock(c);
            j++;
         }
      }
      editPanel.reset();
      editPanel.repaint();
   }

   private void joinTile(){
      if( polyList.size()==0 ){ addTile(); return; }
      Coord[] bl = editPanel.getBoard().getBlockArray();
      if(bl.length==0) return; // shouldn't happen
      // get tile polyomino
      Polyomino poly1 = new Polyomino(null);
      poly1.addUserOrient(bl);
      poly1.setMaxAmount(1);
      // generate all rotations/reflections
      poly1.setType(getTileSym());
      poly1.initialiseOrients(puz.getGridType());

      // generate list of all joined versions of tile with shapes from list
      ArrayList<Polyomino> newList = new ArrayList<Polyomino>();
      for( int i=0; i<polyList.size(); i++){
         Polyomino poly2 = polyList.get(i);
         joinTile(newList,poly1,poly2);
      }

      // replace old list by new one
      polyList.clear(); // make easier on gc
      polyList = newList;
   }
   private void joinTile(ArrayList<Polyomino> newList,Polyomino poly1,Polyomino poly2){
      for(int i=0; i<poly1.getNumOrient(); i++){
         for(int j=0; j<poly2.getNumOrient(); j++){
            joinTile(newList, poly1.getOrient(i), poly2.getOrient(j) );
         }
      }
   }
   private void joinTile(ArrayList<Polyomino> newList,Coord[] shape1,Coord[] shape2){
      IGridType gt = puz.getGridType();
      // generate list of adjacent tiles of shape 2
      ArrayList<Coord> adj = new ArrayList<Coord>();
      for( int i=0; i<shape2.length; i++){
         int j=0;
         while(true){
            Coord c=gt.getAdjacent(shape2[i],j);
            if( c==null ) break;
            if( !adj.contains(c) ) adj.add(c);
            j++;
         }
      }
      for( int i=0; i<shape2.length; i++) adj.remove(shape2[i]);

      // try all ways to cover these neighbours with a copy of shape1
      Coord t = new Coord();
      for( int i=0; i<adj.size(); i++){
         Coord a = adj.get(i);
         for(int j=0; j<shape1.length; j++){
            // get translation needed to shift shape1[j] to cover a.
            if( a.tile == shape1[j].tile ){
               t.set(a);
               t.sub(shape1[j]);
               t.tile = 0;
               joinTile(newList, t, shape1, shape2);
            }
         }
      }
   }
   private void joinTile(ArrayList<Polyomino> newList,Coord tr, Coord[] shape1,Coord[] shape2){
      // shift shape1
      for( int i=0; i<shape1.length; i++) shape1[i].add(tr);
      // check for overlap
      boolean overlap=false;
      for( int i=0; i<shape1.length && !overlap; i++){
         for( int j=0; j<shape2.length && !overlap; j++){
            overlap |= shape1[i].equals(shape2[j]);
         }
      }
      if( !overlap ){
         // join the two lists
         Coord[] newShape = new Coord[shape1.length+shape2.length];
         int j=0;
         for( int i=0; i<shape1.length; i++, j++)
            newShape[j]=new Coord(shape1[i]);
         for( int i=0; i<shape2.length; i++, j++)
            newShape[j]=new Coord(shape2[i]);

         // create polyomino
         Polyomino poly = new Polyomino(null);
         poly.addUserOrient(newShape);
         poly.setMaxAmount(1);
         poly.setType(getListSym());
         poly.initialiseOrients(puz.getGridType());
         // check if it is a new one
         boolean found=false;
         for( int i=0; !found && i<newList.size(); i++){
            Polyomino poly2=newList.get(i);
            if( poly.sameShape(poly2) ) found=true;
         }
         // add if it is new
         if( !found ) newList.add(poly);
      }

      // unshift shape1
      for( int i=0; i<shape1.length; i++) shape1[i].sub(tr);
   }

   public void changedUpdate(DocumentEvent arg0) {
      puz.setDescription(description.getText());
   }
   public void insertUpdate(DocumentEvent arg0) {
      puz.setDescription(description.getText());
   }
   public void removeUpdate(DocumentEvent arg0) {
      puz.setDescription(description.getText());
   }
}