package polysolver.gridtypes;

import java.awt.*;

import polysolver.engine.Coord;


class GridTypeCubeTile
   extends GridType
{
   private static final int[][] tilesInit = { {6,0,1,2}, {6,2,3,4}, {6,4,5,0} };
   private static final Coord[][] adjOffsetInit = {
      { new Coord(0,0,0,2),  new Coord(-1,0,0,1), new Coord(0,1,0,2), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,1,0,1), new Coord(1,0,0,-1), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(0,-1,0,-2), new Coord(-1,-1,0,-1), new Coord(0,0,0,-2) },
   };
   private static int[] tileOrbitInit = {0,0,0};
   private int blockHeight=0;
   private static double factor=Math.sqrt(3);

   public GridTypeCubeTile(){
      super("Cubetiling",6,true, false, 7, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=15;
   final static int icontileheight=26;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int y=cy-icontileheight/2; y<cy+h; y+=icontileheight*2){
         for(int x=cx; x<cx+w; x+=icontilesize*6){
            g.drawLine(x,y,x+icontilesize,y+icontileheight);
            g.drawLine(x,y,x+icontilesize,y-icontileheight);
            g.drawLine(x,y,x+icontilesize*2,y);
            g.drawLine(x+icontilesize,y-icontileheight,x+icontilesize*3,y-icontileheight);
            g.drawLine(x+icontilesize*2,y,x+icontilesize*3,y-icontileheight);
            g.drawLine(x+icontilesize*2,y,x+icontilesize*3,y+icontileheight);

            g.drawLine(x+icontilesize*4,y,x+icontilesize*3,y+icontileheight);
            g.drawLine(x+icontilesize*4,y,x+icontilesize*3,y-icontileheight);
            g.drawLine(x+icontilesize*4,y,x+icontilesize*6,y);
            g.drawLine(x+icontilesize*3,y-icontileheight,x+icontilesize*5,y-icontileheight);
            g.drawLine(x+icontilesize*6,y,x+icontilesize*5,y-icontileheight);
            g.drawLine(x+icontilesize*6,y,x+icontilesize*5,y+icontileheight);
         }
      }
   }

   protected void recalcVertices(){
      blockHeight = (int)(0.5 + blockSize*factor);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = -blockSize;
      vertexy[1] = blockHeight;
      vertexx[2] = 0;
      vertexy[2] = blockHeight*2;
      vertexx[3] = blockSize*2;
      vertexy[3] = blockHeight*2;
      vertexx[4] = blockSize*3;
      vertexy[4] = blockHeight;
      vertexx[5] = blockSize*2;
      vertexy[5] = 0;
      vertexx[6] = blockSize;
      vertexy[6] = blockHeight;
   }

   public boolean screen2Grid(Coord c,int xx,int yy)
   {
      if( blockSize==0 ) return false;
      int x = xx-getScreenXcoord(firstShown);
      int y = yy-getScreenYcoord(firstShown);

      int row=0, col=0;
      // shift into repetitive area around first tile
      // first shift until in same screen x-coord as first tile
      int d=x/(blockSize*3);
      if( x<0 ) d--;
      x-=d*(blockSize*3);
      y-=d*blockHeight;
      col+=d;
      // first shift until in same screen y-coord as first tile
      d=y/(blockHeight*2);
      if( y<0 ) d--;
      y-=d*(blockHeight*2);
      row+=d;col+=d;

      int tile;
      if( y<blockHeight ){
         tile=2;
         // check whether just outside left/right edges of top tile
         if( x*blockHeight-y*blockSize < 0 ) tile=0;
         else if( (x-blockSize*2)*blockHeight-y*blockSize > 0 ){
            tile=0; row--;
         }
      }else{
         tile=1;
         // check whether just outside left/right edges of bottom tile
         if( (x-blockSize*2)*blockHeight+y*blockSize < 0 ){
            tile=0;
         }else if( (x-blockSize*4)*blockHeight+y*blockSize > 0 ){
            tile=0;col++;
         }
      }

      // check if hexagon row,col is visible
      if( row<0 || col<0 ) return false;
      col+=firstShown.x;
      row+=firstShown.y;
      if( col>lastShown.x || row>lastShown.y ) return false;
      c.set(col,row,tile);
      return true;
   }

   protected void rotate(Coord c){
      int i=c.x;
      int j=c.y;
      c.x=i-j;
      c.y=i;
      if( c.tile==2 ){ c.tile=0; }
      else if(c.tile==1){ c.tile=2; c.y++; }
      else{ c.tile=1; c.x--; }
   }
   protected void reflect(Coord c){
      if( c.tile!=0 ) c.tile=3-c.tile;
      int i=c.x;
      int j=c.y;
      c.x=-j;
      c.y=-i;
   }

   protected int getScreenXcoord(Coord c){
      return offsetX + blockSize + 3*blockSize*(c.x - firstShown.x -c.y + lastShown.y);
   }
   protected int getScreenYcoord(Coord c){
      return offsetY + blockHeight*(c.y - firstShown.y + c.x - firstShown.x);
   }
   protected int getScreenXoffset(Coord c){
      return (c.x-c.y)*3*blockSize;
   }
   protected int getScreenYoffset(Coord c){
      return (c.x+c.y)*blockHeight;
   }

   protected double calcWidthInBlocks(Coord first, Coord last){
      return 3*(last.x - first.x + last.y - first.y + 1) + 1;
   }
   protected double calcHeightInBlocks(Coord first, Coord last){
      return (last.y - first.y + last.x - first.x + 2) * factor;
   }
}
