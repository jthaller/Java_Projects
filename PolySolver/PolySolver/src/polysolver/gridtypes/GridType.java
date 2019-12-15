package polysolver.gridtypes;


import java.awt.*;

import polysolver.engine.Board;
import polysolver.engine.Coord;

abstract class GridType
   implements IGridType
{
   private String name;
   protected Coord[][] adjOffset;
   protected int[][] tiles;
   protected int[] vertexx;
   protected int[] vertexy;
   private int[] tileOrbit;
   private int numTileOrbit;
   protected Coord firstShown = new Coord();
   protected Coord lastShown = new Coord();

   private int[] tileCentreX;
   private int[] tileCentreY;

   protected int blockSize=0;
   private int width, height;
   protected int offsetX, offsetY;

   private boolean is3D = false;

   private final static Coord border2D=new Coord(2,2,0); // minimal grid border around used blocks
   private final static Coord border3D=new Coord(2,2,1,0); // minimal grid border around used blocks

   protected GridType(String nm, int numRot, boolean hasMirror, boolean is3dim, int numVert, int[][] tilesInit,
                         Coord[][] adjOffsetInit, int[] tileOrbitInit ){
      name = nm;
      numRotate = numRot;
      numReflect = hasMirror ? numRot*2 : numRot;
      is3D = is3dim;
      numTileTypes = tilesInit.length;
      tiles = tilesInit;
      adjOffset = adjOffsetInit;
      tileOrbit = tileOrbitInit;
      vertexx = new int[numVert];
      vertexy = new int[numVert];
      tileCentreX = new int[tiles.length];
      tileCentreY = new int[tiles.length];
      numTileOrbit = 0;
      for( int i=0; i<tileOrbit.length; i++ ) numTileOrbit = Math.max(numTileOrbit,tileOrbit[i]);
      numTileOrbit++;
   }

   final public void reset(){ blockSize=0; }
   final public boolean is3D(){return is3D;}
   final public int getNumRotate(){return numRotate;}
   final public int getNumReflect(){return numReflect;}
   final public int getNumTileTypes(){return numTileTypes;}
   final public int[] getTileOrbits(){return tileOrbit;}
   final public int getNumTileOrbits(){return numTileOrbit;}

   // rotate and/or reflect c around fixed centre, rotation r.
   final public void getRotate(Coord[] c,int r){
      for(int i=0; i<c.length; i++) getRotate(c[i],r);
      Coord.normalise(c);
   }
   
   abstract public void paintIcon(Graphics g, int x,int y, int w, int h);


   // paint outline of single tile, where ox,oy is screen location of (0,0,0) tile block.
   final protected void paintTileOutline(Graphics g, Coord c,int ox,int oy ){
      int x = ox + getScreenXoffset(c);
      int y = oy + getScreenYoffset(c);
      for( int side=0; side<tiles[c.tile].length; side++ ){
         drawTileSide(g, c.tile, side, x, y );
      }
   }

   final public void paintComponent(Component comp, Graphics g, Board board, Color[] colList, boolean showVoid,
         Color edgeDarkColor, Color edgeLightColor, Color fillColor, Color bgColor ){

      if( blockSize==0 || width!=comp.getWidth() || height!=comp.getHeight() ){
         recalcSettings(comp.getWidth(), comp.getHeight(), board, showVoid);
         if( blockSize==0 ) return;
      }

      paintBoard( g, board, colList, showVoid, edgeDarkColor, edgeLightColor, fillColor, bgColor );
   }

   // Draw tiles such that h-th tile centred on x,y.
   final public void paintCentredOutline(Graphics g, Coord[] cl, int x, int y, int h ){
      int x0 = x - tileCentreX[cl[h].tile];
      int y0 = y - tileCentreY[cl[h].tile];
      Coord temp = new Coord();
      for(int i=0; i<cl.length; i++){
         temp.set(cl[i]);
         temp.sub(cl[h]);
         temp.tile = cl[i].tile;
         paintTileOutline(g,temp,x0,y0);
      }
   }

   final public void paintOutline( Graphics g, Coord selStart, Coord selEnd ){
      if( blockSize==0 ) return;
      if( selStart==null || selEnd==null ) return;

      Coord c = new Coord();
      Coord temp = new Coord();
      c.getFirstInRange(selStart,selEnd);
      do{
         if( c.x==selStart.x || c.y==selStart.y || c.z==selStart.z ||
             c.x==selEnd.x || c.y==selEnd.y || c.z==selEnd.z ){

            Coord[] adjc = adjOffset[c.tile];
            int[] tilearr = tiles[c.tile];
            for( int side=0; side<tilearr.length; side++ ){
               temp.set(c);
               temp.add(adjc[side]);
               if( !temp.isInRange( selStart, selEnd ) ){
                  drawTileSide(g, c.tile, side, getScreenXcoord(c), getScreenYcoord(c) );
               }
            }
         }
      }while( c.getNextInRange(selStart,selEnd, numTileTypes) );
   }

   abstract public boolean screen2Grid(Coord c, int x, int y);

   // get adjacent tiles of given coordinate
   final public Coord getAdjacent(Coord c,int i){
      Coord[] adjc = adjOffset[c.tile];
      if(i<0 || i>=adjc.length) return null;
      Coord d=new Coord(c);
      d.add(adjc[i]);
      return d;
   }

   public String toString(){
      return name;
   }

   //--------
   
   
   abstract protected int getScreenXcoord(Coord c);
   abstract protected int getScreenYcoord(Coord c);
   abstract protected int getScreenXoffset(Coord c);
   abstract protected int getScreenYoffset(Coord c);
   abstract protected double calcWidthInBlocks(Coord first, Coord last);
   abstract protected double calcHeightInBlocks(Coord first, Coord last);
   abstract protected void recalcVertices();
   abstract protected void rotate(Coord c);
   abstract protected void reflect(Coord c);

   // calculate centres for each tile in a block
   private void calcCentres(){
      for( int t=0; t<tiles.length; t++){
         tileCentreX[t] = tileCentreY[t] = 0;
         int[] tilearr = tiles[t];
         for( int i=0; i<tilearr.length; i++ ){
            int vindex = tilearr[i];
            tileCentreX[t] += vertexx[vindex];
            tileCentreY[t] += vertexy[vindex];
         }
         tileCentreX[t] /= tilearr.length;
         tileCentreY[t] /= tilearr.length;
      }
   }

   // rotate and/or reflect c around fixed centre, rotation r.
   protected void getRotate(Coord c,int rot0){
      int rot = rot0;
      if( rot>=numRotate ){
         reflect(c);
         rot-=numRotate;
      }
      while( rot>0){
         rotate(c);
         rot--;
      }
   }

   private void drawTileSide( Graphics g, int tile, int side, int ox, int oy ){
      int[] tilearr = tiles[tile];
      int vindex = tilearr[side];
      int vindex2 = tilearr[(side+1)%tilearr.length];
      // always draw in one direction only.
      if( vindex>vindex2 ){ int t=vindex; vindex=vindex2; vindex2=t; }
      int x1 = vertexx[vindex] + ox;
      int y1 = vertexy[vindex] + oy;
      int x2 = vertexx[vindex2] + ox;
      int y2 = vertexy[vindex2] + oy;
      g.drawLine(x1, y1, x2, y2);
   }
   
   /**
    * @param g  
    * @param b 
    * @param c 
    * @param ox 
    * @param oy 
    */
   protected void drawTileInsides(Graphics g, Board b, Coord c, int ox, int oy ){

   }

   private void paintBoard(Graphics g, Board board, Color[] colList, boolean showVoid,
         Color edgeDarkColor, Color edgeLightColor, Color fillColor, Color bgColor ){

      // draw all tile innards
      int[] rx=new int[tiles[0].length];
      int[] ry=new int[tiles[0].length];
      Coord c = new Coord();
      c.getFirstInRange(firstShown,lastShown);
      do{
         int c0 = board.getContents(c);
         if( c0>=0 ) {
            int[] tilearr = tiles[c.tile];
            if( rx.length < tilearr.length ){
               rx=new int[tilearr.length];
               ry=new int[tilearr.length];
            }
            for( int i=0; i<tilearr.length; i++ ){
               int vindex = tilearr[i];
               rx[i] = vertexx[vindex] + getScreenXcoord(c);
               ry[i] = vertexy[vindex] + getScreenYcoord(c);
            }
            g.setColor(getBlockColour(c0,colList,showVoid, fillColor, bgColor));
            g.fillPolygon(rx, ry, tilearr.length);
         }
      }while( c.getNextInRange(firstShown,lastShown, numTileTypes) );

      // draw all tile edges
      c.getFirstInRange(firstShown,lastShown);
      Coord temp = new Coord();
      do{
         int c0 = board.getContents(c);
         Coord[] adjc = adjOffset[c.tile];
         int[] tilearr = tiles[c.tile];
         int xc = getScreenXcoord(c);
         int yc = getScreenYcoord(c);

         for( int side=0; side<tilearr.length; side++ ){
            temp.set(c);
            temp.add(adjc[side]);
            int c1 = board.getContents(temp);
            if( c1!=c0 || c1==0 || ( showVoid && c1==-1) ) {
               g.setColor( c1!=c0 ? edgeDarkColor : edgeLightColor);
               drawTileSide(g, c.tile, side, xc, yc );
            }
         }
         drawTileInsides(g, board, c, xc, yc );
      }while( c.getNextInRange(firstShown,lastShown, numTileTypes) );
   }

   private void recalcSettings(int w, int h, Board board, boolean showVoid)
   {
      if(w<=0 || h<=0 ){
         blockSize=0;
      }else{
         // get size of actual board
         board.getBlockRange(firstShown, lastShown);
         lastShown.tile = numTileTypes-1;
         // expand by border
         if(showVoid){
            firstShown.sub(is3D()?border3D:border2D);
            lastShown.add(is3D()?border3D:border2D);
         }

         width=w;
         height=h;

         // calculate blockSize
         double f1 = calcWidthInBlocks(firstShown,lastShown);
         double f2 = calcHeightInBlocks(firstShown,lastShown);
         int bs1 = (int)( (w-screenBorder*2)/f1 );
         int bs2 = (int)( (h-screenBorder*2)/f2 );
         blockSize = Math.min(bs1, bs2);
         if(blockSize<3 ) blockSize=3;

         int widthX = (int)(0.5+blockSize*f1);
         int widthY = (int)(0.5+blockSize*f2);
         offsetX = (w-widthX)/2;
         offsetY = (h-widthY)/2;

         recalcVertices();
         calcCentres();
      }
   }

   private int numRotate, numReflect, numTileTypes;

   private static final int screenBorder=5; // screen border in pixels
   private Color getBlockColour(int num, Color[] colList, boolean showVoid, Color fillColor, Color bgColor){
      if( num==0 && !showVoid ) return bgColor;
      else if(colList==null) return fillColor;
      if( num<0 || num>colList.length ) {
         return fillColor;
      }
      return colList[num-1];
   }
}
