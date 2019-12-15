package polysolver.gridtypes;

import java.awt.*;

import polysolver.engine.Coord;


class GridTypeTriangle
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,2}, {0,2,3}
   };
   private static final Coord[][] adjOffsetInit = {
      { new Coord(-1,0,0,1), new Coord(0,1,0,1), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,0,0,-1), new Coord(0,-1,0,-1) },
   };
   private static int[] tileOrbitInit = {0,0};
   private int blockHeight=0;
   private static double factor=Math.sqrt(3);

   public GridTypeTriangle(){
      super("Triangle",6,true, false, 4, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=30;
   final static int icontileheight=26;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int y=cy-icontileheight/2; y<cy+h; y+=icontileheight*2){
         g.drawLine(cx,y,cx+w,y);
         g.drawLine(cx,y+icontileheight,cx+w,y+icontileheight);
         for(int x=cx; x<cx+w; x+=icontilesize){
            g.drawLine(x,y,x+icontilesize,y+icontileheight*2);
            g.drawLine(x+icontilesize,y,x,y+icontileheight*2);
         }
      }
   }

   protected void recalcVertices(){
      blockHeight = (int)(0.5 + blockSize*factor);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = -blockSize;
      vertexy[1] = blockHeight;
      vertexx[2] = blockSize;
      vertexy[2] = blockHeight;
      vertexx[3] = blockSize*2;
      vertexy[3] = 0;
   }

   public boolean screen2Grid(Coord c,int xx,int yy)
   {
      if( blockSize==0 ) return false;
      int x = xx-offsetX;
      int y = yy-offsetY;
      int row=0, col=0;
      // shift into repetitive area around first tile
      // first shift diag / direction to same screen y-coord as first tile
      int d=y/blockHeight;
      if( y<0 ) d--;
      y-=d*blockHeight;
      x+=(d + firstShown.y - lastShown.y - 1)*blockSize;
      row+=d;
      // now shift until in same screen x-coord as first tile
      d=x/(blockSize*2);
      if( x<0 ) d--;
      x-=d*(blockSize*2);
      col+=d;

      // check whether outside edges of tile 1
      int tile=1;
      if( x*blockHeight-y*blockSize < 0 ){
         tile--;
      }else if( (x-blockSize*2)*blockHeight+y*blockSize > 0 ){
         tile--;col++;
      }

      // check if triangle row,col,t is visible
      if( row<0 || col<0 ) return false;
      col+=firstShown.x;
      row+=firstShown.y;
      if( col>lastShown.x || row>lastShown.y ) return false;
      c.set(col,row,tile);
      return true;
   }

   protected void rotate(Coord c){
      c.tile=1-c.tile;
      int i=c.x;
      int j=c.y;
      c.x=i-j-c.tile;
      c.y=i;
   }
   protected void reflect(Coord c){
      int i=c.x;
      c.x=c.y;
      c.y=i;
      c.tile=1-c.tile;
   }

   protected int getScreenXcoord(Coord c){
      return offsetX+2*blockSize*(c.x-firstShown.x)-blockSize*(c.y-firstShown.y) + blockSize*(lastShown.y-firstShown.y+1);
   }
   protected int getScreenYcoord(Coord c){
      return offsetY+blockHeight*(c.y-firstShown.y);
   }
   protected int getScreenXoffset(Coord c){
      return 2*blockSize*c.x - blockSize*c.y;
   }
   protected int getScreenYoffset(Coord c){
      return blockHeight*c.y;
   }

   protected double calcWidthInBlocks(Coord first, Coord last){
      return 2*(last.x - first.x + 1) + (last.y - first.y + 1);
   }
   protected double calcHeightInBlocks(Coord first, Coord last){
      return (last.y - first.y + 1)*factor;
   }
}
