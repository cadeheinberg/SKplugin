package me.cade.PluginSK.SpecialItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.cade.PluginSK.Main;
import me.cade.PluginSK.Weapon;

public class CombatTracker extends SpecialItem {

	public static Material mat = Material.GOLD_INGOT;
	private static Weapon weapon = new Weapon(mat, ChatColor.LIGHT_PURPLE + "Spawn Teleporter",
			ChatColor.WHITE + "Right Click to go to /spawn",
			ChatColor.WHITE + "Don't leave game while PvP cooldown is on!");

	public CombatTracker(Player player) {
		super(player);
		player.getInventory().setItem(8, getWeapon().getWeaponItem());
	}

	@Override
	public boolean doRightClick() {
		if (player.getWorld() == Main.hub) {
			if (player.getCooldown(this.getMaterial()) > 0) {
				player.sendMessage(ChatColor.RED + "Can't" + ChatColor.AQUA + "" + ChatColor.BOLD + " /spawn"
						+ ChatColor.RED + ". You have a" + ChatColor.AQUA + "" + ChatColor.BOLD + " PVP Cooldown "
						+ ChatColor.RED + "on");
				return false;
			}
			player.teleport(Main.hubSpawn);
			return true;
		}
		return false;
	}

	@Override
	public void doDrop() {
		this.doRightClick();
	}

	@Override
	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public Material getMaterial() {
		return mat;
	}

}
