package settingsmodule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public abstract class SettingsStore
{
   private Map<String, String> currentSettings = new TreeMap<String, String>();
   private boolean storeIsOk = true;

   String retrieveSetting(String key){
      return currentSettings.get(key);
   }
   void removeSetting(String key){
      currentSettings.remove(key);
      if( storeIsOk ){
         try{
            removeSettingFromStore(key);
         }catch( Exception e ){
            disableStore();
         }
      }
   }
   void storeSetting(String key, String value){
      currentSettings.put(key,value);
      if( !storeIsOk ) return;
      try{
         storeSettingInStore(key, value);
      }catch( Exception e ){
         disableStore();
      }
   }
   protected void initialise( Set<String> keys){
      if( !storeIsOk ) return;
      try{
         for( String key : keys ){
            String value = retrieveSettingFromStore(key);
            if( value !=null ) currentSettings.put(key,value);
         }
      }catch( Exception e ){
         disableStore();
      }
   }

   public void disableStore(){
      storeIsOk = false;
      JOptionPane.showMessageDialog(null, "Your settings could not be saved. The next time you start this program, default settings will be used.", "File Error", JOptionPane.ERROR_MESSAGE);
   }

   // Access to system's storage
   abstract protected String retrieveSettingFromStore(String key);
   abstract protected void removeSettingFromStore(String key);
   abstract protected void storeSettingInStore(String key, String value);

   // saving to file
   public void exportSettings(OutputStream os) throws IOException{
      OutputStreamWriter bw = new OutputStreamWriter(os);
      for( Map.Entry<String,String> pair : currentSettings.entrySet() ){
         bw.write( pair.getKey() );
         bw.write( '=' );
         bw.write( pair.getValue() );
         bw.write( "\n" );
      }
      bw.close();
   }
   public void importSettings(InputStream is) throws IOException{
      // remove everything
      for( String key : currentSettings.keySet() ) removeSetting(key);
      // load from file
      BufferedReader br = new BufferedReader(new InputStreamReader( is ));
      String d = br.readLine();
      while( d!=null ){
         d = d.trim();
         if( d.startsWith("#") || d.startsWith("//") ) continue;
         int ix = d.indexOf('=');
         if( ix<0 ) continue;
         String key = d.substring(0,ix);
         String value = d.substring(ix+1);
         storeSetting(key, value);
         d = br.readLine();
      }
      br.close();

   }


}
