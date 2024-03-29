package me.cade.PluginSK;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import me.cade.PluginSK.HiddenPersonal.DatabaseAccess;

public class MySQL {

  private java.sql.Connection connection;
  private String host;
  private String database;
  private String username;
  private String password;
  private int port;
  
  private String tableName;
  public String[] column;
  
  private static Plugin plugin = Main.getPlugin(Main.class);
  
  public MySQL() {

    host = DatabaseAccess.getHost();
    port = DatabaseAccess.getPort();
    database = DatabaseAccess.getDatabase();
    username = DatabaseAccess.getUsername();
    password = DatabaseAccess.getPassword();

    try {
    Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL: CONNECTING...");
      connect();
      Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL: CONNECTED");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL: ERROR 1");
    } catch (SQLException e) {
      e.printStackTrace();
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL: ERROR 2");
    }
    
    tableName = "playerstats";
    column = new String[17];
    column[0] = "UUID";
    column[1] = "PlayerName";
    column[2] = "KitID";
    column[3] = "KitIndex";
    column[4] = "PlayerLevel";
    column[5] = "Kills";
    column[6] = "KillStreak";
    column[7] = "Deaths";
    column[8] = "Cakes";
    column[9] = "Exp";
    column[10] = "Kit00";
    column[11] = "Kit01";
    column[12] = "Kit02";
    column[13] = "Kit03";
    column[14] = "Kit04";
    column[15] = "Kit05";
    column[16] = "Kit06";
    
    refreshConnection();

  }
  
  public void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void connect() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.jdbc.Driver");
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setServerName(host);
    dataSource.setPort(port);
    dataSource.setDatabaseName(database);
    dataSource.setUser(username);
    dataSource.setPassword(password);
    connection = dataSource.getConnection();
  }
  
  public void addScore(Player player) {
    String insertStatement = "INSERT INTO " + tableName + "(";
    for (int i = 0; i < column.length; i++) {
      if (i == column.length - 1) {
        insertStatement = insertStatement + column[i] + ")";
      } else {
        insertStatement = insertStatement + column[i] + ",";
      }
    }
    insertStatement = insertStatement + " VALUES (";
    for (int i = 0; i < column.length; i++) {
      if (i == column.length - 1) {
        insertStatement = insertStatement + "?)";
      } else {
        insertStatement = insertStatement + "?,";
      }
    }
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(insertStatement);
      statement.setString(1, player.getUniqueId().toString());
      statement.setString(2, player.getName());
      statement.setInt(3, 0);
      statement.setInt(4, 0);
      statement.setInt(5, 1);
      statement.setInt(6, 0);
      statement.setInt(7, 0);
      statement.setInt(8, 0);
      statement.setInt(9, 500);
      statement.setInt(10, 0);
      statement.setInt(11, 0);
      statement.setInt(12, 0);
      statement.setInt(13, 0);
      statement.setInt(14, 0);
      statement.setInt(15, 0);
      statement.setInt(16, 0);
      statement.setInt(17, 0);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public int getStat(Player player, String stat) {
    int getter = 0;
    PreparedStatement statement;
    try {
      statement = connection
        .prepareStatement("SELECT " + stat + " FROM " + tableName + " WHERE " + column[0] + " = ?");
      statement.setString(1, player.getUniqueId().toString());
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        getter = result.getInt(stat);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return getter;
  }
  
  public void setStat(String uuid, String stat, int setter) {
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(
        "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[0] + " = ?");
      statement.setString(2, uuid);
      statement.setInt(1, setter);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void updateName(Player player, String stat, String setter) {
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(
        "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[0] + " = ?");
      statement.setString(2, player.getUniqueId().toString());
      statement.setString(1, setter);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void incOfflineStat(String name, String stat, int setter) {
    // GET THE AMOUNT OF STAT
    int getter = 0;
    {
      PreparedStatement statement;
      try {
        statement = connection.prepareStatement(
          "SELECT " + stat + " FROM " + tableName + " WHERE " + column[1] + " = ?");
        statement.setString(1, name);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
          getter = result.getInt(stat);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    // INCREASE THE AMOUNT OF STAT
    {
      PreparedStatement statement;
      try {
        statement = connection.prepareStatement(
          "UPDATE " + tableName + " SET " + stat + " = ? WHERE " + column[1] + " = ?");
        statement.setString(2, name);
        statement.setInt(1, setter + getter);
        statement.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean playerExists(Player player) {
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(
        "SELECT " + column[2] + " FROM " + tableName + " WHERE " + column[0] + " = ?");
      statement.setString(1, player.getUniqueId().toString());
      ResultSet results = statement.executeQuery();
      if (results.next()) {
        return true;
      }
      return false;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
  
  public void refreshConnection() {
	new BukkitRunnable(){
        @Override
        public void run() {
        	Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL: REFRESHING CONNECTION");
            try {
                connection.isValid(0);
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL: REFRESHED");
            } catch (SQLException e) {
                e.printStackTrace();
            }              
        }
    }.runTaskTimer(plugin, 20*60*60*7, 20*60*60*7);
  }
  
}
