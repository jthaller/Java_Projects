package polysolver.gridtypes;

import java.awt.*;

import polysolver.engine.Coord;


class GridTypeDrafter
   extends GridType
{
   private static final int[][] tilesInit = {
      {12,0,1}, {12,1,2},
      {12,2,3}, {12,3,4},
      {12,4,5}, {12,5,6},
      {12,6,7}, {12,7,8},
      {12,8,9}, {12,9,10},
      {12,10,11}, {12,11,0},
      };
   private static final Coord[][] adjOffsetInit = {
      { new Coord(0,0,0,11), new Coord(-1,0,0,7), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(-1,0,0,5), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(0,1,0,7), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(0,1,0,5), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,1,0,7), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,1,0,5), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,0,0,-5), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,0,0,-7), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(0,-1,0,-5), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(0,-1,0,-7), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(-1,-1,0,-5), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(-1,-1,0,-7), new Coord(0,0,0,-11) },
   };
   private static int[] tileOrbitInit = {0,0,0,0,0,0,0,0,0,0,0,0};
   private int blockHeight=0;
   private static double factor=Math.sqrt(3);

   public GridTypeDrafter(){
      super("Drafter",6,true, false, 13, tilesInit, adjOffsetInit, tileOrbitInit);
   }

   final static int icontilesize=15;
   final static int icontileheight=26;
   public void paintIcon(Graphics g, int cx, int cy, int w, int h){
      for(int y=cy-icontileheight/2; y<cy+h; y+=icontileheight*2){
         for(int x=cx; x<cx+w; x+=icontilesize*6){
            g.drawLine(x,y-icontileheight,x+icontilesize*6,y-icontileheight);
            g.drawLine(x,y,x+icontilesize*6,y);
            g.drawLine(x,y-icontileheight,x,y+icontileheight);
            g.drawLine(x+icontilesize*3,y-icontileheight,x+icontilesize*3,y+icontileheight);

            g.drawLine(x,y,x+icontilesize,y-icontileheight);
            g.drawLine(x+icontilesize,y-icontileheight,x+icontilesize*3,y+icontileheight);
            g.drawLine(x+icontilesize*3,y+icontileheight,x+icontilesize*5,y-icontileheight);
            g.drawLine(x+icontilesize*5,y-icontileheight,x+icontilesize*6,y);
            g.drawLine(x+icontilesize*6,y,x+icontilesize*5,y+icontileheight);
            g.drawLine(x+icontilesize*5,y+icontileheight,x+icontilesize*3,y-icontileheight);
            g.drawLine(x+icontilesize*3,y-icontileheight,x+icontilesize,y+icontileheight);
            g.drawLine(x+icontilesize,y+icontileheight,x,y);

            g.drawLine(x,y,x+icontilesize*3,y-icontileheight);
            g.drawLine(x+icontilesize*3,y-icontileheight,x+icontilesize*6,y);
            g.drawLine(x+icontilesize*6,y,x+icontilesize*3,y+icontileheight);
            g.drawLine(x+icontilesize*3,y+icontileheight,x,y);
         }
      }
   }

   protected void recalcVertices(){
      blockHeight = (int)(blockSize*factor);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = -blockSize;
      vertexy[1] = blockHeight;
      vertexx[2] = -blockSize*2;
      vertexy[2] = blockHeight*2;
      vertexx[3] = -blockSize;
      vertexy[3] = blockHeight*3;
      vertexx[4] = 0;
      vertexy[4] = blockHeight*4;
      vertexx[5] = blockSize*2;
      vertexy[5] = blockHeight*4;
      vertexx[6] = blockSize*4;
      vertexy[6] = blockHeight*4;
      vertexx[7] = blockSize*5;
      vertexy[7] = blockHeight*3;
      vertexx[8] = blockSize*6;
      vertexy[8] = blockHeight*2;
      vertexx[9] = blockSize*5;
      vertexy[9] = blockHeight;
      vertexx[10] = blockSize*4;
      vertexy[10] = 0;
      vertexx[11] = blockSize*2;
      vertexy[11] = 0;
      vertexx[12] = blockSize*2;
      vertexy[12] = blockHeight*2;
   }

   public boolean screen2Grid(Coord c,int xx,int yy)
   {
      if( blockSize==0 ) return false;
      int x = xx-getScreenXcoord(firstShown);
      int y = yy-getScreenYcoord(firstShown);

      int row=0, col=0;
      // shift into repetitive area around first hexagon
      // first shift until in same screen x-coord as first tile
      int d=x/(blockSize*6);
      x-=d*(blockSize*6);
      if( x<0 ) { x+=blockSize*6; d--; }
      y-=d*blockHeight*2;
      col+=d;
      // first shift until in same screen y-coord as first tile
      d=y/(blockHeight*4);
      y-=d*(blockHeight*4);
      if( y<0 ) { x+=blockHeight*4; d--; }
      row+=d;col+=d;

      // make 0,0 centre of hexagon.
      x-=blockSize*2;
      y-=blockHeight*2;
      // if just outside right edges of hexagon, remap into tile
      if( y<0 ){
         if( (x-blockSize*4)*blockHeight-y*blockSize > 0 ){
            row--;
            x-=blockSize*6;
            y+=blockHeight*2;
         }
      }else{
         if( (x-blockSize*4)*blockHeight+y*blockSize > 0 ){
            col++;
            x-=blockSize*6;
            y-=blockHeight*2;
         }
      }

      // check if hexagon row,col is visible
      if( row<0 || col<0 ) return false;
      col+=firstShown.x;
      row+=firstShown.y;
      if( col>lastShown.x || row>lastShown.y ) return false;

      // check which drafter it is in
      int tile=0;
      if(y>0){tile+=6;x=-x;y=-y;}
      if(x>0){tile+=3; int i=x; x=y;y=-i;}
      if(x*blockHeight-y*blockSize>0) tile+=2;
      else if(x*blockSize-y*blockHeight>0) tile++;
      tile = (13-tile)%12;
      c.set(col,row,tile);
      return true;
   }

   protected void rotate(Coord c){
      int i=c.x;
      int j=c.y;
      c.x=i-j;
      c.y=i;
      c.tile = (c.tile+10)%12;
   }
   protected void reflect(Coord c){
      c.y = c.x-c.y;
      c.tile=11-c.tile;
   }

   protected int getScreenXcoord(Coord c){
      return offsetX + 2*blockSize + 6*blockSize*(c.x - firstShown.x -c.y + lastShown.y);
   }
   protected int getScreenYcoord(Coord c){
      return offsetY + 2*blockHeight*(c.y - firstShown.y + c.x - firstShown.x);
   }
   protected int getScreenXoffset(Coord c){
      return (c.x-c.y)*6*blockSize;
   }
   protected int getScreenYoffset(Coord c){
      return (c.x+c.y)*2*blockHeight;
   }

   protected double calcWidthInBlocks(Coord first, Coord last){
      return 6*(last.x - first.x + last.y - first.y + 1) + 2;
   }
   protected double calcHeightInBlocks(Coord first, Coord last){
      return (last.y - first.y + last.x - first.x + 2) * 2 * factor;
   }
}
