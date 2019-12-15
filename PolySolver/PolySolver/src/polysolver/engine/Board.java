package polysolver.engine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
// Contains board.
import java.util.Iterator;

import fileMenu.Parser;


public class Board
   implements Iterable<Map.Entry<Coord,Integer>>
{
   //contents of board; 0=empty, -1=hole, >0=tilenumber
   private Map<Coord, Integer> blockList = new HashMap<Coord, Integer>();
   private IChangeFlag changeFlag;

   public Board(IChangeFlag cf){ changeFlag = cf; }

// all get/set stuff
   public Iterator<Map.Entry<Coord,Integer>> iterator(){ return blockList.entrySet().iterator(); }
   public int getNumBlocks(){return blockList.size();}
   public boolean isEmpty(){return blockList.isEmpty();}
   public int[] getNumBlocksArray(int numOrb, int[] tileOrb){
      int[] count = new int[numOrb];
      for( Coord c: blockList.keySet()){
         count[tileOrb[c.tile]]++;
      }
      return count;
   }
   public String getNumBlocksString(int numOrb, int[] tileOrb){
      int[] count = getNumBlocksArray(numOrb, tileOrb);
      String s = "";
      for( int i=0; i<numOrb; i++ ){
         if( i!=0 ) s+= "+";
         s += count[i];
      }
      return s;
   }

   // count empty spaces
   public int getNumEmptyBlocks(){
      int c=0;
      for( Map.Entry<Coord,Integer> k : blockList.entrySet() ){
         if( k.getValue().intValue() ==0 ) c++;
      }
      return c;
   }
   // count empty spaces of each type of tile
   public int[] getNumEmptyBlocks(int numTileOrbit, int tileOrbit[]){
      int[] count = new int[numTileOrbit];
      for( Map.Entry<Coord,Integer> k : blockList.entrySet() ){
         if( k.getValue().intValue() ==0 )
            count[tileOrbit[k.getKey().tile]]++;
      }
      return count;
   }
   public int getContents( Coord c){
      Object d = blockList.get(c);
      if( d==null ) return -1;
      return ((Integer)d).intValue();
   }
   public void setContentsUnsafe( Coord c,int v){
      Coord d=new Coord(c);
      blockList.put(d,new Integer(v));
   }

   public void toggleBlock( Coord c ){
      Coord d=new Coord(c);
      Integer i = blockList.get(d);
      if( i==null ){
         blockList.put(d,0);
      }else{
         blockList.remove(d);
      }
      if(changeFlag!=null) changeFlag.setChange();
   }

   public void addBlock( Coord c ){
      Coord d=new Coord(c);
      Integer i = blockList.get(d);
      if( i==null ){
         blockList.put(d,0);
      }
      if(changeFlag!=null) changeFlag.setChange();
   }

   // remove all pieces
   public void clearPieces(){
      for( Map.Entry<Coord,Integer> k : blockList.entrySet() ){
         k.setValue(Integer.valueOf(0));
      }
   }
   // destroy board, so it is of zero size.
   public void destroyBoard(){
      blockList.clear();
      if(changeFlag!=null) changeFlag.setChange();
   }

   public Coord[] getBlockArray(){
      Coord[] b = new Coord[blockList.size()];
      int j=0;
      for(Coord c : blockList.keySet() )
      {
         b[j++] =  new Coord(c);
      }
      return b;
   }
   public void setBoardContents(Solution sol){
      clearPieces();
      for( int i=0; i<sol.getNumPoly(); i++){
         Polyomino poly = sol.getPoly(i);
         CoordOri[] places = sol.getPlacement(i);
         for( int j=0; j<places.length; j++){
            if( places[j]!=null ){
               poly.place(this,places[j],j);
            }
         }
      }
   }
   //set a list of coordinates into the board
   public void setBoardBlocks(Coord[] b,int colour){
      blockList.clear();
      if(changeFlag!=null) changeFlag.setChange();

      // write out contents
      for(int j=0; j<b.length;j++)
         blockList.put(b[j], colour);
   }
   public void getTileRange(Coord s,Coord e){
      s.set();
      e.set();
      boolean first = true;
      for( Coord c : blockList.keySet() ){
         if( first ){
            s.set(c);e.set(c);
            first=false;
         }else{
            s.min(c);
            e.max(c);
         }
      }
   }
   public void getBlockRange(Coord s,Coord e){
      getTileRange(s,e);
      s.tile = 0;
      e.tile = 0;
   }

// all text io stuff
   public static Board parse(Parser parser, IChangeFlag cf) throws IOException{
      parser.skipOpenBracket();
      Coord mx = Coord.parse(parser);
      Coord mn = new Coord();

      // create empty board of that size
      Board brd=new Board(cf);
      Coord t = new Coord();
      t.getFirstInRange(mn, mn);
      do{
         brd.toggleBlock(t);
      }while( t.getNextInRange(mn,mx,mx.tile+1));

      // parse the list of voids
      String s=parser.readString();
      while(!s.equals(")")){
         parser.pushback();
         t = Coord.parse(parser);
         brd.toggleBlock(t);
         s=parser.readString();
      }
      return brd;
   }

   public String textRepr(boolean is3D, boolean hasTile){
      if( isEmpty() ) return "";
      StringBuilder s=new StringBuilder();
      Coord mn = new Coord(); mn.tile = 0;
      Coord mx = new Coord();
      getTileRange(mn,mx);
      Coord t = new Coord(mx);
      t.sub(mn);
      s.append("Board ( ").append(t.toString(is3D,hasTile));
      Coord c = new Coord();
      c.getFirstInRange(mn, mx);
      do{
         if( getContents(c)==-1 ){
            t.set(c); t.sub(mn);
            s.append(t.toString(is3D,hasTile));
         }
      }while( c.getNextInRange(mn, mx, mx.tile+1));
      s.append(")\n");
      return s.toString();
   }
}