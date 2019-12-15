package settingsmodule;

import java.applet.Applet;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

public class SettingsManager
{
   private Map<String, String> defaultSettings = new TreeMap<String, String>();
   private SettingsStore store;
   private boolean initialised = false;

   SettingsManager( SettingsStore s ) {
      store = s;
   }

   public void registerStringSetting(String key, String defaultValue){
      if( initialised )
         throw new RuntimeException("Cannot register new application settings after initialisation.");
      defaultSettings.put(key, defaultValue);
   }

   public void initialise(){
      if( !initialised )
         store.initialise(defaultSettings.keySet());
      initialised = true;
   }

   public void reset(){
      for( Map.Entry<String,String> pair : defaultSettings.entrySet() ){
         setStringSetting( pair.getKey(), pair.getValue() );
      }
   }

   private String getDefaultSetting(String key){
      String value = defaultSettings.get(key);
      if( value==null )
         throw new RuntimeException("Setting key "+key+" has not been registered.");
      return value;
   }
   public void setStringSetting(String key, String value){
      initialise();
      String oldVal = getStringSetting(key);
      if( oldVal.equals(value) ) return;
      String defVal = getDefaultSetting(key);
      if( defVal.equals(value) ){
         store.removeSetting(key);
      }else{
         store.storeSetting(key,value);
      }
   }

   public String getStringSetting(String key){
      initialise();
      String defVal = getDefaultSetting(key);
      String value = store.retrieveSetting(key);
      return value == null ? defVal : value;
   }

   // saving to file
   public void exportSettings(OutputStream os) throws IOException{
      store.exportSettings(os);
   }
   public void importSettings(InputStream is) throws IOException{
      store.importSettings(is);
   }

   // storage of other setting types in terms of string settings

   // integer
   public void registerIntSetting(String key, int defaultValue){
      registerStringSetting(key, Integer.toString(defaultValue));
   }
   public void setIntSetting(String key, int value){
      setStringSetting(key, Integer.toString(value));
   }
   public int getIntSetting(String key){
      return Integer.parseInt( getStringSetting(key) );
   }

   // long
   public void registerLongSetting(String key, long defaultValue){
      registerStringSetting(key, Long.toString(defaultValue));
   }
   public void setLongSetting(String key, long value){
      setStringSetting(key, Long.toString(value));
   }
   public long getLongSetting(String key){
      return Long.parseLong( getStringSetting(key) );
   }

   // double
   public void registerDoubleSetting(String key, double defaultValue){
      registerStringSetting(key, Double.toString(defaultValue));
   }
   public void setDoubleSetting(String key, double value){
      setStringSetting(key, Double.toString(value));
   }
   public double getDoubleSetting(String key){
      return Double.parseDouble( getStringSetting(key) );
   }

   // color
   public void registerColorSetting(String key, Color defaultValue){
      registerIntSetting(key, defaultValue.getRGB());
   }
   public void setColorSetting(String key, Color value){
      setIntSetting(key, value.getRGB());
   }
   public Color getColorSetting(String key){
      return new Color( getIntSetting(key) );
   }


   //-------------

   static public SettingsManager createSettingsManager( Applet applet, String prefix ){
      SettingsStore store = new SettingsStoreApplet(applet, prefix);
      String appletContext = applet.getAppletContext().toString();
      if (appletContext.startsWith("sun.applet.AppletViewer")){
         store.disableStore();
      }
      return new SettingsManager(store);
   }
   static public SettingsManager createSettingsManager( Class<?> applicationClass ){
      return new SettingsManager(new SettingsStoreApplication(applicationClass));
   }
}
