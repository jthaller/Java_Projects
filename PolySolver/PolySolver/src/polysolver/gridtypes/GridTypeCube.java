package polysolver.gridtypes;

import java.awt.*;

import polysolver.engine.Board;
import polysolver.engine.Coord;


class GridTypeCube
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3} };
   private static final Coord[][] adjOffsetInit = {
      { new Coord(-1,0,0,0), new Coord(0,1,0,0), new Coord(1,0,0,0), new Coord(0,-1,0,0), new Coord(0,0,1,0), new Coord(0,0,-1,0) }
   };
   private static int[] tileOrbitInit = {0};

   public GridTypeCube(){
      super("Cube", 24,true, true, 4, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=30;
   final static int iconsplit=10;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      int s = (w-4*iconsplit)/12;
      if( s>icontilesize ) s=icontilesize;
      int offX = (w-2*iconsplit-12*s)/2;
      int offY = (h-5*s)/2;

      for( int z0=0, z=cx+offX; z0<3; z0++, z+=s*4+iconsplit ){
         for(int x0=0, x=z; x0<5; x0++, x+=s){
            g.drawLine(x,cy+offY,x,cy+offY+s*5);
         }
         for(int y0=0, y=cy+offY; y0<6; y0++, y+=s){
            g.drawLine(z,y,z+4*s,y);
         }
      }
   }
   
   private int offsetZ;

   protected void recalcVertices(){
      offsetZ = (lastShown.x-firstShown.x+1)*blockSize + blockSize;
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = 0;
      vertexy[1] = blockSize;
      vertexx[2] = blockSize;
      vertexy[2] = blockSize;
      vertexx[3] = blockSize;
      vertexy[3] = 0;
   }

   public boolean screen2Grid(Coord c,int xx,int yy)
   {
      if( blockSize==0 ) return false;
      int x = xx-offsetX;
      int y = yy-offsetY;
      int z=0;
      if( x<0 || y<0 ) return false;
      while(x>offsetZ){ x-=offsetZ; z++; }
      x/=blockSize;
      y/=blockSize;
      x+=firstShown.x;
      y+=firstShown.y;
      z+=firstShown.z;
      if( x>lastShown.x || y>lastShown.y || z>lastShown.z ) return false;
      c.set(x,y,z,0);
      return true;
   }

   protected void rotate(Coord c){
      int t=c.x; c.x=c.y; c.y=-t;
   }
   protected void reflect(Coord c){
      c.x = -c.x;
   }
   
   protected void getRotate(Coord c,int rot0){
      int rot = rot0;
      int x=c.x, y=c.y, z=c.z;
      // reflect if 24-47
      if( rot>=24 ){
         x=-x; y=-y; z=-z;
         rot-=24;
      }
      // rotate correct face to top (the +z side)
      int f=rot>>2;// f=face
      if     ( f>=4){ int t=x; x=y; y=z; z=t; }
      else if( f>=2){ int t=x; x=z; z=y; y=t; }
      if((f&1)!=0){z=-z;y=-y;}

      // do correct face at front (the +y side)
      if((rot&1)!=0){   int t=x;x=y;y=-t; } //do quarter turn
      if((rot&2)!=0){   x=-x;y=-y; } //do half turn

      c.set(x,y,z,0);
   }

   protected int getScreenXcoord(Coord c){
      return offsetX+blockSize*(c.x-firstShown.x)+offsetZ*(c.z-firstShown.z);
   }
   protected int getScreenYcoord(Coord c){
      return offsetY+blockSize*(c.y-firstShown.y);
   }
   protected int getScreenXoffset(Coord c){
      return blockSize*c.x+offsetZ*c.z;
   }
   protected int getScreenYoffset(Coord c){
      return blockSize*c.y;
   }

   protected double calcWidthInBlocks(Coord first, Coord last){
      return (last.x - first.x + 2) * (last.z - first.z + 1)-1;
   }
   protected double calcHeightInBlocks(Coord first, Coord last){
      return last.y - first.y + 1;
   }
   /*
   if( c1>0 || ( c1==0 && showVoid ) ){
      // above
      g.setColor(Color.BLACK);
      int c2 = board.getContents(x0+1,y0+1,z0-1);
      if( c1==c2 ) {
         g.drawLine(x, y,x+blockSize,y+blockSize);
         g.drawLine(x+blockSize, y,x,y+blockSize);
      }
      // below
      c2 = board.getContents(x0+1,y0+1,z0+1);
      if( c1==c2 ) {
         g.drawOval(x+1, y+1, blockSize-2, blockSize-2);
      }
   }
*/ 
   private Coord temp = new Coord();
   protected void drawTileInsides(Graphics g, Board board, Coord c, int ox, int oy ){
      int c0 = board.getContents(c);
      if( c0<0 ) return;
      Coord[] adjc = adjOffset[c.tile];

      g.setColor( Color.BLACK );

      // draw above
      temp.set(c);
      temp.add(adjc[4]);
      int c1 = board.getContents(temp);
      if( c1==c0 ) {
         g.drawLine(ox, oy, ox+blockSize, oy+blockSize);
         g.drawLine(ox+blockSize, oy, ox, oy+blockSize);
      }

      // draw below
      temp.set(c);
      temp.add(adjc[5]);
      c1 = board.getContents(temp);
      if( c1==c0 ) {
         g.drawOval(ox+1, oy+1, blockSize-2, blockSize-2);
      }
   }
}
