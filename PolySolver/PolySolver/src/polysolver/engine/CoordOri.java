package polysolver.engine;

import java.io.IOException;

import fileMenu.Parser;


public class CoordOri
{
   public Coord coord;
   public int ori;
   public CoordOri(){
      coord = new Coord();
      ori=0;
   }
   public CoordOri(CoordOri c){
      coord = new Coord(c.coord);
      ori=c.ori;
   }
   public CoordOri(Coord c, int v){
      coord = new Coord(c);
      ori=v;
   }
   public void set(){ coord.set(); ori=0;}
   public void set(Coord c, int v){
      coord.set(c);
      ori=v;
   }
   public void set(CoordOri c){
      coord.set(c.coord);
      ori = c.ori;
   }
   public String toString(){
      return toString( true, true );
   }
   public String toString(boolean is3D, boolean hasTile){
      String s = "(" + coord.x + "," + coord.y;
      if( is3D || coord.z!=0 ) s += "," + coord.z;
      if( hasTile || coord.tile!=0 ) s += ";" + coord.tile;
      s += "|" + ori + ")";
      return s;
   }
   public static CoordOri parse(Parser parser) throws IOException{
      CoordOri c=new CoordOri();
      // parse Coord
      String s=parser.readString();
      if( !s.equals("(") )
         throw new IOException("Expected '(' at start of coordinate.");
      c.coord.x=parser.readNumber();
      c.coord.y=parser.readNumber();
      s=parser.readString();
      if( s.equals(":") || s.equals("|") || s.equals(")") ){
         c.coord.z=0;
      }else{
         parser.pushback();
         c.coord.z=parser.readNumber();
         s=parser.readString();
      }
      if( s.equals("|") || s.equals(")") ){
         c.coord.tile=0;
      }else{
         parser.pushback();
         c.coord.tile=parser.readNumber();
         s=parser.readString();
      }
      if( s.equals(")") ){
         c.ori=0;
      }else{
         if( !s.equals("|") ) parser.pushback();
         c.ori=parser.readNumber();
         s=parser.readString();
      }
      if( !s.equals(")") )
         throw new IOException("Expected ')' at end of coordinate.");
      return c;
   }
   public boolean equals(Object p){
      if( p instanceof CoordOri ){
         CoordOri c = (CoordOri) p;
         return coord.equals(c.coord);
      }
      return false;
   }

   public int hashCode() { return coord.hashCode(); }
}