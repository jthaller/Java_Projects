import javax.swing.JButton;
import java.awt.Color;

// A version of JButton specialized to serve as elements of a 15-puzzle
public class BoggleButton extends JButton {

    // Remember the location of this button in the puzzle grid
    private int row;
    private int column;
    private String color;

    // Create a new button.
    public BoggleButton( int theRow, int theColumn ) {
        row = theRow;
        column = theColumn;
    }

    // Return the row number of this button
    public int getRow() {
        return row;
    }

    // Return the column number of this button
    public int getColumn() {
        return column;
    }

    // Return true if other button is adjacent to this button horizontally or vertically
    public boolean isAdjacentTo( BoggleButton other ) {
        if( other != this &&
        Math.abs( row - other.getRow() ) + Math.abs( column - other.getColumn() ) == 1) {
            return true;
        } else if ( Math.abs( row - other.getRow() ) == 1 && Math.abs( column - other.getColumn() ) ==1 )
        {
            return true;
        }else {
            return false;
        }

    }

    public void setRed(){ 
        this.setForeground(Color.RED);
        this.setColor( "Red" );
    }

    public void setBlue(){
        this.setForeground(Color.BLUE);
        this.setColor( "Blue" );
    }
    
    public void setBlack(){
        this.setForeground(Color.BLACK);
        this.setColor( "Black" );
    }
    
    public void setColor( String newColor ) {
        color = newColor;
    }
    
    public String getColor() {
        return color;
    }
}