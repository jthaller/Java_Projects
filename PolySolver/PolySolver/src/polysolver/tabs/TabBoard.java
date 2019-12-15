package polysolver.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import polysolver.PolySolver;

public final class TabBoard extends MyTab
   implements ActionListener, DocumentListener
{
   private JPanel controlPanel;
   private JLabel boardLabel, shapeLabel;
   private JButton clearButton;
   private GridEditPanel editPanel;
   private JTextField description = new JTextField();

   public TabBoard(){
      super( "Board", "Edit the board" );
      setLayout(new BorderLayout());

      add(description, BorderLayout.NORTH);
      description.getDocument().addDocumentListener(this);

      editPanel = new GridEditPanel(true,false); add(editPanel);
      editPanel.addActionListener(this);
      editPanel.setForeground(Color.LIGHT_GRAY);

      boardLabel=new JLabel();
      shapeLabel=new JLabel();

      clearButton = new JButton("Clear");
      clearButton.addActionListener(this);

      controlPanel = new JPanel();
      controlPanel.add(boardLabel);
      controlPanel.add(shapeLabel);
      controlPanel.add(clearButton);

      add(controlPanel, BorderLayout.SOUTH);
   }
   public void init(){
      // get data from puzzle and store in panel
      puz.clearBoard();
      editPanel.setBoard(puz.getBoard(), puz.getGridType());
      shapeLabel.setText("Shapes use "+puz.getShapesNumBlocks()+" cells.");
      description.getDocument().removeDocumentListener(this);
      description.setText(puz.getDescription());
      description.getDocument().addDocumentListener(this);
      setLabel();
   }
   public void exit(){}
   public void initColors(){
      Color fillColor      = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyFgColour);
      Color bgColor        = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyBgColour);
      Color edgeDarkColor  = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeDarkColour);
      Color edgeLightColor = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeLightColour);
      Color selColor       = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeSelectionColour);

      editPanel.setColors(fillColor, bgColor, edgeDarkColor, edgeLightColor, selColor);
   }
   public void actionPerformed(ActionEvent ae){
      if(ae.getSource() == clearButton ){
         editPanel.setBlockList();
         setLabel();
      }else if(ae.getSource() == editPanel ){
         setLabel();
      }
   }

   private void setLabel(){
      String s = editPanel.getBoard().getNumBlocksString(puz.getGridType().getNumTileOrbits(), puz.getGridType().getTileOrbits());
      boardLabel.setText("Board contains "+s+" cells.");
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