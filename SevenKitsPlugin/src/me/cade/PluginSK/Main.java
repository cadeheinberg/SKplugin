package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
  
  public static World kitpvp;
  
  @Override
  public void onEnable() {
    getConfig().options().copyDefaults(true);
    saveConfig();
    //establish mysql connecton
    setLocations();
    //build kits
    //spawn npcs
  }
  
  @Override
  public void onDisable() {
    //close mysql connection
  }
  
  public void setLocations() {
    Bukkit.getServer().createWorld(new WorldCreator("kitpvp"));
    kitpvp = Bukkit.getServer().getWorld("kitpvp");
  }

}
