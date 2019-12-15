package fileMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ServerUtil {
   private static final String[] scripts = {
      "readdir2", "readfile2","writefile2","deletefile2","renamefile2","createdir2","changepass2"
   };
   private static final String scriptDir = "http://www.jaapsch.net/scripts/";

   public static final int READDIR = 0;
   public static final int READFILE = 1;
   public static final int WRITEFILE = 2;
   public static final int DELETEFILE = 3;
   public static final int RENAMEFILE = 4;
   public static final int CREATEDIR = 5;
   public static final int CHANGEPASS = 6;

   public static String submit(int task, String[] params){
      try {
         //System.out.println(scripts[task]+" :");
         // Construct data
         String data = "";
         if( params !=null ){
            for( int i=0; i<params.length; i+=2 ){
               if( i>0 ) data += "&";
               data += URLEncoder.encode(params[i], "UTF-8") + "=" + URLEncoder.encode(params[i+1], "UTF-8");
               //System.out.println("   " + params[i] + "=" + params[i+1]);
            }
         }

         // Send data
         URL url = new URL(scriptDir+scripts[task]+".php");
         URLConnection conn = url.openConnection();
         conn.setDoOutput(true);
         OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
         wr.write(data);
         wr.flush();

         // Get the response
         BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         String response = "";
         String line;
         while ((line = rd.readLine()) != null) {
             response += line + '\n';
         }
         wr.close();
         rd.close();
         return response;
     } catch (IOException e) {
        return e.toString();
     }
   }

   public static String checkFileName(String filename){
      if( filename.isEmpty() ) return "Please enter a file name.";
      if( ! filename.matches("^[0-9a-zA-Z_]*$") )
         return "The file name may only contain alphanumeric characters and underscores.";
      return null;
   }
}
