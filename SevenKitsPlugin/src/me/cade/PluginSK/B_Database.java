package me.cade.PluginSK;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class B_Database extends JavaPlugin implements Listener {

  private static Connection connection;
  private static String host;
  private static String database;
  private static String username;
  private static String password;
  private static String tableName;
  public static String[] column;
  private static int port;

  public static void startConnection() {

    host = "67.222.138.243";
    port = 3306;
    database = "mc10515";
    username = "mc10515";
    password = "452db2354c";

    try {
      connect();
      Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL CONNECTED");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL ERROR 1");
    } catch (SQLException e) {
      e.printStackTrace();
      Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "MYSQL ERROR 2");
    }

    tableName = "playerstats";
    column = new String[24];
    column[0] = "UUID";
    column[1] = "PlayerName";
    column[2] = "KitID";
    column[3] = "KitIndex";
    column[4] = "PlayerLevel";
    column[5] = "Prestige";
    column[6] = "Kills";
    column[7] = "Deaths";
    column[8] = "Cakes";
    column[9] = "KillStreak";
    column[10] = "KillStreakAttribute";
    column[11] = "SpeedAttribute";
    column[12] = "SpecialItemAttribute";
    column[13] = "ProtectionArmorAttribute";
    column[14] = "Noob";
    column[15] = "Booster";
    column[16] = "Shotty";
    column[17] = "Goblin";
    column[18] = "Igor";
    column[19] = "Heavy";
    column[20] = "Wizard";
    column[21] = "Exp";
    column[22] = "ArmorColor";
    column[23] = "Tokens";

  }

  public static void closeConnection() {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void connect() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.jdbc.Driver");
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setServerName(host);
    dataSource.setPort(port);
    dataSource.setDatabaseName(database);
    dataSource.setUser(username);
    dataSource.setPassword(password);
    connection = dataSource.getConnection();
  }

  public static void addScore(Player player) {
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
      statement.setInt(3, 1);
      statement.setInt(4, 0);
      statement.setInt(5, 1);
      statement.setInt(6, 1);
      statement.setInt(7, 0);
      statement.setInt(8, 0);
      statement.setInt(9, 500);
      statement.setInt(10, 0);
      statement.setInt(11, 0);
      statement.setInt(12, 0);
      statement.setInt(13, 0);
      statement.setInt(14, 0);
      statement.setInt(15, -1);
      statement.setInt(16, -1);
      statement.setInt(17, -1);
      statement.setInt(18, -1);
      statement.setInt(19, -1);
      statement.setInt(20, -1);
      statement.setInt(21, -1);
      statement.setInt(22, 0);
      statement.setInt(23, -1);
      statement.setInt(24, 5);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static int getStat(Player player, String stat) {
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

  public static void setStat(String uuid, String stat, int setter) {
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

  public static void updateName(Player player, String stat, String setter) {
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

  public static void incOfflineStat(String name, String stat, int setter) {
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

  public static boolean playerExists(Player player) {
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

  public static ArrayList<String> getKDLeaders() {
    ArrayList<String> topKDs = new ArrayList<>();
    PreparedStatement statement;
    try {
      // error here
      statement = connection.prepareStatement(
        "SELECT *, (Kills / Deaths) AS KD FROM playerstats ORDER BY KD DESC LIMIT 3");
      ResultSet result = statement.executeQuery();
      while (result != null && result.next()) {
        double kd = result.getDouble("kd");
        topKDs.add("" + kd);
        String playerName = result.getString("PlayerName");
        topKDs.add(playerName);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return topKDs;
  }

  public static ArrayList<String> getKillsLeaders() {
    ArrayList<String> tops = new ArrayList<>();
    PreparedStatement statement;
    try {
      // error here
      statement = connection
        .prepareStatement("SELECT *, Kills AS KD FROM playerstats ORDER BY KD DESC LIMIT 3");
      ResultSet result = statement.executeQuery();
      while (result != null && result.next()) {
        int kd = result.getInt("kd");
        tops.add("" + kd);
        String playerName = result.getString("PlayerName");
        tops.add(playerName);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return tops;
  }

  public static void resetAllPlayers() {
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(
        "SELECT *, UUID AS KD FROM playerstats");
      ResultSet result = statement.executeQuery();
      while (result != null && result.next()) {
        String playerUUID = result.getString("kd");
        for(int i = 2; i < 23; i++) { 
        setStat(playerUUID, column[i], E3_Defaults.setters[i]);
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
  
  public static void resetAllPlayersKillsDeaths() {
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(
        "SELECT *, UUID AS KD FROM playerstats");
      ResultSet result = statement.executeQuery();
      while (result != null && result.next()) {
        String playerUUID = result.getString("kd");
        setStat(playerUUID, column[6], E3_Defaults.setters[6]);
        setStat(playerUUID, column[7], E3_Defaults.setters[7]);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

}
