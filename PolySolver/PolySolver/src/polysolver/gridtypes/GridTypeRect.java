package polysolver.gridtypes;

import java.awt.*;

import polysolver.engine.Coord;


class GridTypeRect
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,2,3} };
   private static final Coord[][] adjOffsetInit = {
      { new Coord(-1,0,0,0), new Coord(0,1,0,0), new Coord(1,0,0,0), new Coord(0,-1,0,0) }
   };
   private static int[] tileOrbitInit = {0};

   public GridTypeRect(){
      super("Rectangle",2,true, false, 4, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=15;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=icontilesize/2; x<w; x+=icontilesize*3){
         g.drawLine(cx+x,cy,cx+x,cy+h);
      }
      for(int y=icontilesize/2; y<h; y+=icontilesize*2){
         g.drawLine(cx,cy+y,cx+w,cy+y);
      }
   }

   protected void recalcVertices(){
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = 0;
      vertexy[1] = blockSize*2;
      vertexx[2] = blockSize*3;
      vertexy[2] = blockSize*2;
      vertexx[3] = blockSize*3;
      vertexy[3] = 0;
   }

   public boolean screen2Grid(Coord c,int xx,int yy)
   {
      if( blockSize==0 ) return false;
      int x = xx-offsetX;
      int y = yy-offsetY;
      if( x<0 || y<0 ) return false;
      x/=blockSize*3;
      y/=blockSize*2;
      x+=firstShown.x;
      y+=firstShown.y;
      if( x>lastShown.x || y>lastShown.y ) return false;
      c.set(x,y,0);
      return true;
   }

   protected void rotate(Coord c){
      c.x = -c.x; c.y = -c.y;
   }
   protected void reflect(Coord c){
      c.x = -c.x;
   }
   
   protected int getScreenXcoord(Coord c){
      return offsetX+3*blockSize*(c.x-firstShown.x);
   }
   protected int getScreenYcoord(Coord c){
      return offsetY+2*blockSize*(c.y-firstShown.y);
   }
   protected int getScreenXoffset(Coord c){
      return 3*blockSize*c.x;
   }
   protected int getScreenYoffset(Coord c){
      return 2*blockSize*c.y;
   }

   protected double calcWidthInBlocks(Coord first, Coord last){
      return 3*(last.x - first.x + 1);
   }
   protected double calcHeightInBlocks(Coord first, Coord last){
      return 2*(last.y - first.y + 1);
   }
}
