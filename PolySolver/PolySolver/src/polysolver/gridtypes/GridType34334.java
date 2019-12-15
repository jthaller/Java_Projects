package polysolver.gridtypes;

import java.awt.*;

import polysolver.engine.Coord;


class GridType34334
   extends GridType
{
   private static final int[][] tilesInit = {
      {0,1,7}, {1,8,7}, {8,3,4}, {8,4,5},
      {1,2,9},{2,3,9},{8,1,9},{3,8,9},
      {6,7,10},{5,6,10},{7,8,10},{8,5,10},
   };
   private static final Coord[][] adjOffsetInit = {
      { new Coord(-1,0,0,9), new Coord(0,0,0,1), new Coord(0,-1,0,5) },
      { new Coord(0,0,0,5), new Coord(0,0,0,9), new Coord(0,0,0,-1) },
      { new Coord(0,0,0,5), new Coord(0,1,0,6), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-1), new Coord(1,0,0,1), new Coord(0,0,0,8) },

      { new Coord(-1,0,0,-1), new Coord(0,0,0,1), new Coord(0,0,0,2) },
      { new Coord(0,1,0,-5), new Coord(0,0,0,2), new Coord(0,0,0,-1) },
      { new Coord(0,0,0,-5), new Coord(0,0,0,-2), new Coord(0,0,0,1) },
      { new Coord(0,0,0,-5), new Coord(0,0,0,-1), new Coord(0,0,0,-2) },

      { new Coord(0,-1,0,-6), new Coord(0,0,0,2), new Coord(0,0,0,1) },
      { new Coord(1,0,0,-9), new Coord(0,0,0,-1), new Coord(0,0,0,2) },
      { new Coord(0,0,0,-9), new Coord(0,0,0,1), new Coord(0,0,0,-2) },
      { new Coord(0,0,0,-8), new Coord(0,0,0,-2), new Coord(0,0,0,-1) },
   };
   private static int[] tileOrbitInit = {0,0,0,0,1,1,1,1,1,1,1,1};


   public GridType34334(){
      super("34334", 4,true, false, 11, tilesInit, adjOffsetInit, tileOrbitInit );
   }

   final static double sin15 = Math.sin(Math.toRadians(15));

   final static int icontilesize=30;
   public void paintIcon(Graphics g, int cx,int cy, int w0, int h0){
      int h = (int)(icontilesize*sin15);
      for(int y=cy-icontilesize; y<cy+h0; y+=icontilesize*2){
         for(int x=cx-icontilesize; x<cx+w0; x+=icontilesize*2){
            g.drawLine(x,y,x+icontilesize,y+h);
            g.drawLine(x,y,x+h,y+icontilesize);
            g.drawLine(x+icontilesize,y+h,x+h, y+icontilesize);
            g.drawLine(x+icontilesize,y+h,x+icontilesize+h, y+icontilesize+h);
            g.drawLine(x+h,y+icontilesize,x+icontilesize+h, y+icontilesize+h);
            g.drawLine(x+icontilesize+h, y+icontilesize+h, x+icontilesize*2, y+icontilesize*2);
            g.drawLine(x+icontilesize,y+h, x+icontilesize*2,y);
            g.drawLine(x+h,y+icontilesize, x,y+icontilesize*2);
            g.drawLine(x+icontilesize+h, y+icontilesize+h, x+icontilesize, y+icontilesize*2+h);
            g.drawLine(x+icontilesize+h, y+icontilesize+h, x+icontilesize*2+h, y+icontilesize);

            g.drawLine(x+icontilesize+h, y+icontilesize+h, x, y+icontilesize*2);
            g.drawLine(x+icontilesize+h, y+icontilesize+h, x+icontilesize*2, y);
            g.drawLine(x+h,y+icontilesize, x+icontilesize,y+icontilesize*2+h);
            g.drawLine(x+icontilesize,y+h, x+icontilesize*2+h,y+icontilesize);
         }
      }
   }

   private int blockTab;
   protected void recalcVertices(){
      blockTab = (int)(blockSize*sin15+0.5);
      vertexx[0] = 0;
      vertexy[0] = 0;
      vertexx[1] = blockTab;
      vertexy[1] = blockSize;
      vertexx[2] = 0;
      vertexy[2] = 2*blockSize;
      vertexx[3] = blockSize;
      vertexy[3] = 2*blockSize+blockTab;
      vertexx[4] = 2*blockSize;
      vertexy[4] = 2*blockSize;
      vertexx[5] = 2*blockSize+blockTab;
      vertexy[5] = blockSize;
      vertexx[6] = 2*blockSize;
      vertexy[6] = 0;
      vertexx[7] = blockSize;
      vertexy[7] = blockTab;
      vertexx[8] = blockSize+blockTab;
      vertexy[8] = blockSize+blockTab;
      vertexx[9] = (blockSize+blockTab)/2;
      vertexy[9] = (3*blockSize+blockTab)/2;
      vertexx[10] = (3*blockSize+blockTab)/2;
      vertexy[10] = (blockSize+blockTab)/2;
   }

   public boolean screen2Grid(Coord c,int xx,int yy)
   {
      if( blockSize==0 ) return false;
      int x = xx-offsetX;
      int y = yy-offsetY;

      int x0=x/(blockSize*2);
      if(x<0) x0--;
      x-=x0*blockSize*2;
      int y0=y/(blockSize*2);
      if(y<0) y0--;
      y-=y0*blockSize*2;
      x0+=firstShown.x;
      y0+=firstShown.y;

      // shift tabs to build whole repeating region
      if( x*blockSize - y*blockTab < 0 && x*blockSize + y*blockTab < blockSize*blockSize*2  ){
         x0--;
         x+=blockSize*2;
      }else if( y*blockSize - x*blockTab < 0 && y*blockSize + x*blockTab < blockSize*blockSize*2  ){
         y0--;
         y+=blockSize*2;
      }

      if( x0<firstShown.x || y0<firstShown.y ) return false;
      if( x0>lastShown.x || y0>lastShown.y ) return false;

      int tile=0;
      if( x+y < blockSize+blockTab ) tile=0;
      else if( y*blockSize - x*blockTab < blockSize*blockSize-blockTab*blockTab
            && x*blockSize - y*blockTab < blockSize*blockSize-blockTab*blockTab  ){
         tile = 1;
      }else if( y>x ){
         if( x*blockSize + y*blockTab > (blockSize+blockTab)*(blockSize+blockTab) ){
            tile = 2;
         }else{
            tile=4;
            if( x*(blockSize-blockTab)+y*(blockSize+blockTab) > blockSize*2*(blockSize+blockTab) )
               tile+=1;
            if( x*(blockSize+blockTab)-y*(blockSize-blockTab) > blockTab*(blockTab+blockSize*2) -blockSize*blockSize )
               tile+=2;
         }
      }else{
         if( y*blockSize + x*blockTab > (blockSize+blockTab)*(blockSize+blockTab) ){
            tile = 3;
         }else{
            tile=8;
            if( y*(blockSize-blockTab)+x*(blockSize+blockTab) > blockSize*2*(blockSize+blockTab) )
               tile+=1;
            if( y*(blockSize+blockTab)-x*(blockSize-blockTab) > blockTab*(blockTab+blockSize*2) -blockSize*blockSize )
               tile+=2;
         }
      }

      c.set(x0,y0,tile);
      return true;
   }

   private static Coord[] rotateOffset = new Coord[]{
      new Coord(0,0,0,2),
      new Coord(0,0,0,2),
      new Coord(1,0,0,-1),
      new Coord(1,0,0,-3),
      new Coord(1,0,0,1),
      new Coord(1,0,0,2),
      new Coord(1,0,0,-2),
      new Coord(1,0,0,-1),
      new Coord(0,0,0,2),
      new Coord(0,0,0,-1),
      new Coord(0,0,0,1),
      new Coord(0,0,0,-2),
   };
   protected void rotate(Coord c){
      int i = c.x; c.x = c.y;  c.y = -i;
      c.add(rotateOffset[c.tile]); 
   }
   private static Coord[] reflectOffset = new Coord[]{
      new Coord(0,0,0,0),
      new Coord(0,0,0,0),
      new Coord(0,0,0,1),
      new Coord(0,0,0,-1),
      new Coord(0,0,0,4),
      new Coord(0,0,0,4),
      new Coord(0,0,0,4),
      new Coord(0,0,0,4),
      new Coord(0,0,0,-4),
      new Coord(0,0,0,-4),
      new Coord(0,0,0,-4),
      new Coord(0,0,0,-4),
   };
   protected void reflect(Coord c){
      int i = c.x; c.x = c.y;  c.y = i;
      c.add(reflectOffset[c.tile]); 
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
      return sin15 + (last.x - first.x + 1)*2;
   }
   protected double calcHeightInBlocks(Coord first, Coord last){
      return sin15 + (last.y - first.y + 1)*2;
   }
}
