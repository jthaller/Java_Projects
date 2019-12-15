package buttons;
public class MysteriousIf extends GUIManager {
private final int WINDOW_WIDTH = 400;
private final int WINDOW_HEIGHT = 75;
private JButton sunny;
private JButton saturday;
private JButton healthy;
public MysteriousIf() {
this.createWindow( WINDOW_WIDTH, WINDOW_HEIGHT );
sunny = new JButton("Sunny");
saturday = new JButton("Saturday");
healthy = new JButton("Healthy");
contentPane.add(sunny);
contentPane.add(saturday);
contentPane.add(healthy);
sunny.setEnabled(true);
saturday.setEnabled(true);
healthy.setEnabled(true);
}
public void buttonClicked( JButton which ) {
healthy.setEnabled(false);
if (which == sunny){
if (saturday.isEnabled()){
healthy.setEnabled(true);
} else {
saturday.setEnabled(true);
}
sunny.setEnabled(false);
} else if ( !(which == saturday) ){
saturday.setEnabled(true);
} else if (which == healthy){
if (saturday.isEnabled()){
sunny.setEnabled(false);
}
} else {
sunny.setEnabled(true);
saturday.setEnabled(false);
healthy.setEnabled(true);
}
}
