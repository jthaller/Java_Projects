package settingsmodule;

import java.applet.Applet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JOptionPane;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

public class SettingsStoreApplet
   extends SettingsStore
{
   Applet applet;
   String prefix;

   SettingsStoreApplet(Applet applet0, String prefix0){
      applet = applet0;
      prefix = prefix0;
   }

   // setting storage
   protected String retrieveSettingFromStore(String key){
      return readCookie(prefix+key);
   }
   protected void removeSettingFromStore(String key){
      createCookie(prefix+key,"",-1);
   }
   protected void storeSettingInStore(String key, String value){
      createCookie(prefix+key,value,365);
   }

   private void createCookie(String key, String value, long days) {
      String expires = "";
      if( days!=0 ){
         Date date = new Date();
         date.setTime(date.getTime() + days*24*60*60*1000);

         final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z" ;
         final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
         sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

         expires = "; expires="+sdf.format(date);

      }
      try {
         JOptionPane.showMessageDialog(null, "setting:"+expires, "File Error", JOptionPane.ERROR_MESSAGE);
         JSObject window = JSObject.getWindow(applet);
         JSObject document = (JSObject)window.getMember( "document" );
         document.setMember( "cookie", key + "=" + value + expires + "; path=/" );
      } catch (JSException e) {
         throw new RuntimeException("Cookie not available");
      }
   }

   private String readCookie(String key) {
      try {
         String keyEQ = key + "=";
         JSObject window = JSObject.getWindow(applet);
         JSObject document = (JSObject)window.getMember( "document" );
         String[] ca = ((String)document.getMember("cookie")).split(";");
         for(String c : ca) {
            c = c.trim();
            if( c.startsWith(keyEQ) )
               return c.substring(keyEQ.length());
         }
      } catch (JSException e) {
         throw new RuntimeException("Cookie not available");
      }
      return null;
   }
}


