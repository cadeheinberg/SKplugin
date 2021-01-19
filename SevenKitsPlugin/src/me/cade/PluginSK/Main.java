package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import me.cade.PluginSK.BuildKits.*;
import me.cade.PluginSK.Damaging.*;
import me.cade.PluginSK.KitListeners.G_KitListener;
import me.cade.PluginSK.NPCS.*;
import me.cade.PluginSK.Permissions.BasicPermissions;
import me.cade.PluginSK.PlayerJoin.*;

public class Main extends JavaPlugin{
  
  //eating cake
  //charges
  //the fighter ability
  
  public static World hub;
  public static Location hubSpawn;
  
  @Override
  public void onEnable() {
    getConfig().options().copyDefaults(true);
    saveConfig();
    Database.startConnection();
    setLocations();
    F_KitBuilder.buildAllKits();
    D_SpawnKitSelectors.removeAllNpcs();
    D_SpawnAllNPCS.spawnNPCS();
    registerListeners();
    addPlayersToFighters();
  }

  private void registerListeners() {
    Bukkit.getServer().getPluginManager().registerEvents(new D0_NpcListener(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new FallDamageListener(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new G_KitListener(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new BasicPermissions(), this);
  }
  
  @Override
  public void onDisable() {
    Database.closeConnection();
  }
  
  public void setLocations() {
    Bukkit.getServer().createWorld(new WorldCreator("kitpvp"));
    hub = Bukkit.getServer().getWorld("kitpvp");
    hubSpawn = new Location(hub, -1052.5, 197.5, -131.5);
  }
  
  private void addPlayersToFighters() {
    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      player.teleport(Main.hubSpawn);
      Fighter fighter = new Fighter(player);
      fighter.addToFightersHashMap();
    }
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      return false;
    }
    Player player = (Player) sender;
    if (label.equals("spawn")) {
        player.teleport(hubSpawn);
        return true;
    }
    return false; 
  }
}
