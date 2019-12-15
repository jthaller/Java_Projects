package polysolver.gridtypes;

public class GridTypeFactory
{
   private static final IGridType[] gridTypes = {
      new GridTypeSquare(),
      new GridTypeRect(),
      new GridTypeDiamond(),
      new GridTypeTan(),
      new GridTypeOctagon(),
      new GridTypePentagon(),
      new GridTypePentagonHalf(),
      new GridTypeTriangle(),
      new GridTypeHexagon(),
      new GridTypeCubeTile(),
      new GridTypeDrafter(),
      new GridType34334(),
      new GridTypeCube(),
   };

   static public int numGridTypes(){
      return gridTypes.length;
   }
   static public IGridType factory(String s){
      for( int i=0; i<gridTypes.length; i++)
         if( gridTypes[i].toString().equalsIgnoreCase(s))
            return factory(i);
      return null;
   }
   static public IGridType factory(int i){
      IGridType g=null;
      try{ g=gridTypes[i].getClass().newInstance();} catch(Exception e){}
      return g;
   }
}
