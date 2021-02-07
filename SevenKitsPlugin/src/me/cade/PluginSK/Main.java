package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.cade.PluginSK.BuildKits.*;
import me.cade.PluginSK.Damaging.*;
import me.cade.PluginSK.KitListeners.G_KitListener;
import me.cade.PluginSK.Money.CakeSpawner;
import me.cade.PluginSK.NPCS.*;
import me.cade.PluginSK.Permissions.BasicPermissions;
import me.cade.PluginSK.Permissions.PickingUp;
import me.cade.PluginSK.PlayerJoin.*;
import me.cade.PluginSK.ScoreBoard.Experience;

public class Main extends JavaPlugin {

  // eating cake
  // charges
  // the fighter ability

  public static World hub;
  public static Location hubSpawn;

  @Override
  public void onEnable() {
    getConfig().options().copyDefaults(true);
    saveConfig();
    MySQL.startConnection();
    setLocations();
    F_KitBuilder.buildAllKits();
    D_SpawnKitSelectors.removeAllNpcs();
    D_SpawnAllNPCS.spawnNPCS();
    registerListeners();
    Borders.startCheckingBorders();
    Experience.makeExpNeeded();
    CakeSpawner.startCakes();
    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    // do last
    addPlayersToFighters();
  }

  private void registerListeners() {
    PluginManager pm = Bukkit.getServer().getPluginManager();
    pm.registerEvents(new D0_NpcListener(), this);
    pm.registerEvents(new PlayerJoinListener(), this);
    pm.registerEvents(new FallDamageListener(), this);
    pm.registerEvents(new G_KitListener(), this);
    pm.registerEvents(new BasicPermissions(), this);
    pm.registerEvents(new EntityDamage(), this);
    pm.registerEvents(new PlayerChat(), this);
    pm.registerEvents(new PickingUp(), this);
  }

  @Override
  public void onDisable() {
    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      Fighter.get(player).fighterLeftServer();
    }
    MySQL.closeConnection();
    Borders.stopCheckingBorders();
    CakeSpawner.stopCakes();
  }

  public void setLocations() {
    hub = Bukkit.getServer().getWorld("world");
    hubSpawn = new Location(hub, -1052.5, 197.5, -131.5);
  }

  private void addPlayersToFighters() {
    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      // player.teleport(Main.hubSpawn);
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
    } else if (label.equals("givekit")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      int kitID = Integer.parseInt(args[1].toString());
      int kitIndex = Integer.parseInt(args[2].toString());
      Fighter fighter = Fighter.get(giveCake);
      fighter.giveKit(kitID, kitIndex);
    } else if (label.equals("givekittoall")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      int kitID = Integer.parseInt(args[0].toString());
      int kitIndex = Integer.parseInt(args[1].toString());
      for (Player giveTo : Bukkit.getOnlinePlayers()) {
        Fighter.get(giveTo).giveKit(kitID, kitIndex);
      }
    } else if (label.equals("setunlocked")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      int unlockedBitString = Integer.parseInt(args[1].toString());
      Fighter fighter = Fighter.get(giveCake);
      fighter.setUnlocked(unlockedBitString);
      player.sendMessage("done");
    }else if (label.equals("sendplayer")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      BungeeSend.sendPlayer(giveCake);
    }
    return false;
  }
}
