package polysolver.tabs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.event.*;

import polysolver.engine.Board;
import polysolver.engine.Coord;
import polysolver.engine.Solution;
import polysolver.gridtypes.GridTypeFactory;
import polysolver.gridtypes.IGridType;



import java.util.*;

public final class GridEditPanel extends JPanel
   implements MouseInputListener
{
   private Color[] colList;
   private boolean editMode, playMode;
   private IGridType gridType;
   private Coord[] floatShape;
   private int floatHandle;
   private boolean mouseInside=true;
   private Board board = new Board(null);

   private Color fillColor = Color.LIGHT_GRAY;
   private Color bgColor = Color.WHITE;
   private Color edgeDarkColor = Color.BLACK;
   private Color edgeLightColor = Color.GRAY;
   private Color selColor = Color.RED;

   public GridEditPanel(boolean e, boolean p){
      editMode=e;
      playMode=p;
      setLayout(null);
      addMouseListener(this);
      addMouseMotionListener(this);
      // set default background colors
      setBackground(bgColor);
      setForeground(fillColor);
   }

// get/set stuff
   public void setNumColours(int n){
      // create list of colours
      colList = new Color[n];
      float h=0, d=1.0f/n;
      for( int i=0; i<n; i++){
         colList[i]=Color.getHSBColor(h,1,1);
         h+=d;
      }
   }
   public void setFloat(Coord[] s, int h){
      if( s==null ){
         floatShape = null;
      }else{
         floatHandle = h;
         floatShape = s;
      }
   }
   public void setGridType(IGridType gt){
      if( gridType==null || gridType.getClass()!=gt.getClass() ){
         gridType = GridTypeFactory.factory(gt.toString());
      }
      gridType.reset();
      board.destroyBoard();
   }
   public IGridType getGridType(){ return gridType; }
   public void setBlockList(){
      board.destroyBoard();
      reset();
   }
   public void setBlockList(Coord[] cl,int colour){
      board.setBoardBlocks(cl,colour);
      reset();
   }
   public void setBoard(Board b, IGridType gt){
      if( gridType==null || gridType.getClass()!=gt.getClass() ){
         gridType = GridTypeFactory.factory(gt.toString());
      }
      board = b;
      reset();
   }
   public void setBoardPosition(Solution s){
      board.setBoardContents(s);
      reset();
   }
   public void reset(){
      gridType.reset();
      mouseOverBlock = false;
      repaint();
   }
   public void setColors( Color fillColor0, Color bgColor0,
                           Color edgeDarkColor0, Color edgeLightColor0, Color selColor0 ){
      fillColor = fillColor0;
      bgColor = bgColor0;
      edgeDarkColor = edgeDarkColor0;
      edgeLightColor = edgeLightColor0;
      selColor = selColor0;

      setBackground(bgColor);
      setForeground(fillColor);
      repaint();
   }
   public Board getBoard(){ return board; }

   public void addBlock(Coord c){
      board.addBlock(c);
   }

// all screen output stuff

   private Point mousePoint = new Point();
   private Coord selectStartCoord=new Coord();
   private Coord selectEndCoord=new Coord();
   private boolean selecting = false;
   private Coord clickedCoord=new Coord();

   public void paintComponent(Graphics g) {
      super.paintComponent(g);  //paint background
      gridType.paintComponent(this,g,board,colList,editMode, edgeDarkColor, edgeLightColor, fillColor, bgColor );
      if(floatShape!=null){
         if(mouseInside){
            // draw floating shape
            g.setColor(selColor);
            gridType.paintCentredOutline(g,floatShape,mousePoint.x,mousePoint.y, floatHandle);
         }
      }else if( editMode ){
         if(selecting){
            g.setColor(selColor);
            gridType.paintOutline(g,selectStartCoord,selectEndCoord);
         }else if(mouseOverBlock){
            g.setColor(selColor);
            gridType.paintOutline(g,selectEndCoord,selectEndCoord);
         }
      }
   }
   public void mousePressed(MouseEvent e) {
      if( editMode ) {
         selecting = gridType.screen2Grid(selectStartCoord, e.getX(),e.getY());
         selectEndCoord.set(selectStartCoord);
      }
      repaint();
   }
   public void mouseDragged(MouseEvent e) {
      if( selecting ){
         // set end selection if it is within range
         boolean n = gridType.screen2Grid(selectEndCoord, e.getX(),e.getY());
         if(n) repaint();
      }
   }
   private boolean mouseOverBlock=false;
   public void mouseMoved(MouseEvent e) {
      if( editMode ){
         boolean n = gridType.screen2Grid(selectEndCoord, e.getX(),e.getY());
         if( n && !selectEndCoord.equals(selectStartCoord)){
            mouseOverBlock=n;
            selectStartCoord.set(selectEndCoord);
            repaint();
         }else if( n!=mouseOverBlock){
            mouseOverBlock=n;
            repaint();
         }
      }else if( floatShape!=null ){
         mousePoint.x = e.getX();
         mousePoint.y = e.getY();
         repaint();
      }
   }
   public void mouseReleased(MouseEvent e) {
      mousePoint.x = e.getX();
      mousePoint.y = e.getY();
      int act=0;
      if( editMode ){
         if(selecting ){
            // toggle all blocks within the selected range
            Coord c = new Coord();
            c.getFirstInRange(selectStartCoord,selectEndCoord);
            do{
               board.toggleBlock(c);
            }while(c.getNextInRange(selectStartCoord,selectEndCoord, gridType.getNumTileTypes()));
            gridType.reset();
            act =1 ;// notify edit made
            selecting = mouseOverBlock = false;
         }
      }else if(playMode){
         boolean n = gridType.screen2Grid(clickedCoord, e.getX(),e.getY());
         int c=-1;
         if(n){
            c=board.getContents(clickedCoord);
            n&=(c>=0);
         }
         if(n){
            // clicked on grid somewhere
            if( floatShape==null && c>0 ){ // pick up piece
               act = -c; // notify picked up placed piece
            }else if( floatShape!=null && c==0 ){ // clicked on empty cell
               // get position
               clickedCoord.sub(floatShape[floatHandle]);
               if( clickedCoord.tile==0 )
                  act = 2; // notify tried to place piece
            }
         }
      }else{
         gridType.screen2Grid(clickedCoord, e.getX(),e.getY());
      }
      fireActionPerformed(act);
      repaint();
   }
   public Coord getClickedCoord(){ return clickedCoord; }

   public void mouseEntered(MouseEvent e) {
      mouseInside=true;
   }
   public void mouseExited(MouseEvent e) {
      mouseInside=false;
      repaint();
   }
   public void mouseClicked(MouseEvent e) {}



   // changeListener implementation
   ArrayList<ActionListener> actionListeners = new ArrayList<ActionListener>();
   public void addActionListener(ActionListener al) {
      if( !actionListeners.contains(al) )
         actionListeners.add(al);
   }
   public void removeActionListener(ActionListener al){
      actionListeners.remove(al);
   }
   public ActionListener[] getActionListeners() {
       return (ActionListener[])(actionListeners.toArray());
   }
   protected void fireActionPerformed(int n) {
      int i=actionListeners.size();
      if(i>0){
         ActionEvent e = new ActionEvent(this,n,"");
         for (i--; i>=0; i--) {
            ActionListener cl = actionListeners.get(i);
            cl.actionPerformed(e);
         }
      }
   }
}
