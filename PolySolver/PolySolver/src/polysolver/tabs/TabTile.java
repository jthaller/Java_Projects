package polysolver.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
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



public final class TabTile extends MyTab
   implements ActionListener, ChangeListener, DocumentListener
{
   private JButton clearButton, delDupButton, combDupButton;
   private JLabel boardLabel, shapeLabel;
   private GridEditPanel editPanel;
   private MySpinner polyIndex, polyOrient;
   private MyNumberSpinner polyMinAmount, polyMaxAmount;
   private JTextField description = new JTextField();
   private JRadioButton[] rb;

   public TabTile(){
      super("Shapes", "Edit the tile shapes");
      setLayout(new BorderLayout());

      add(description, BorderLayout.NORTH);
      description.getDocument().addDocumentListener(this);

      editPanel = new GridEditPanel(true,false);
      editPanel.setForeground(Color.CYAN);
      editPanel.addActionListener(this);
      add(editPanel);

      //Create the radio buttons.
      Box radioPanel = Box.createVerticalBox();
      rb = new JRadioButton[3];
      String[] rbLabel=new String[]{"None","Rotations","Reflections"};
      ButtonGroup oriButtons = new ButtonGroup();
      for( int i=0; i<3; i++){
         //Create button
         rb[i] = new JRadioButton(rbLabel[i]);
         //Add button to group
         oriButtons.add(rb[i]);
         //Register a listener for the radio buttons.
         rb[i].addActionListener(this);
         // add to panel
         radioPanel.add(rb[i]);
      }

      polyOrient = new MySpinner();
      polyOrient.addChangeListener(this);

      polyMaxAmount = new MyNumberSpinner();
      polyMaxAmount.addChangeListener(this);
      polyMinAmount = new MyNumberSpinner();
      polyMinAmount.addChangeListener(this);

      polyIndex = new MySpinner();
      polyIndex.addChangeListener(this);

      clearButton = new JButton("Delete");
      clearButton.addActionListener(this);
      delDupButton = new JButton("Delete Duplicates");
      delDupButton.addActionListener(this);
      combDupButton = new JButton("Combine Duplicates");
      combDupButton.addActionListener(this);

      boardLabel = new JLabel();
      shapeLabel = new JLabel();
      JPanel labelPanel = new JPanel();
      labelPanel.add(boardLabel);
      labelPanel.add(shapeLabel);

      JPanel controlPanel = new JPanel();

      JLabel label = new JLabel("Piece:");
      controlPanel.add(label);
      controlPanel.add(polyIndex);

      controlPanel.add(new JLabel("Amount"));
      Box amountPanel = Box.createVerticalBox();
      Box amountPanel2 = Box.createHorizontalBox();
      amountPanel2.add(new JLabel("Min:"));
      amountPanel2.add(polyMinAmount);
      amountPanel.add(amountPanel2);
      amountPanel2 = Box.createHorizontalBox();
      amountPanel2.add(new JLabel("Max:"));
      amountPanel2.add(polyMaxAmount);
      amountPanel.add(amountPanel2);
      controlPanel.add(amountPanel);

      controlPanel.add(radioPanel);

      label = new JLabel("Orientation:");
      controlPanel.add(label);
      controlPanel.add(polyOrient);

      controlPanel.add(clearButton);

      JPanel dupPanel = new JPanel();
      dupPanel.add(delDupButton);
      dupPanel.add(combDupButton);

      JPanel southPanel = new JPanel(); add(southPanel, BorderLayout.SOUTH);
      southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.Y_AXIS));
      southPanel.add(labelPanel);
      southPanel.add(controlPanel);
      southPanel.add(dupPanel);
   }
   public void init(){
      IGridType g = puz.getGridType();
      editPanel.setGridType(g);

      description.getDocument().removeDocumentListener(this);
      description.setText(puz.getDescription());
      description.getDocument().addDocumentListener(this);

      polyIndex.removeChangeListener(this);
      polyIndex.setValue(1);
      polyIndex.addChangeListener(this);
      updatePolyIndex();
      loadTile();

      String s = puz.getBoard().getNumBlocksString(g.getNumTileOrbits(), g.getTileOrbits());
      boardLabel.setText("Board contains "+s+" cells.");
      setLabel();
   }
   public void initColors(){
      Color fillColor      = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyFgColour);
      Color bgColor        = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyBgColour);
      Color edgeDarkColor  = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeDarkColour);
      Color edgeLightColor = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeLightColour);
      Color selColor       = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeSelectionColour);

      editPanel.setColors(fillColor, bgColor, edgeDarkColor, edgeLightColor, selColor);
   }

   private void setLabel(){
      // display total number of blocks in shapes
      shapeLabel.setText("Shapes use "+puz.getShapesNumBlocks()+" cells.");
   }
   public void exit(){
      saveTile();
   }
   // get currently selected Polyomino
   private Polyomino getPoly(){
      int i = polyIndex.getIntValue()-1;
      return i==puz.getNumPolyomino() ? null : puz.getPolyomino(i);
   }
   // get currently selected symmetry type
   private int getType(){
      for( int i=0; i<rb.length; i++){
         if( rb[i].isSelected() ) return i;
      }
      return 2;
   }
   // update polyIndex-spinner, and all the rest as well
   private void updatePolyIndex(){

      polyIndex.removeChangeListener(this);
      {
         int p=puz.getNumPolyomino();
         polyIndex.setMinimum(1);
         // set max too large to allow final empty pattern to be filled
         // and new empty pattern to be added
         polyIndex.setMaximum(p+2);
         polyIndex.setTail(p);
         // do not move past final empty pattern
         if( polyIndex.getIntValue() >= p+2 ){
            polyIndex.setValue(p+1);
         }
      }
      polyIndex.addChangeListener(this);

      polyOrient.removeChangeListener(this);
      polyOrient.setValue(1);
      polyOrient.addChangeListener(this);

      updatePolyOrient();
   }
   // update orient-spinner and radiobuttons to reflect new current poly
   private void updatePolyOrient(){
      Polyomino poly = getPoly();
      int m = poly==null? 0 : poly.getNumUserOrient();

      polyOrient.removeChangeListener(this);
      {
         polyOrient.setMinimum(1);
         // set max too large to allow final empty pattern to be filled
         // and new empty pattern to be added
         polyOrient.setMaximum(m+2);
         polyOrient.setTail(m);
         // do not move past final empty pattern
         if( polyOrient.getIntValue() >= m+2 ){
            polyOrient.setValue( m+1 );
         }
      }
      polyOrient.addChangeListener(this);

      polyMaxAmount.removeChangeListener(this);
      polyMinAmount.removeChangeListener(this);
      {
         int currentPolyOrient = polyOrient.getIntValue();
         if( poly!=null ){
            rb[poly.getType()].setSelected(true);
            int n=poly.getMaxAmount();
            int n2=poly.getMinAmount();
            polyMaxAmount.setMinimum(n2);
            polyMaxAmount.setValue(n);
            polyMinAmount.setMaximum(n);
            polyMinAmount.setValue(n2);
            if( currentPolyOrient==m+1 )
               editPanel.setBlockList();
            else
               editPanel.setBlockList(poly.getUserOrient(currentPolyOrient-1),0);
         }else{
            // set default
            rb[puz.getGridType().is3D() ? 1 : 2].setSelected(true);
            polyMaxAmount.setMinimum(0);
            polyMaxAmount.setValue(1);
            polyMinAmount.setMinimum(0);
            polyMinAmount.setMaximum(1);
            polyMinAmount.setValue(0);
            editPanel.setBlockList();
         }
      }
      polyMaxAmount.addChangeListener(this);
      polyMinAmount.addChangeListener(this);
   }
   public void stateChanged(ChangeEvent ce){
      if(ce.getSource() == polyIndex ){
         updatePolyIndex();
         loadTile();
      }else if(ce.getSource() == polyOrient ){
         updatePolyOrient();
         loadTile();
      }else if(ce.getSource() == polyMaxAmount ){
         Polyomino poly = getPoly();
         if(poly!=null){
            poly.setMaxAmount(polyMaxAmount.getIntValue());
            setLabel();
         }
         polyMaxAmount.removeChangeListener(this);
         polyMaxAmount.setMaximum(polyMaxAmount.getIntValue()+1);
         polyMaxAmount.addChangeListener(this);
         polyMinAmount.removeChangeListener(this);
         polyMinAmount.setMaximum(polyMaxAmount.getIntValue());
         polyMinAmount.addChangeListener(this);
      }else if(ce.getSource() == polyMinAmount ){
         Polyomino poly = getPoly();
         if(poly!=null){
            poly.setMinAmount(polyMinAmount.getIntValue());
            setLabel();
         }
         polyMaxAmount.removeChangeListener(this);
         polyMaxAmount.setMinimum(polyMinAmount.getIntValue());
         polyMaxAmount.addChangeListener(this);
      }
   }
   public void actionPerformed(ActionEvent ae){
      if(ae.getSource() == clearButton ){
         editPanel.setBlockList();
         saveTile();
         updatePolyIndex();
      }else if(ae.getSource() == delDupButton ){
         removeDuplicates(false);
         setLabel();
      }else if(ae.getSource() == combDupButton ){
         removeDuplicates(true);
         setLabel();
      }else if(ae.getSource() == editPanel ){
         saveTile();
      }else{// radiobutton
         Polyomino poly = getPoly();
         if(poly!=null) poly.setType(getType());
      }
   }
   // get data from panel and store in puzzle
   private void saveTile(){
      Polyomino poly = getPoly();
      Coord[] shape = editPanel.getBoard().getBlockArray();

      if( poly==null ){
         if( shape.length!=0 ){
            // add new polyomino
            poly = puz.createPolyomino();
            poly.addUserOrient(shape);
            poly.setType(getType());
            poly.setMaxAmount(polyMaxAmount.getIntValue());
            poly.setMinAmount(polyMinAmount.getIntValue());

            // current poly index does not change
            int n= puz.getNumPolyomino();
            polyIndex.removeChangeListener(this);
            polyIndex.setTail( n );
            polyIndex.addChangeListener(this);

            n= poly.getNumUserOrient();
            polyOrient.removeChangeListener(this);
            polyOrient.setTail( n );
            polyOrient.addChangeListener(this);
         }
      }else{
         // edit existing polyomino
         poly.setType(getType());
         poly.setMaxAmount(polyMaxAmount.getIntValue());
         poly.setMinAmount(polyMinAmount.getIntValue());
         int i = polyOrient.getIntValue()-1;
         if( i>=poly.getNumUserOrient() ){
            // add new orient to poly
            poly.addUserOrient(shape);
            // current poly orient does not change
            int n= poly.getNumUserOrient();
            polyOrient.removeChangeListener(this);
            polyOrient.setTail( n );
            polyOrient.addChangeListener(this);
         }else if(shape.length==0){
            // remove current orient from poly
            poly.delUserOrient(i);
            // current poly orient changes to last+1
            int n= poly.getNumUserOrient();
            polyOrient.removeChangeListener(this);
            polyOrient.setValue( n+1 );
            polyOrient.setTail( n );
            polyOrient.addChangeListener(this);

            if(poly.getNumUserOrient()==0){
               // remove whole poly
               puz.removePolyomino(poly);
               n= puz.getNumPolyomino();
               polyIndex.removeChangeListener(this);
               polyIndex.setValue( n+1 );
               polyIndex.setTail( n );
               polyIndex.addChangeListener(this);
            }
         }else{
            // update existing ori, but without changing number
            poly.setUserOrient(i,shape);
         }
      }
      setLabel();
   }
   // get data from puzzle and store in panel
   private void loadTile(){
      Polyomino poly = getPoly();
      if( poly==null ){
         editPanel.setBlockList();
         rb[ puz.getGridType().is3D() ? 1 : 2].setSelected(true);
      }else{
         rb[poly.getType()].setSelected(true);
         int i = polyOrient.getIntValue()-1;
         if( i>=poly.getNumUserOrient() ){
            editPanel.setBlockList();
         }else{
            editPanel.setBlockList(poly.getUserOrient(i),0);
         }
      }
   }
   private void removeDuplicates(boolean combine){
      // save current poly
      Polyomino poly = getPoly();
      // do deed
      puz.removeDuplicates(combine);

      // get new index to be shown
      int k=0;
      if( poly==null ) k=puz.getNumPolyomino();
      else{
         // find current poly in reduced list of shapes
         for(int i=0; i< puz.getNumPolyomino(); i++){
            if( puz.getPolyomino(i).sameShape(poly) ){
               k=i; break;
            }
         }
      }
      // set current poly index
      polyIndex.removeChangeListener(this);
      polyIndex.setValue(k+1);
      updatePolyIndex();
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