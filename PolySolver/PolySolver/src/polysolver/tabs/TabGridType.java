package polysolver.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.Box;
import javax.swing.BoxLayout;

import polysolver.PolySolver;
import polysolver.gridtypes.GridTypeFactory;
import polysolver.gridtypes.IGridType;



public final class TabGridType extends MyTab
   implements ActionListener
{
   class GridIcon implements Icon{
      int width = 300;
      int height = 300;
      IGridType gridType;
      public int getIconHeight() { return height; }
      public int getIconWidth() { return width; }
      public void paintIcon(Component c, Graphics g, int x, int y) {
         g.setColor(c.getBackground());
         g.fillRect(x,y,width,height);
         g.setColor(c.getForeground());
         gridType.paintIcon(g, x, y, width, height);
      }
   }


   private JLabel iconLabel, shapeLabel, boardLabel;
   private GridIcon icon = new GridIcon();
   private JRadioButton[] rb;
   private JButton shapeButton, boardButton;
   private ButtonGroup typesButtons;
   private IGridType[] gridTypes;

   public TabGridType(){
      super("Grid type", "Choose the type of grid");

      //Create the radio buttons.
      JPanel radioPanel = new JPanel();
      gridTypes = new IGridType[GridTypeFactory.numGridTypes()];
      rb = new JRadioButton[gridTypes.length];
      radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
      typesButtons = new ButtonGroup();
      for( int i=0; i<gridTypes.length; i++){
         // create instance of this gridType
         gridTypes[i] = GridTypeFactory.factory(i);
         //Create button
         rb[i] = new JRadioButton(gridTypes[i].toString());
         //Add button to group
         typesButtons.add(rb[i]);
         //Register a listener for the radio buttons.
         rb[i].addActionListener(this);
         // add to panel
         radioPanel.add(rb[i]);
      }
      // set default
      rb[0].setSelected(true);

      iconLabel = new JLabel();
      iconLabel.setBackground(Color.WHITE);
      iconLabel.setIcon(icon);

      shapeLabel = new JLabel();
      shapeButton = new JButton("Delete all shapes");
      shapeButton.addActionListener(this);
      boardLabel = new JLabel();
      boardButton = new JButton("Delete the board");
      boardButton.addActionListener(this);

      JPanel puzzlePanel = new JPanel();
      puzzlePanel.setLayout(new BoxLayout(puzzlePanel, BoxLayout.Y_AXIS));
      puzzlePanel.add(shapeLabel);
      puzzlePanel.add(shapeButton);
      puzzlePanel.add(boardLabel);
      puzzlePanel.add(boardButton);

      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

      add(radioPanel);
      add(Box.createHorizontalGlue());
      add(iconLabel);
      add(Box.createHorizontalGlue());
      add(puzzlePanel);
   }
   public void init(){
      setLabels();
      if( puz.getGridType()==null){
         setIcon(0);
         rb[0].setSelected(true);
      }else{
         for(int i=0; i<gridTypes.length; i++ ){
            if( gridTypes[i].getClass() == puz.getGridType().getClass() ) {
               setIcon(i);
               rb[i].setSelected(true);
               break;
            }
         }
      }
   }
   public void initColors(){
      Color bgColor        = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyBgColour);
      Color edgeDarkColor  = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeDarkColour);

      iconLabel.setBackground(bgColor);
      iconLabel.setForeground(edgeDarkColor);
      repaint();
   }
   protected void setLabels(){
      boolean enable=true;

      int n=puz.getNumPolyomino();
      shapeButton.setEnabled(n!=0);
      enable &= (n==0);
      shapeLabel.setText(  (n==0? "No" : n+"")+
                     (n==1? " shape": " shapes")+
                     " defined.");

      boolean empt = puz.getBoard().isEmpty();
      boardButton.setEnabled(!empt);
      enable &= empt;
      String s = puz.getBoard().getNumBlocksString(puz.getGridType().getNumTileOrbits(), puz.getGridType().getTileOrbits());
      boardLabel.setText( empt ? "No board defined." :
                  "Board with "+s+" cells defined.");

      for(int i=0; i<rb.length; i++ ) rb[i].setEnabled(enable);
   }
   public void exit(){
      for( int i=0; i<gridTypes.length; i++){
         if( rb[i].isSelected() ) {
            puz.setGridType(gridTypes[i]);
         }
      }
   }
   private void setIcon(int i){
      icon.gridType = gridTypes[i];
      iconLabel.repaint();
   }
   public void actionPerformed(ActionEvent ae){
      if( ae.getSource()==boardButton ){
         puz.getBoard().destroyBoard();
         setLabels();
      }else if( ae.getSource()==shapeButton ){
         while( puz.getNumPolyomino()>0 ){
            puz.removePolyomino(puz.getPolyomino(0));
         }
         setLabels();
      }else
      for( int i=0; i<rb.length; i++){
         if( rb[i].isSelected() ) {
            setIcon(i);
         }
      }
   }
}