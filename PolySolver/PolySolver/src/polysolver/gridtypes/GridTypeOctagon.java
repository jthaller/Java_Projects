package polysolver.gridtypes;

import java.awt.*;

import polysolver.engine.Coord;


class GridTypeOctagon
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2,3,4,5,6,7},{3,8,9,4} };
   private static final Coord[][] adjOffsetInit = {
      { new Coord(-1,0,0,0), new Coord(-1,0,0,1),
        new Coord(0,1,0,0), new Coord(0,0,0,1),
        new Coord(1,0,0,0), new Coord(0,-1,0,1),
        new Coord(0,-1,0,0), new Coord(-1,-1,0,1),
      },{
         new Coord(0,1,0,-1),
         new Coord(1,1,0,-1),
         new Coord(1,0,0,-1),
         new Coord(0,0,0,-1),
      }
   };
   private static int[] tileOrbitInit = {0,1};

   public GridTypeOctagon(){
      super("Octagon",4,true, false, 10, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=10;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int x=cx-icontilesize/2; x<cx+w+icontilesize; x+=icontilesize*3){
         for(int y=cy-icontilesize/2; y<cy+h+icontilesize; y+=icontilesize*3){
            g.drawLine(x+icontilesize,y+icontilesize*3,x,y+icontilesize*2);
            g.drawLine(x,y+icontilesize*2,x,y+icontilesize);
            g.drawLine(x,y+icontilesize,x+icontilesize,y);
            g.drawLine(x+icontilesize,y,x+icontilesize*2,y);
            g.drawLine(x+icontilesize*2,y,x+icontilesize*3,y+icontilesize);
            g.drawLine(x+icontilesize*2,y+icontilesize*3,x+icontilesize*3,y+icontilesize*2);
         }
      }
   }

   protected void recalcVertices(){
      vertexx[0] = 0;
      vertexy[0] = blockSize;
      vertexx[1] = 0;
      vertexy[1] = blockSize*2;
      vertexx[2] = blockSize;
      vertexy[2] = blockSize*3;
      vertexx[3] = blockSize*2;
      vertexy[3] = blockSize*3;
      vertexx[4] = blockSize*3;
      vertexy[4] = blockSize*2;
      vertexx[5] = blockSize*3;
      vertexy[5] = blockSize;
      vertexx[6] = blockSize*2;
      vertexy[6] = 0;
      vertexx[7] = blockSize;
      vertexy[7] = 0;
      vertexx[8] = blockSize*3;
      vertexy[8] = blockSize*4;
      vertexx[9] = blockSize*4;
      vertexy[9] = blockSize*3;
   }

   public boolean screen2Grid(Coord c,int xx,int yy)
   {
      if( blockSize==0 ) return false;
      int x = xx-offsetX;
      int y = yy-offsetY;
      if( x<0 || y<0 ) return false;
      int x0=x/(blockSize*3); x-=x0*blockSize*3;
      int y0=y/(blockSize*3); y-=y0*blockSize*3;
      int tile=0;
      if( x+y<blockSize ) { tile=1; x0--; y0--;}
      else if( x+y>5*blockSize ) { tile=1; }
      else if( x-y>2*blockSize ) { tile=1; y0--; }
      else if( y-x>2*blockSize ) { tile=1; x0--; }

      if( x0<0 || y0<0 ) return false;
      x0+=firstShown.x;
      y0+=firstShown.y;
      if( x0>lastShown.x || y0>lastShown.y ) return false;
      c.set(x0,y0,tile);
      return true;
   }

   protected void rotate(Coord c){
      int t = c.x;
      c.x = c.y;
      c.y = -t;
      if( c.tile==1 ) c.y--;
   }
   protected void reflect(Coord c){
      c.x = -c.x;
      if( c.tile == 1 ) c.x--;  
   }
   
   protected int getScreenXcoord(Coord c){
      return offsetX+3*blockSize*(c.x-firstShown.x);
   }
   protected int getScreenYcoord(Coord c){
      return offsetY+3*blockSize*(c.y-firstShown.y);
   }
   protected int getScreenXoffset(Coord c){
      return 3*blockSize*c.x;
   }
   protected int getScreenYoffset(Coord c){
      return 3*blockSize*c.y;
   }

   protected double calcWidthInBlocks(Coord first, Coord last){
      return 3*(last.x - first.x + 1)+1;
   }
   protected double calcHeightInBlocks(Coord first, Coord last){
      return 3*(last.y - first.y + 1)+1;
   }
}
