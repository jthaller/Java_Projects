package settingsmodule;

import java.util.prefs.Preferences;

public class SettingsStoreApplication
   extends SettingsStore
{
   Preferences prefs;

   SettingsStoreApplication(Class<?> applicationClass){
      prefs = Preferences.userNodeForPackage(applicationClass);
   }

   // setting storage
   protected String retrieveSettingFromStore(String key){
      return prefs.get(key, null);
   }
   protected void removeSettingFromStore(String key){
      prefs.remove(key);
   }
   protected void storeSettingInStore(String key, String value){
      prefs.put(key,value);
   }
}
