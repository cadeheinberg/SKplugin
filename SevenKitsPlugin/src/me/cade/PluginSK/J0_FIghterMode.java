package me.cade.PluginSK;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class J0_FIghterMode {
  
  private static ArrayList<String> inMode;
  
  public static void setUpClass() {
    inMode = new ArrayList<String>();
  }
  
  public static void addFighterToList(Player player) {
    inMode.add(player.getName());
  }
  
  public static void removeFighterFromList(Player player) {
    inMode.remove(player.getName());
  }
  
  public static boolean listContainsPlayer(Player player) {
    return inMode.contains(player.getName());
  }

  public static ArrayList<String> getInMode() {
    return inMode;
  }

  public static void setInMode(ArrayList<String> inMode) {
    J0_FIghterMode.inMode = inMode;
  }

}
