package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.cade.PluginSK.BuildKits.*;
import me.cade.PluginSK.Damaging.*;
import me.cade.PluginSK.KitListeners.G_KitListener;
import me.cade.PluginSK.Money.A_CakeManager;
import me.cade.PluginSK.NPCS.*;
import me.cade.PluginSK.Permissions.BasicPermissions;
import me.cade.PluginSK.Permissions.PickingUp;
import me.cade.PluginSK.PlayerJoin.*;
import me.cade.PluginSK.ScoreBoard.Experience;
import me.cade.PluginSK.SpecialItems.H1_CombatTracker;

public class Main extends JavaPlugin {

	  // fire is burning forever with scorch
	
		//new player starts out with 1 level and 0 exp some reason
		
		//trident does took much throwing damage, should do more melee
		
		//add back the cool wizard spells
		
		//killer.setWalkSpeed((float) 0.2); on respawn or death i guess
		
		//add baseline speed boost to goblin
		
		//apply night vision potion
		
		//able to break kit selectors
		
		//error inside of the console
		
		//add care packages back
		
		//add real snowmen with healing areas around them
		
		//give each island their own special thing
		
		//change biome for darker grass
		
		//quak craft with big map and speed boost
	
	//global game modes that apply to the hub world
	//players wont join a new world to play
	//if enough people are queued for one gamemode it will 
	//change the hub map to add flags and put everyone
	//on different teams
		
	
	//global game modifiers that apply to the hub world
		//fortnite mode where everyone gets blocks to build with
		//and popup forts that go away after fortnite mode is over

  public static World hub;
  public static Location hubSpawn;
  public static World secondWorld;
  public static Location secondWorldSpawn;

  @Override
  public void onEnable() {
    getConfig().options().copyDefaults(true);
    saveConfig();
    MySQL.startConnection();
    setLocations();
    F_KitBuilder.buildAllKits();
    D_SpawnAllNPCS.removeAllNpcs();
    D_SpawnAllNPCS.spawnNPCS();
    registerListeners();
    Borders.startCheckingBorders();
    Experience.makeExpNeeded();
    A_CakeManager.startCakePackage();
    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    H1_CombatTracker.makeTracker();
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
    A_CakeManager.stopCakePackage();
    Borders.stopCheckingBorders();
  }

  public void setLocations() {
    hub = Bukkit.getServer().getWorld("world");
    hubSpawn = new Location(hub, -1052.5, 197.5, -131.5);
    Bukkit.getServer().createWorld(new WorldCreator("secondworld"));
    secondWorld = Bukkit.getServer().getWorld("secondworld");
    secondWorldSpawn = new Location(secondWorld, -1052.5, 197.5, -131.5);
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
    } else if (label.equals("secondworld")) {
        player.teleport(secondWorldSpawn);
        return true;
     }else if (label.equals("givekit")) {
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
    } else if (label.equals("sendplayer")) {
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
