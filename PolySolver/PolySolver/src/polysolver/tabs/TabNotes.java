package polysolver.tabs;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public final class TabNotes extends MyTab
   implements DocumentListener
{
   private JTextArea notesArea = new JTextArea();
   private JTextField description = new JTextField();

   public TabNotes(){
      super( "Notes", "Make notes" );
      setLayout(new BorderLayout());
      
      notesArea.setLineWrap(true);
      notesArea.setWrapStyleWord(true);

      add(description, BorderLayout.NORTH);
      description.getDocument().addDocumentListener(this);

      Box main = Box.createVerticalBox();
      main.add(Box.createVerticalStrut(10));
      Box inner = Box.createHorizontalBox();
      inner.add(new JLabel("Notes:"));
      inner.add(new JScrollPane(notesArea));
      inner.add(Box.createHorizontalStrut(10));
      main.add(inner);
      main.add(Box.createVerticalStrut(10));
      add(main);
      notesArea.getDocument().addDocumentListener(this);
   }
   public void init(){
      // get data from puzzle and store in panel
      description.getDocument().removeDocumentListener(this);
      description.setText(puz.getDescription());
      description.getDocument().addDocumentListener(this);

      notesArea.getDocument().removeDocumentListener(this);
      notesArea.setText(puz.getNotes());
      notesArea.getDocument().addDocumentListener(this);
   }
   public void exit(){}
   public void initColors(){
   }

   public void changedUpdate(DocumentEvent de) {
      updateText(de);
   }
   public void insertUpdate(DocumentEvent de) {
      updateText(de);
   }
   public void removeUpdate(DocumentEvent de) {
      updateText(de);
   }
   private void updateText(DocumentEvent de){
      if( de.getDocument() == description.getDocument()){
         puz.setDescription(description.getText());
      }else if( de.getDocument() == notesArea.getDocument()){
         puz.setNotes(notesArea.getText());
      }
   }
}