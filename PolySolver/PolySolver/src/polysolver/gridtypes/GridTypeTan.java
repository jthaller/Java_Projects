package polysolver.gridtypes;

import java.awt.*;

import polysolver.engine.Coord;


class GridTypeTan
   extends GridType
{
   private static final int[][] tilesInit = { {0,1,4},{1,2,4},{2,3,4},{3,0,4} };
   private static final Coord[][] adjOffsetInit = {
      { new Coord(-1,0,0,2), new Coord(0,0,0,1),  new Coord(0,0,0,3) },
      { new Coord(0,1,0,2), new Coord(0,0,0,1),  new Coord(0,0,0,-1)  },
      { new Coord(1,0,0,-2), new Coord(0,0,0,1),  new Coord(0,0,0,-1)  },
      { new Coord(0,-1,0,-2), new Coord(0,0,0,-3),  new Coord(0,0,0,-1)  }
   };
   private static int[] tileOrbitInit = {0,0,0,0};

   public GridTypeTan(){
      super("Tan",4,true, false, 5, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=30;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize/2; x<cx+w+icontilesize; x+=icontilesize){
         for(int y=cy-icontilesize/2; y<cy+h+icontilesize; y+=icontilesize){
            g.drawLine(x+icontilesize,y+icontilesize,x,y);
            g.drawLine(x,y,x+icontilesize,y);
            g.drawLine(x+icontilesize,y,x,y+icontilesize);
            g.drawLine(x,y+icontilesize,x,y);
         }
      }
   }

   protected void recalcVertices(){
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = 0;
      vertexy[1] = blockSize*2;
      vertexx[2] = blockSize*2;
      vertexy[2] = blockSize*2;
      vertexx[3] = blockSize*2;
      vertexy[3] = 0;
      vertexx[4] = blockSize;
      vertexy[4] = blockSize;
   }

   public boolean screen2Grid(Coord c,int xx,int yy)
   {
      if( blockSize==0 ) return false;
      int x = xx-offsetX;
      int y = yy-offsetY;
      if( x<0 || y<0 ) return false;
      int x0=x/(blockSize*2); x-=x0*blockSize*2;
      int y0=y/(blockSize*2); y-=y0*blockSize*2;
      x0+=firstShown.x;
      y0+=firstShown.y;
      if( x0>lastShown.x || y0>lastShown.y ) return false;
      int tile=0;
      if(y<x) tile+=3;
      if( x+y>blockSize*2 ) tile^=1;
      c.set(x0,y0,tile);
      return true;
   }

   protected void rotate(Coord c){
      int t = c.x;
      c.x = c.y;
      c.y = -t;
      c.tile = (c.tile +1)%4; 
   }
   protected void reflect(Coord c){
      c.x = -c.x;
      if( c.tile == 0 || c.tile==2 ) c.tile ^= 2;  
   }
   
   protected int getScreenXcoord(Coord c){
      return offsetX+2*blockSize*(c.x-firstShown.x);
   }
   protected int getScreenYcoord(Coord c){
      return offsetY+2*blockSize*(c.y-firstShown.y);
   }
   protected int getScreenXoffset(Coord c){
      return 2*blockSize*c.x;
   }
   protected int getScreenYoffset(Coord c){
      return 2*blockSize*c.y;
   }

   protected double calcWidthInBlocks(Coord first, Coord last){
      return 2*(last.x - first.x + 1);
   }
   protected double calcHeightInBlocks(Coord first, Coord last){
      return 2*(last.y - first.y + 1);
   }
}
