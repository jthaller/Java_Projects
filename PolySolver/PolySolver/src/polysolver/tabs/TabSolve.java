package polysolver.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import polysolver.PolySolver;
import polysolver.engine.Board;
import polysolver.engine.Puzzle;



public final class TabSolve extends MyTab
   implements ActionListener, ChangeListener
{
   private GridEditPanel mainView;
   private JButton solveButton;
   private JSlider slider;
   private ButtonGroup showButtons;
   private JRadioButton[] rb;
   private JLabel sliderMaxLabel;
   private JLabel timerLabel;
   private JTextField description = new JTextField();
   private Timer progressTimer;

   public TabSolve(){
      super("Solve", "Let program solve the puzzle.");

      setLayout(new BorderLayout());

      add(description, BorderLayout.NORTH);
      description.setEditable(false);

      mainView = new GridEditPanel(false,false);
      mainView.setForeground(Color.YELLOW);
      add(mainView);

      solveButton = new JButton("Solve");
      solveButton.setEnabled(false);
      solveButton.addActionListener(this);

      //Create the radio buttons.
      JPanel radioPanel = new JPanel();
      rb = new JRadioButton[2];
      radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
      String[] rbLabel=new String[]{"Show progress","Show solution"};
      showButtons = new ButtonGroup();
      for( int i=0; i<2; i++){
         //Create button
         rb[i] = new JRadioButton(rbLabel[i]);
         //Add button to group
         showButtons.add(rb[i]);
         //Register a listener for the radio buttons.
         rb[i].addActionListener(this);
         // set default
         if(i==1) rb[i].setSelected(true);
         // add to panel
         radioPanel.add(rb[i]);
         rb[i].setEnabled(false);
      }

      slider = new JSlider(0,0);
      sliderMaxLabel=new JLabel();
      slider.setMinimum(1);
      slider.setMinimum(1);
      slider.setPaintTicks(true);
      slider.setPaintLabels(false);
      slider.setMajorTickSpacing(5);
      slider.setMinorTickSpacing(1);
      slider.setSnapToTicks(false);
      slider.addChangeListener(this);
      slider.setEnabled(false);

      JPanel controlPanel = new JPanel();
      controlPanel.add(solveButton);
      controlPanel.add(radioPanel);
      controlPanel.add(slider);
      controlPanel.add(sliderMaxLabel);
      timerLabel = new JLabel(" ");
      JPanel jp = new JPanel(new BorderLayout());
      jp.add(controlPanel);
      jp.add(timerLabel, BorderLayout.NORTH);
      add(jp, BorderLayout.SOUTH);
   }
   public Puzzle getPuzzle(){return puz; }
   public void init(){
      puz.prepareSolve();

      Board b = new Board(null);
      b.setBoardBlocks(puz.getBoard().getBlockArray(), 0);
      mainView.setBoard(b, puz.getGridType());
      mainView.setNumColours(puz.getNumPieces());

      description.setText(puz.getDescription());

      puz.setActionListener(this);
      updateControls();
      updateMain();
   }
   public void exit(){
      if( puz.isRunning() ) puz.stopSolve();
   }
   public void initColors(){
      Color fillColor      = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyFgColour);
      Color bgColor        = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyBgColour);
      Color edgeDarkColor  = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeDarkColour);
      Color edgeLightColor = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeLightColour);
      Color selColor       = PolySolver.settingsManager.getColorSetting(PolySolver.settingKeyEdgeSelectionColour);

      mainView.setColors(fillColor, bgColor, edgeDarkColor, edgeLightColor, selColor);
   }
   public void stateChanged(ChangeEvent ce){
      if( ce.getSource() == slider ){
         if (!slider.getValueIsAdjusting()) {
            // only update main if showing solution
            if( rb[1].isSelected() ){
               updateMain();
            }
         }
         updateLabel();
      }
   }
   public void actionPerformed(ActionEvent ae){
      if( ae.getSource() == solveButton ){
         if( puz.isRunning() ){
            aborted = true;
            puz.stopSolve();
         }else{
            new Thread(puz).start();
            // action event when solving starts will update controls
            initClock();
         }
      }else if( ae.getSource() == puz ){
         // ai.getID ==   0=found solution, 1=stop solve, 2=start solve
         updateControls();
         if( ae.getID()==1 ){
            outitClock(aborted);
         }else if( ae.getID()==0 && firstFound==null){
            firstFound = timeToString(System.currentTimeMillis() - startTime);
            updateClock("Elapsed");
         }
      }else if( ae.getSource() == progressTimer ){
         updateClock("Elapsed");
      }else{// radiobutton
         updateControls();
         updateMain();
      }
   }

   static final private int ticksTillMainUpdateInit = 14;
   private int ticksTillMainUpdate;
   private long startTime;
   private String firstFound = null;
   private boolean aborted;
   private void initClock(){
      ticksTillMainUpdate = 1;
      aborted = false;
      progressTimer = new Timer(50,this);
      progressTimer.start();
      startTime = System.currentTimeMillis();
      firstFound = null;
   }
   private void outitClock(boolean premature){
      if( progressTimer==null ) return;
      progressTimer.stop();
      progressTimer = null;
      updateControls();
      updateMain();

      updateClock(premature ? "Aborted" : "Completed");
      JOptionPane.showMessageDialog(this, "The search has been "+(premature ? "aborted." : "completed."),
            "Search finished", premature ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
   }
   private void updateClock(String state){
      ticksTillMainUpdate--;
      if( ticksTillMainUpdate==0 ){
         ticksTillMainUpdate = ticksTillMainUpdateInit;
         updateMain();
      }

      long elapsed = System.currentTimeMillis() - startTime;
      String s = state + ": " + timeToString(elapsed);
      if( firstFound!=null ){
         s += "   First Solution: "+firstFound;
         // calculate average times per solution
         long avg = elapsed / puz.getNumSolutions();
         s += "  Average: " + timeToString(avg);
      }
      timerLabel.setText(s);

   }
   private String timeToString(long t0){
      long t = t0;
      long ms = t%1000;
      t /= 1000;
      long s = t%60;
      t /= 60;
      long m = t%60;
      t /= 60;
      long h = t%24;
      t /= 24;
      String str = "";
      if( t>1 ) str += t+" days ";
      else if( t==1 ) str += t+" day ";
      if( h<10 ) str+="0";
      str += h + ":";
      if( m<10 ) str+="0";
      str += m + ":";
      if( s<10 ) str+="0";
      str += s + ".";
      str += ms/100;
      return str;
   }

   private void updateControls(){
      solveButton.setText( puz.isRunning()? "Abort":"Solve");
      solveButton.setEnabled(true);

      int p = puz.getNumSolutions();
      slider.removeChangeListener(this);
      // first do if showButtons
      if( p==0 ){
         rb[0].setSelected(true);
         rb[0].setEnabled(true);
         rb[1].setEnabled(false);
      }else if( !puz.isRunning() ){
         rb[1].setSelected(true);
         rb[0].setEnabled(false);
         rb[1].setEnabled(true);
      }else{ // both options are possible
         rb[0].setEnabled(true);
         rb[1].setEnabled(true);
      }
      slider.setEnabled(p>1 && rb[1].isSelected() );

      // do slider
      if(p==0){
         slider.setMinimum(0);
         slider.setMaximum(0);
         slider.setValue(0);
      }else{
         slider.setMinimum(1);
         slider.setMaximum(p);
         if( p==1 ){
            slider.setValue(1);
            slider.setMinorTickSpacing(1);
            slider.setMajorTickSpacing(1);
         }else if( p<=10){
            slider.setMajorTickSpacing(2);
            slider.setMinorTickSpacing(1);
         }else if(p<=20){
            slider.setMajorTickSpacing(5);
            slider.setMinorTickSpacing(1);
         }else if( p<=50){
            slider.setMajorTickSpacing(10);
            slider.setMinorTickSpacing(2);
         }else if( p<=100){
            slider.setMajorTickSpacing(20);
            slider.setMinorTickSpacing(5);
         }else if( p<=200){
            slider.setMajorTickSpacing(50);
            slider.setMinorTickSpacing(10);
         }else if( p<=500){
            slider.setMajorTickSpacing(100);
            slider.setMinorTickSpacing(20);
         }else if( p<=1000){
            slider.setMajorTickSpacing(200);
            slider.setMinorTickSpacing(50);
         }else if( p<=2000){
            slider.setMajorTickSpacing(500);
            slider.setMinorTickSpacing(100);
         }else if( p<=5000){
            slider.setMajorTickSpacing(1000);
            slider.setMinorTickSpacing(200);
         }else if( p<=10000){
            slider.setMajorTickSpacing(2000);
            slider.setMinorTickSpacing(500);
         }else if( p<=20000){
            slider.setMajorTickSpacing(5000);
            slider.setMinorTickSpacing(1000);
         }else{
            slider.setMajorTickSpacing(10000);
            slider.setMinorTickSpacing(2000);
         }
      }
      slider.addChangeListener(this);
      updateLabel();
   }
   private void updateLabel(){
      sliderMaxLabel.setText(slider.getValue()+"/"+puz.getNumSolutions());
   }

   private void updateMain(){
      int i=( rb[0].isSelected() || puz.getNumSolutions()==0 ) ? 0 : slider.getValue();
      mainView.setBoardPosition( puz.getSolution(i) );
   }
}