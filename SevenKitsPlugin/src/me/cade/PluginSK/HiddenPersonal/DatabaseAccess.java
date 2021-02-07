package me.cade.PluginSK.HiddenPersonal;

public class DatabaseAccess {
  
  private static final String host = "localhost";
  private static final String database = "SevenKits";
  private static final String username = "root";
  private static final String password = "Sanders425362!";
  private static final int port = 3306;
  
  public static String getHost() {
    return host;
  }
  
  public static String getDatabase() {
    return database;
  }
  
  public static String getUsername() {
    return username;
  }
  
  public static String getPassword() {
    return password;
  }
  
  public static int getPort() {
    return port;
  }

}
