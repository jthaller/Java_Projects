package polysolver.gridtypes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import polysolver.engine.Board;
import polysolver.engine.Coord;


public interface IGridType
{

   void reset();

   public boolean is3D();
   int getNumRotate();
   int getNumReflect();
   int getNumTileTypes();
   
   // rotate/reflect a set of coordinates
   void getRotate(Coord[] t, int j);

   // draw tiling illustration
   void paintIcon(Graphics g, int x,int y, int w, int h);

   // Paint whole board with given colours. If showVoid true then include a void border.
   void paintComponent(Component gridEditPanel, Graphics g, Board board, Color[] colList, boolean showVoid, Color edgeDarkColor, Color edgeLightColor, Color fillColor, Color bgColor );
   // paint outline of given tile with x,y = centre of part with index floathandle 
   void paintCentredOutline(Graphics g, Coord[] floatShape, int x, int y, int floatHandle);
   // Outline a region of the board
   void paintOutline(Graphics g, Coord selectEndCoord, Coord selectEndCoord2);
   // convert screen coord x,y to board coordinate coord.
   boolean screen2Grid(Coord coord, int x, int y);

   // get coordinate adjacent to coord
   Coord getAdjacent(Coord coord, int j);

   // get the list of orbits the tiles belong to
   int[] getTileOrbits();
   int getNumTileOrbits();
}