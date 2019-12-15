package polysolver.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import polysolver.gridtypes.IGridType;

import fileMenu.Parser;

public class Polyomino {
   public final static int NONE = 0;
   public final static int ROTATE = 1;
   public final static int FLIP = 2;

   private int id;
   private int type = ROTATE;    //type of generated orientations
   // coordinate offsets of blocks in each user defined orientation
   private List<Coord[]> userOrients = new ArrayList<Coord[]>();
   // coordinate offsets of blocks in every orientation, including generated rotations/reflections
   private List<Coord[]> orients;
   // number of these tiles available
   private int maxAmount=0;
   // Minimum number of these tiles that must be used
   private int minAmount=0;
   private CoordOri[] placements;
   // number of these tiles in use so far
   private int used=0;

   private IChangeFlag changeFlag;

   public Polyomino(IChangeFlag cf){ changeFlag = cf; }

// all get/set stuff
   public void setChangeFlag(IChangeFlag cf){ changeFlag = cf; }
   public void reset(){used=0;}
   public int getId(){ return id; }
   public void setId(int i){
      if( id!=i ){
         id = i;
         if( changeFlag!=null ) changeFlag.setChange();
      }
   }
   public void setMaxAmount(int n){
      if(maxAmount != n){
         maxAmount = n;
         placements = new CoordOri[n];
         for(int i=0; i<n; i++) placements[i]=new CoordOri();
         if( changeFlag!=null ) changeFlag.setChange();
      }
   }
   public void setMinAmount(int n){
      if(minAmount != n){
         minAmount = n;
         if( changeFlag!=null ) changeFlag.setChange();
      }
   }


   public CoordOri[] getPlacements(){
      synchronized(placements){
         // make a copy
         CoordOri[] pl=new CoordOri[used];
         for( int i=0; i<used; i++){
            pl[i] = new CoordOri(placements[i]);
         }
         return pl;
      }
   }
   // Get the coordinate of the i'th piece of this type placed on the board
   public CoordOri getPlacement(int i){
      return new CoordOri(placements[i]);
   }
   public void setType(int t){
      if(type!=t){
         type = t;
         orients=null;
         if( changeFlag!=null ) changeFlag.setChange();
      }
   }
   public void addUserOrient(Coord[] t){
      // normalise so that all coords are non-negative
      if( t.length==0 ) return;
      Coord.normalise(t);
      userOrients.add(t);
      orients=null;
      if( changeFlag!=null ) changeFlag.setChange();
   }
   public void setUserOrient(int i,Coord[] t){
      if( t.length==0 ) return;
      Coord.normalise(t);
      if( Arrays.equals( getUserOrient(i), t)) return;
      userOrients.set(i,t);
      orients=null;
      if( changeFlag!=null ) changeFlag.setChange();
   }

   public void delUserOrient(int i){
      userOrients.remove(i);
      orients=null;
      if( changeFlag!=null ) changeFlag.setChange();
   }
   public int getMaxAmount(){ return maxAmount; }
   public int getMinAmount(){ return minAmount; }
   public int getAvailable(){ return maxAmount - used; }
   public int getType(){return type;}
   public int getNumUserOrient(){ return userOrients.size(); }
   public Coord[] getUserOrient(int ori){ return userOrients.get(ori); }
   public int getNumOrient(){ return orients.size(); }
   public Coord[] getOrient(int ori){ return orients.get(ori); }
   public int getNumBlocks(int ori){ return getOrient(ori).length; }

   public void getMinNumTiles(int[] minTotal, int[] tileOrbits) {
      int[] min = new int[minTotal.length];
      Arrays.fill(min, -1);
      int[] temp = new int[minTotal.length];
      for(Coord[] ca : userOrients){
         Arrays.fill(temp, 0);
         for( Coord c : ca )
            temp[tileOrbits[c.tile]]++;
         for( int j=0; j<min.length; j++){
            if( min[j]==-1 || min[j]>temp[j] ) min[j]=temp[j];
         }
      }
      for( int j=0; j<min.length; j++){
         minTotal[j] += min[j]*(maxAmount-used);
      }
   }
   public void getMaxNumTiles(int[] maxTotal, int[] tileOrbits) {
      int[] max = new int[maxTotal.length];
      Arrays.fill(max, -1);
      int[] temp = new int[maxTotal.length];
      for(Coord[] ca : userOrients){
         Arrays.fill(temp, 0);
         for( Coord c : ca )
            temp[tileOrbits[c.tile]]++;
         for( int j=0; j<max.length; j++){
            if( max[j]==-1 || max[j]<temp[j] ) max[j]=temp[j];
         }
      }
      for( int j=0; j<max.length; j++){
         maxTotal[j] += max[j]*(maxAmount-used);
      }
   }
   public Coord getCoord(int block, CoordOri c){
      Coord r = new Coord(getOrient(c.ori)[block]);
      r.add(c.coord);
      return r;
   }
   private int findOrient(Coord sd[]){
      for( int i=0; i<getNumOrient(); i++){
         if( Arrays.equals( sd, getOrient(i) )) return i;
      }
      return -1;
   }
   public boolean sameShape(Polyomino p2){
      if( p2==null ) return false;
      for(Coord[] ca : orients){
         if( p2.findOrient(ca)<0 ) return false;
      }
      return true;
   }

// initialisation stuff

