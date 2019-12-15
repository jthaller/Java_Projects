public class StringList
{
    private boolean empty = false;
    private StringList restOfWords;
    private String firstWord;
   
    public StringList(){
       empty = true;
    }
    public StringList( String newWord, StringList existingList ){
        firstWord = newWord;
        restOfWords = existingList;
    }
    public boolean contains( String word ){
        if ( empty ) {
            return false;
        } else if (firstWord.equals( word ) ) {
            return true;
        } else {
            return restOfWords.contains( word );
        }
    }
 
   
}
