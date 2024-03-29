package me.cade.PluginSK;

import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import me.cade.PluginSK.BuildKits.*;
import me.cade.PluginSK.Damaging.*;
import me.cade.PluginSK.Money.A_CakeManager;
import me.cade.PluginSK.NPCS.*;
import me.cade.PluginSK.Permissions.BasicPermissions;
import me.cade.PluginSK.Permissions.PickingUp;
import me.cade.PluginSK.PlayerJoin.*;
import me.cade.PluginSK.ScoreBoard.Experience;
import me.cade.PluginSK.SpecialItems.SpecialItemsListener;

public class Main extends JavaPlugin {

	public static World hub;
	public static Location hubSpawn;
	public static World secondWorld;
	public static Location secondWorldSpawn;
	public static MySQL mysql;

	private static Plugin plugin = null;
	private static ProtocolManager protocolManager;
	private static PlayerParticlesAPI ppAPI;
	
	private static NumberFormat myFormat = NumberFormat.getInstance(); 

	@Override
	public void onEnable() {
		plugin = Main.getPlugin(Main.class);
		protocolManager = ProtocolLibrary.getProtocolManager();
        if (Bukkit.getPluginManager().isPluginEnabled("PlayerParticles")) {
            ppAPI = PlayerParticlesAPI.getInstance();
        }
		getConfig().options().copyDefaults(true);
		saveConfig();
		startMySQL();
		myFormat.setGroupingUsed(true);
		setLocations();
		D_SpawnAllNPCS.removeAllNpcs();
		D_SpawnAllNPCS.spawnNPCS();
		registerListeners();
		Borders.startCheckingBorders();
		Experience.makeExpNeeded();
		A_CakeManager.startCakePackage();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		// do last
		addPlayersToFighters();
	}

	private void startMySQL() {
		mysql = new MySQL();
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new D0_NpcListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new FallDamageListener(), this);
		pm.registerEvents(new KitListener(), this);
		pm.registerEvents(new BasicPermissions(), this);
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new PlayerChat(), this);
		pm.registerEvents(new PickingUp(), this);
		pm.registerEvents(new SpecialItemsListener(), this);
		pm.registerEvents(new DoubleJumpListener(), this);
	}

	@Override
	public void onDisable() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			Fighter.get(player).fighterLeftServer();
		}
		mysql.closeConnection();
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
		} else if (label.equals("hidescoreboard")) {
			if (!(player.isOp())) {
				player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
						+ " operator " + ChatColor.RED + "on this server");
				return false;
			}
			Fighter.get(player).getScoreBoardObjext().hideScoreBoard();
		} else if (label.equals("unhidescoreboard")) {
			if (!(player.isOp())) {
				player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
						+ " operator " + ChatColor.RED + "on this server");
				return false;
			}
			Fighter.get(player).getScoreBoardObjext().unhideScoreBoard();
		} else if (label.equals("fly")) {
			if (!(player.isOp())) {
				player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
						+ " operator " + ChatColor.RED + "on this server");
				return false;
			}
		      double input = Double.parseDouble(args[0].toString());
		      player.setFlySpeed((float) input);
		}
		return false;
	}

	public static Plugin getInstance() {
		return plugin;
	}

	public static ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	public static PlayerParticlesAPI getPpAPI() {
		return ppAPI;
	}
	
	public static NumberFormat getMyNumberFormat() {
		return myFormat;
	}

}
