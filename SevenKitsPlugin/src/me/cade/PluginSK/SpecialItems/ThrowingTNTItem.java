package me.cade.PluginSK.SpecialItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import me.cade.PluginSK.Main;
import me.cade.PluginSK.Weapon;

public class ThrowingTNTItem {

	private static Plugin plugin = Main.getPlugin(Main.class);

	private static int cooldown = 250;
	private static Material mat = Material.COAL;
	private static Weapon weapon = new Weapon(mat, ChatColor.YELLOW + "Grenade",
			ChatColor.WHITE + "Right Click to throw TNT",
			ChatColor.WHITE + "Coolwdown: " + Math.floor((cooldown / 20) * 100) / 100);

	private Player player;

	public ThrowingTNTItem(Player player) {
		this.player = player;
		player.getInventory().addItem(getWeapon().getWeaponItem());
	}

	public static Weapon getWeapon() {
		return weapon;
	}

	public static Material getMaterial() {
		return mat;
	}

	public void doRightClick() {
		if (player.getCooldown(getMaterial()) > 0) {
			player.sendMessage(ChatColor.RED + "Item needs to recharge");
			return;
		}
		this.doThrowingTNT();
		player.setCooldown(getMaterial(), cooldown);
	}

	private void doThrowingTNT() {
		Entity tnt = player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
		TNTPrimed fuse = (TNTPrimed) tnt;
		tnt.setCustomName(this.player.getName());
		tnt.setCustomNameVisible(false);
		tnt.setMetadata("thrower", new FixedMetadataValue(plugin, this.player.getName()));
		fuse.setFuseTicks(15);
		Vector currentDirection4 = player.getLocation().getDirection().normalize();
		currentDirection4 = currentDirection4.multiply(new Vector(1, 1, 1));
		tnt.setVelocity(currentDirection4);
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(Plugin plugin) {
		ThrowingTNTItem.plugin = plugin;
	}
	
	public void resetCooldown() {
		player.setCooldown(getMaterial(), 0);
	}

}
