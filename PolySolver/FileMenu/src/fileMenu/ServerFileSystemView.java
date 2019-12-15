package fileMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import fileMenu.ServerUtil;

public class ServerFileSystemView
   extends FileSystemView
   implements Comparator<String>
{

   private final File rootDirectory;
   private final File[] roots = new File[1];
   private final String rootname;
   private File currentDirectory;
   private Map<String, File> lastDirContents = new HashMap<String, File>();
   private Icon fileIcon, protectedFileIcon, directoryIcon;
   private static final String icondir = "images/";
   
   public ServerFileSystemView(String rootname0) {
      rootname = rootname0;
      rootDirectory = new ServerFile(rootname);
      roots[0] = rootDirectory;
      currentDirectory = rootDirectory;

      fileIcon = loadIcon("fileicon.gif");
      protectedFileIcon = loadIcon("pwfileicon.gif");
      directoryIcon = loadIcon("diricon.gif");
   }
   
   public static Icon loadIcon(String name){
      URL imageURL = FileMenu.class.getResource(icondir+name);
      return imageURL == null ? null : new ImageIcon(imageURL);
   }
   

   @Override
   public File createNewFolder(File parentdir) throws IOException {

      //System.out.println("createNewFolder: "+parentdir);
      
      String result = ServerUtil.submit(ServerUtil.CREATEDIR, new String[]{
            "dir", parentdir.getPath(),
            "filename", "NewFolder",
            });
      if( result==null || ! result.startsWith("Ok\n") ){
         throw new IOException(result == null ? "directory creation failed" : result);
      }
      
      // directory successfully created
      String dirname = result.substring(3).trim();
      return new ServerFile(parentdir, dirname, true, true, false);
   }
 
   @Override
   public File createFileObject(File parentdir, String filename) {
      //System.out.println("createFileObject: "+parentdir+","+filename);

      // return cached file from directory listing
      File f = lastDirContents.get(filename);
      if( f!=null ) return f;

      // return new file
      return new ServerFile(parentdir, filename);
   }
 
   @Override
   public File createFileObject(String path) {
      //System.out.println("createFileObject: "+path);

      // return cached file from directory listing
      File f = lastDirContents.get(path);
      if( f!=null ) return f;

      // return new file
      return new ServerFile(currentDirectory, path);
   }
 
   @Override
   protected File createFileSystemRoot(File f) {
      //System.out.println("createFileSystemRoot: "+f);
      return null;
   }
 
   @Override
   public File getChild(File parent, String fileName) {
      //System.out.println("getChild: " + parent+", "+fileName);
      return new ServerFile(parent, fileName);
   }
 
   @Override
   public File getDefaultDirectory() {
      //System.out.println("getDefault");
      return rootDirectory;
   }
 
   @Override
   public File[] getFiles(File dir, boolean useFileHiding) {
      //System.out.println("getFiles: " + dir + ", "+useFileHiding);
      
      String result = ServerUtil.submit(ServerUtil.READDIR, new String[]{
         "dir", dir.getPath(),
      });
      if( result==null || ! result.startsWith("Ok\n") ){
         if( result!=null ) System.out.println(result);
         return null;
      }
      // directory successfully read
      result = result.substring(3).trim();
      String[] namelist = result.isEmpty() ? new String[0] : result.split("\n");
      Arrays.sort(namelist, this);
      File[] filelist = new File[namelist.length];
      lastDirContents.clear();
      for( int i=0; i<namelist.length; i++ ){
         String[] fields = namelist[i].split("\t");
         boolean isdir = fields.length>3 && fields[3].equals("1");
         boolean haspass = fields.length>4 && fields[4].equals("1");
         ServerFile f = new ServerFile(dir, fields[0], isdir, true, haspass);
         if( fields.length>1 ) f.setLength(Long.parseLong(fields[1]));
         if( fields.length>2 ) f.setLastModified(1000 * Long.parseLong(fields[2]));
         filelist[i] = f;
         //System.out.println(namelist[i]+" : "+getSystemDisplayName(f));
         
         lastDirContents.put(f.getName(), filelist[i]);
      }
      currentDirectory = dir;
      return filelist;
   }
 
   @Override
   public File getHomeDirectory() {
      //System.out.println("getHome");
      return rootDirectory;
   }
 
   @Override
   public File getParentDirectory(File dir) {
      //System.out.println("getParent: "+dir);
      return dir.getParentFile();
   }
 
   @Override
   public File[] getRoots() {
      //System.out.println("getRoots");
      return roots;
   }
 
   @Override
   public String getSystemDisplayName(File f) {
      //System.out.println("getSystemDisplayName: " + f+" : "+dn);
      return f.getName();
   }
 
   @Override
   public Icon getSystemIcon(File f) {
      //System.out.println("getSystemIcon: " + f);
      if( f.isDirectory() ) return directoryIcon;
      if( ((ServerFile)f).hasPassword() ) return protectedFileIcon;
      return fileIcon;
   }
 
   @Override
   public String getSystemTypeDescription(File f) {
      System.out.println("getSystemTypeDescription: " + f);
      return "Description";
   }
 
   @Override
   public boolean isFileSystem(File f) {
      System.out.println("isFileSystem: " + f);
      return true;
   }
 
   @Override
   public boolean isFileSystemRoot(File dir) {
      System.out.println("isFileSystemRoot: " + dir);
      return false;
   }
 
   @Override
   public boolean isHiddenFile(File f) {
      System.out.println("isHiddenFile: " + f);
      return false;
   }
 
   @Override
   public boolean isParent(File folder, File file) {
      //System.out.println("isParent: " + folder+", "+file);
      return folder.equals(file.getParentFile());
   }
 
   @Override
   public boolean isRoot(File f) {
      //System.out.println("isRoot: " + f);
      return rootDirectory == f;
   }
 
   @Override
   public Boolean isTraversable(File f) {
      //System.out.println("isTraversable: " + f);
      return f.isDirectory();
   }

   // ---
   public int compare(String strA, String strB) {
      return strA.compareToIgnoreCase(strB);
   }

}
