package me.cade.PluginSK.SpecialItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import me.cade.PluginSK.Main;
import me.cade.PluginSK.Weapon;

public class IceCageItem {

	private static int cooldown = 250;
	private static Material mat = Material.PRISMARINE_CRYSTALS;
	private static Weapon weapon = new Weapon(mat, ChatColor.AQUA + "Porta Fort",
			ChatColor.WHITE + "Right Click to spawn fort",
			ChatColor.WHITE + "Coolwdown: " +  Math.floor( (cooldown/20) * 100) / 100);

	private Player player;
	
	private static Plugin plugin = Main.getPlugin(Main.class);
	
	public IceCageItem(Player player) {
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
		this.doIceCageSpell();
		player.setCooldown(getMaterial(), cooldown);
	}

	private void doIceCageSpell() {
		Location oneBlockAway = player.getLocation().add(player.getLocation().getDirection());
		Item iceCube = player.getWorld().dropItem(oneBlockAway, new ItemStack(Material.PACKED_ICE, 1));
		Vector currentDirection = player.getLocation().getDirection().normalize();
		currentDirection = currentDirection.multiply(new Vector(2, 2, 2));
		iceCube.setVelocity(currentDirection);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				iceCube.remove();
				Location iceLocation = iceCube.getLocation();
				Block iceBlock = iceLocation.getBlock();
				Block[] b = new Block[57];
				int j = 0;
				for (int i = -1; i < 3; i++) {
					b[j++] = iceBlock.getRelative(2, i, -1);
					b[j++] = iceBlock.getRelative(2, i, 0);
					b[j++] = iceBlock.getRelative(2, i, 1);

					b[j++] = iceBlock.getRelative(-2, i, -1);
					b[j++] = iceBlock.getRelative(-2, i, 0);
					b[j++] = iceBlock.getRelative(-2, i, 1);

					b[j++] = iceBlock.getRelative(-1, i, 2);
					b[j++] = iceBlock.getRelative(0, i, 2);
					b[j++] = iceBlock.getRelative(1, i, 2);

					b[j++] = iceBlock.getRelative(-1, i, -2);
					b[j++] = iceBlock.getRelative(0, i, -2);
					b[j++] = iceBlock.getRelative(1, i, -2);
				}
				// top
				b[j++] = iceBlock.getRelative(-1, 3, -1);
				b[j++] = iceBlock.getRelative(-1, 3, 0);
				b[j++] = iceBlock.getRelative(-1, 3, 1);
				b[j++] = iceBlock.getRelative(1, 3, -1);
				b[j++] = iceBlock.getRelative(1, 3, 0);
				b[j++] = iceBlock.getRelative(1, 3, 1);
				b[j++] = iceBlock.getRelative(0, 3, -1);
				b[j++] = iceBlock.getRelative(0, 3, 0);
				b[j++] = iceBlock.getRelative(0, 3, 1);
				for (int i = 0; i < 57; i++) {
					Block block = b[i];
					if (block.getType() != Material.AIR) {
						continue;
					}
					if (i > 11) {
						block.setType(Material.PACKED_ICE);
					}
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						@Override
						public void run() {
							block.setType(Material.AIR);
						}
					}, 120);
				}
				player.getWorld().playSound(iceBlock.getLocation(), Sound.BLOCK_GLASS_BREAK, 2, 1);
			}
		}, 10);
	}
	
	public void resetCooldown() {
		player.setCooldown(getMaterial(), 0);
	}

}