   // generate all orientations from user input (incl rotations/reflections if asked for)
   public void initialiseOrients(IGridType g){
      orients = new ArrayList<Coord[]>();

      for( Coord[] uo : userOrients){
         if( type==NONE ){
            // just add this pattern
            Coord.normalise(uo);
            addOrient(uo);
         }else{
            Coord t[] =new Coord[uo.length];
            for(int k=0; k<t.length; k++) t[k]=new Coord();
            // try each orientation
            int n = (type==ROTATE) ? g.getNumRotate() : g.getNumReflect();
            for(int j=0; j<n ; j++){
               for(int k=0; k<t.length; k++) t[k].set(uo[k]);
               g.getRotate(t,j);
               addOrient(t);
            }
         }
      }
   }
   private void addOrient(Coord []t){
      for( Coord[] oc : orients){
         if( Arrays.equals(oc,t) ) return;
      }
      // make copy
      Coord[] s=new Coord[t.length];
      for( int i=0; i<t.length; i++) s[i]=new Coord(t[i]);
      orients.add(s);
   }

// all text io stuff

   public static Polyomino parse(Parser parser, IChangeFlag cf) throws IOException{
      Polyomino poly = new Polyomino(cf);

      // get orientation type
      int n = ROTATE;   // type
      String s=parser.readString();
      if( s.equalsIgnoreCase("flip") ) n=FLIP;
      else if( s.equalsIgnoreCase("fixed") ) n=NONE;
      //else if( s.equalsIgnoreCase("cyclic") ) n=ROTATE;
      else parser.pushback();
      poly.setType(n);

      // get muliplicity
      s=parser.readString();
      parser.pushback();
      if(!s.equals("(")){
         n=parser.readNumber();
         poly.setMaxAmount(n);
         s=parser.readString();
         parser.pushback();
         if(!s.equals("(")){
            n=parser.readNumber();
            poly.setMinAmount(n);
         }else
            poly.setMinAmount(0);
      }else
         poly.setMaxAmount(1);

      parser.skipOpenBracket();

      ArrayList<Coord> al = new ArrayList<Coord>();

      // get each orientation. first openbracket already skipped
      do{
         // get each cell of this orientation
         al.clear();
         do{
            // parse Coord
            Coord c = Coord.parse(parser);
            al.add(c);
            s=parser.readString();
            parser.pushback();
         }while( !s.equals(")") && !s.equals("+") );

         // store this orientation
         if(al.size()>0){
            Coord[] t = al.toArray(new Coord[]{});
            poly.addUserOrient(t);
         }
         s=parser.readString();
      }while(s.equals("+"));
      return poly;
   }

   public String textRepr(boolean is3D, boolean hasTile){
      StringBuilder s = new StringBuilder("tile ");
      if( type==FLIP ) s.append("flip");
      else if( type==NONE ) s.append("fixed");
      if( getMaxAmount()!=1 || getMinAmount()!=0 ){
         s.append(" ").append(getMaxAmount());
         if( getMinAmount()!=0 ){
            s.append(" ").append(getMinAmount());
         }
      }
      s.append("(");
      for(int i=0;i<getNumUserOrient(); i++){
         if(i!=0) s.append("+");
         Coord[] uo = getUserOrient(i);
         for(int j=0;j<uo.length; j++){
            s.append(uo[j].toString(is3D,hasTile));
         }
      }
      s.append(")\n");
      return s.toString();
   }

// all board placement stuff

   public boolean canPlace(Board brd,CoordOri c){
      Coord[] bl = getOrient(c.ori);
      Coord r = new Coord();
      for(Coord blc : bl){
         r.set(blc);
         r.add(c.coord);
         if( brd.getContents(r)!=0 ) return false;
      }
      return true;
   }

   // place polyomino on real board (during solve, or play by user)
   public void placeS(CoordOri c){
      synchronized(placements){
         placements[used].set(c);
         used++;
      }
   }
   // place polyomino on shadow board, for display during play
   public void place(Board brd,CoordOri c){
      place(brd,c,used);
   }
   // place polyomino on shadow board, for display of a solution, during solve
   public void place(Board brd, CoordOri c,int v){
      Coord[] bl = getOrient(c.ori);
      Coord r = new Coord();
      for(Coord blc : bl){
         r.set(blc);
         r.add(c.coord);
         brd.setContentsUnsafe(r,id+v);
      }
   }
   // remove polyomino from board (set flag if on real board)
   public void removeS(){
      synchronized(placements){
         used--;
      }
   }
   // remove polyomino from board (set flag if on real board)
   private void remove(Board brd, CoordOri c){
      Coord[] bl = getOrient(c.ori);
      Coord r = new Coord();
      for(Coord blc: bl){
         r.set(blc);
         r.add(c.coord);
         brd.setContentsUnsafe(r,0);
      }
   }

   //remove one of the earlier polyominoes from the board, during play
   public void remove(Board brd,int ix){
      int u = used;
      for( int i=u-1; i>=ix; i--){
         remove(brd, placements[i]);
         removeS();
      }
      for( int i=ix; i<u-1; i++){
         place(brd, placements[i+1]);
         placeS(placements[i+1]);
      }
   }
}