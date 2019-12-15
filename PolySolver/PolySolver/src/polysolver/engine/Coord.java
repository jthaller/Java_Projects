package polysolver.engine;

import java.io.IOException;
import java.util.Arrays;

import fileMenu.Parser;

public class Coord
   implements Comparable<Coord>
{
   public int x,y,z,tile;
   public Coord(){ x=y=z=tile=0; }
   public Coord(int x0,int y0,int z0,int tile0){
      x=x0;y=y0;z=z0;tile=tile0;
   }
   public Coord(int x0,int y0,int tile0){
      x=x0;y=y0;z=0;tile=tile0;
   }
   public Coord(Coord c){
      x=c.x;y=c.y;z=c.z;tile=c.tile;
   }
   public void add(Coord c){
      x+=c.x;
      y+=c.y;
      z+=c.z;
      tile+=c.tile;
   }
   public void sub(Coord c){
      x-=c.x;
      y-=c.y;
      z-=c.z;
      tile-=c.tile;
   }
   public boolean min(Coord c){
      boolean r=false;
      if(c.x<x) { x=c.x; r=true; }
      if(c.y<y) { y=c.y; r=true; }
      if(c.z<z) { z=c.z; r=true; }
      if(c.tile<tile) { tile=c.tile; r=true; }
      return r;
   }
   public boolean max(Coord c){
      boolean r=false;
      if(c.x>x) { x=c.x; r=true; }
      if(c.y>y) { y=c.y; r=true; }
      if(c.z>z) { z=c.z; r=true; }
      if(c.tile>tile) { tile=c.tile; r=true; }
      return r;
   }
   public void set(){ x=y=z=tile=0; }
   public void set(int x0,int y0,int tile0){
      x=x0;
      y=y0;
      z=0;
      tile=tile0;
   }
   public void set(int x0,int y0,int z0,int tile0){
      x=x0;
      y=y0;
      z=z0;
      tile=tile0;
   }
   public void set(Coord c){
      x=c.x;
      y=c.y;
      z=c.z;
      tile=c.tile;
   }
   public String toString(){
      return toString(true,true);
   }
   public String toString(boolean is3D, boolean hasTile){
      String s = "(" + x + "," + y;
      if( is3D || z!=0) s += "," + z;
      if( hasTile || tile!=0) s += ":" + tile;
      s += ")";
      return s;
   }

   public static Coord parse(Parser parser) throws IOException{
      Coord c=new Coord();
      // parse Coord
      String s=parser.readString();
      if( !s.equals("(") )
         throw new IOException("Expected '(' at start of coordinate.");
      c.x=parser.readNumber();
      c.y=parser.readNumber();
      s=parser.readString();
      if( s.equals(":") || s.equals(")") ){
         c.z=0;
      }else{
         parser.pushback();
         c.z=parser.readNumber();
         s=parser.readString();
      }
      if( s.equals(")") ){
         c.tile=0;
      }else{
         if( !s.equals(":") ) parser.pushback();
         c.tile=parser.readNumber();
         s=parser.readString();
      }
      if( !s.equals(")") )
         throw new IOException("Expected ')' at end of coordinate.");

      return c;
   }
   public boolean equals(Object p){
      if( p instanceof Coord ){
         Coord c = (Coord) p;
         return x==c.x && y==c.y && z==c.z && tile==c.tile;
      }
      return false;
   }

   public int hashCode() { return 10000*tile+100*y+x; }
   public int compareTo(Coord b) {
      if( tile<b.tile ) return -1;
      else if(tile>b.tile) return 1;
      if( x<b.x ) return -1;
      else if(x>b.x) return 1;
      if( y<b.y ) return -1;
      else if(y>b.y) return 1;
      if( z<b.z ) return -1;
      else if(z>b.z) return 1;
      return 0;
   }



   // shift all coordinates so that first coordinate is as small as possible,
   // Used for shapes, to ensure all placements tested
   static public void normalise(Coord[] b){
      if(b==null || b.length==0) return;
      // get minimum coordinate
      Coord m=new Coord(b[0]);
      for( int i=0; i<b.length; i++) m.min(b[i]);
      m.tile=0;   // tile type and does not change by translation
      // subtract minimum from everything
      for( int i=0; i<b.length; i++) b[i].sub(m);

      // put smallest first
      Arrays.sort(b);
   }

   public boolean getNextInRange(Coord start,Coord end){
      if( x<end.x || x<start.x ){ x++; return true; }
      x = start.x<end.x ? start.x : end.x;
      if( y<end.y || y<start.y ){ y++; return true; }
      y = start.y<end.y ? start.y : end.y;
      if( z<end.z || z<start.z ){ z++; return true; }
      z = start.z<end.z ? start.z : end.z;
      return false;
   }
   // update coordinate to be next in range start - end
   public boolean getNextInRange(Coord start, Coord end, int numTileTypes){
      if( start.equals(end)) return false;
      tile++;
      if(tile<numTileTypes) return true;
      tile=0;
      return getNextInRange(start,end);
   }

   // update coordinate to be first in range start - end
   public void getFirstInRange(Coord start, Coord end){
      x = start.x<end.x ? start.x : end.x;
      y = start.y<end.y ? start.y : end.y;
      z = start.z<end.z ? start.z : end.z;
      tile = start.equals(end) ? start.tile : 0;
   }
   // test whether coordinate lies in a range
   public boolean isInRange(Coord start, Coord end){
      if( x<start.x && x<end.x ) return false;
      if( y<start.y && y<end.y ) return false;
      if( z<start.z && z<end.z ) return false;
      if( x>start.x && x>end.x ) return false;
      if( y>start.y && y>end.y ) return false;
      if( z>start.z && z>end.z ) return false;
      if( start.equals(end) && tile!=start.tile ) return false;
      return true;
   }
}