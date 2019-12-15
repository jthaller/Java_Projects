package fileMenu;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public class ServerFile
   extends File
{
   private File parent;
   private String name;
   private boolean isDir;
   private boolean exists;
   private boolean hasPassword;
   private long length = 1;
   private long lastMod = 1;
   static char serverSeparator = '/';

   // create file object to represent a new file
   public ServerFile(File parent0, String filename) {
      super(filename);
      parent = parent0;
      name = filename;
      isDir = false;
      exists = false;
      hasPassword = false;
   }
   // create file object to represent a existing file
   public ServerFile(File parent0, String filename, boolean isDir0, boolean exists0, boolean hasPassword0) {
      super(filename);
      parent = parent0;
      name = filename;
      isDir = isDir0;
      exists = exists0;
      hasPassword = hasPassword0;
      //System.out.println("creating file "+parent+" // "+name+" = "+getPath());
   }

   // create root file
   public ServerFile(String filename) {
      super(filename);
      parent = null;
      name = filename;
      isDir = true;
      exists = true;
   }

   @ Override
   public boolean isDirectory () {
      return isDir;
   }

   @ Override
   public boolean exists () {
      return exists;
   }

   public boolean hasPassword () {
      return hasPassword;
   }

   @ Override
   public String getCanonicalPath() { 
      return getPath();
   }   
   @ Override
   public File getCanonicalFile() { 
      return this;
   }

   @Override
   //Returns the name of the file or directory denoted by this abstract pathname.
   public String getName(){
      return name;
   }

   @Override
   //Returns the pathname string of this abstract pathname.
   public String toString(){
      return getPath();
   }

   @Override
   //Returns the abstract pathname of this abstract pathname's parent, or null if this pathname does not name a parent directory.
   public File getParentFile(){
      return parent;
   }

   @Override
   //Returns the pathname string of this abstract pathname's parent, or null if this pathname does not name a parent directory.
   public String getParent(){
      return getParentFile().getPath();
   }

   @Override
   //Tests this abstract pathname for equality with the given object.
   public boolean equals(Object obj){
      if( obj==null && !(obj instanceof ServerFile)) return false;
      ServerFile f2 = (ServerFile)obj;
      return getPath().equals(f2.getPath()) && hasPassword == f2.hasPassword;
   }

   @Override
   //Computes a hash code for this abstract pathname.
   public int hashCode(){
      return getCanonicalPath().hashCode();
   }

   @Override
   //Converts this abstract pathname into a pathname string.
   public String getPath(){
      return (parent==null ? "" : parent.getPath()+serverSeparator) + name;
   }

   @Override
   //Returns the absolute pathname string of this abstract pathname.
   public String getAbsolutePath(){
      return getPath();
   }

   @Override
   //Tests whether this abstract pathname is absolute.
   public boolean isAbsolute(){
      return true;
   }

   @Override
   //Returns the absolute form of this abstract pathname.
   public File getAbsoluteFile(){
      return this;
   }

   @Override
   //Tests whether the file denoted by this abstract pathname is a normal file.
   public boolean isFile(){
      return !isDir;
   }

   @Override
   //Tests whether the file named by this abstract pathname is a hidden file.
   public boolean isHidden(){
      return false;
   }

   @Override
   //Tests whether the application can execute the file denoted by this abstract pathname.
   public boolean canExecute(){
      System.out.println("File " + this + " canExecute");
      return false;
   }

   @Override
   //Compares two abstract pathnames lexicographically.
   public int compareTo(File pathname){
      System.out.println("File " + this + " compareto: " + pathname);
      return getPath().compareToIgnoreCase(pathname.getPath());
   }

   @Override
   //Renames the file denoted by this abstract pathname.
   public boolean renameTo(File dest){
      //System.out.println("File " + this + " renameto: " + dest);

      if( dest.exists()) return false;
      
      String newfilename = dest.getName();
      String err = ServerUtil.checkFileName(newfilename);
      if( err!=null ) return false;

      String password =  isDir || !hasPassword ? "" : FileMenu.filemenu.getPassword(false);

      // rename
      err = ServerUtil.submit(ServerUtil.RENAMEFILE, new String[]{
         "dir1", getParent(),
         "dir2", dest.getParent(),
         "filename1", getName(),
         "filename2", dest.getName(),
         "password", password
      });

      if( !err.startsWith("Ok\n") ){
         System.out.println(err);
         return false;
      }
      name = dest.getName();
      return true;
   }


   @Override
   //Returns the length of the file denoted by this abstract pathname.
   public long length(){
      //System.out.println("File " + this + " length");
      return length;
   }

   public void setLength(long ln){
      length = ln;
   }

   @Override
   //Returns the time that the file denoted by this abstract pathname was last modified.
   public long lastModified(){
      //System.out.println("File " + this + " lastmodified");
      return lastMod;
   }

   @Override
   //Sets the last-modified time of the file or directory named by this abstract pathname.
   public boolean setLastModified(long time){
      //System.out.println("File " + this + " setlastmodified: "+time);
      lastMod = time;
      return true;
   }










   @Override
   //Tests whether the application can read the file denoted by this abstract pathname.
   public boolean canRead(){
      System.out.println("File " + this + " canread");
      throw new UnsupportedOperationException();
   }

   @Override
   //Tests whether the application can modify the file denoted by this abstract pathname.
   public boolean canWrite(){
      System.out.println("File " + this + " canwrite");
      throw new UnsupportedOperationException();
   }

   @Override
   //Atomically creates a new, empty file named by this abstract pathname if and only if a file with this name does not yet exist.
   public boolean createNewFile(){
      System.out.println("File " + this + " createNewFile");
      throw new UnsupportedOperationException();
   }

   @Override
   //Deletes the file or directory denoted by this abstract pathname.
   public boolean delete(){
      String password =  isDir || !hasPassword ? "" : FileMenu.filemenu.getPassword(false);
      if( password == null ) return false;

      // rename
      String err = ServerUtil.submit(ServerUtil.DELETEFILE, new String[]{
         "dir", getParent(),
         "filename", getName(),
         "password", password
      });

      if( !err.startsWith("Ok\n") ){
         System.out.println(err);
         return false;
      }
      return true;
   }

   @Override
   //Requests that the file or directory denoted by this abstract pathname be deleted when the virtual machine terminates.
   public void deleteOnExit(){
      System.out.println("File " + this + " deleteOnExit");
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns the number of unallocated bytes in the partition named by this abstract path name.
   public long getFreeSpace(){
      System.out.println("File " + this + " getFreeSpace");
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns the size of the partition named by this abstract pathname.
   public long getTotalSpace(){
      System.out.println("File " + this + " getTotalSpace");
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns the number of bytes available to this virtual machine on the partition named by this abstract pathname.
   public long getUsableSpace(){
      System.out.println("File " + this + " getUsableSpace");
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname.
   public String[] list(){
      System.out.println("File " + this + " list");
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter.
   public String[] list(FilenameFilter filter){
      System.out.println("File " + this + " list: "+filter);
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns an array of abstract pathnames denoting the files in the directory denoted by this abstract pathname.
   public File[] listFiles(){
      System.out.println("File " + this + " listfiles");
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns an array of abstract pathnames denoting the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter.
   public File[] listFiles(FileFilter filter){
      System.out.println("File " + this + " listfiles: "+filter);
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns an array of abstract pathnames denoting the files and directories in the directory denoted by this abstract pathname that satisfy the specified filter.
   public File[] listFiles(FilenameFilter filter){
      System.out.println("File " + this + " listfiles: "+filter);
      throw new UnsupportedOperationException();
   }

   @Override
   //Creates the directory named by this abstract pathname.
   public boolean mkdir(){
      System.out.println("File " + this + " mkdir");
      throw new UnsupportedOperationException();
   }

   @Override
   //Creates the directory named by this abstract pathname, including any necessary but nonexistent parent directories.
   public boolean mkdirs(){
      System.out.println("File " + this + " mkdirs");
      throw new UnsupportedOperationException();
   }

   @Override
   //A convenience method to set the owner's execute permission for this abstract pathname.
   public boolean setExecutable(boolean executable){
      System.out.println("File " + this + " setexecutable");
      throw new UnsupportedOperationException();
   }

   @Override
   //Sets the owner's or everybody's execute permission for this abstract pathname.
   public boolean setExecutable(boolean executable, boolean ownerOnly){
      System.out.println("File " + this + " setexecutable");
      throw new UnsupportedOperationException();
   }

   @Override
   //A convenience method to set the owner's read permission for this abstract pathname.
   public boolean setReadable(boolean readable){
      System.out.println("File " + this + " setreadable");
      throw new UnsupportedOperationException();
   }

   @Override
   //Sets the owner's or everybody's read permission for this abstract pathname.
   public boolean setReadable(boolean readable, boolean ownerOnly){
      System.out.println("File " + this + " setreadable");
      throw new UnsupportedOperationException();
   }

   @Override
   //Marks the file or directory named by this abstract pathname so that only read operations are allowed.
   public boolean setReadOnly(){
      System.out.println("File " + this + " setreadonly");
      throw new UnsupportedOperationException();
   }

   @Override
   //A convenience method to set the owner's write permission for this abstract pathname.
   public boolean setWritable(boolean writable){
      System.out.println("File " + this + " setwritable");
      throw new UnsupportedOperationException();
   }

   @Override
   //Sets the owner's or everybody's write permission for this abstract pathname.
   public boolean setWritable(boolean writable, boolean ownerOnly){
      System.out.println("File " + this + " setwritable");
      throw new UnsupportedOperationException();
   }

   @Override
   //Returns a java.nio.file.Path object constructed from the this abstract path.
   public Path toPath(){
      System.out.println("File " + this + " topath");
      throw new UnsupportedOperationException();
   }

   @Override
   //Constructs a file: URI that represents this abstract pathname.
   public URI toURI(){
      throw new UnsupportedOperationException();
   }

   @SuppressWarnings("deprecation")
   //This method does not automatically escape characters that are illegal in URLs. It is recommended that new code convert an abstract pathname into a URL by first converting it into a URI, via the toURI method, and then converting the URI into a URL via the URI.toURL method.
   @Deprecated
   @Override
   public URL toURL(){
      throw new UnsupportedOperationException();
   }
}
