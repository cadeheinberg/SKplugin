package me.cade.PluginSK.SpecialItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import me.cade.PluginSK.Main;
import me.cade.PluginSK.Weapon;

public class ParachuteItem {

	private static int cooldown = 250;
	private static Material mat = Material.PHANTOM_MEMBRANE;
	private static Weapon weapon = new Weapon(mat, ChatColor.YELLOW + "Parachute", ChatColor.WHITE + "Right Click to open",
			ChatColor.WHITE + "Coolwdown: " + Math.floor((cooldown / 20) * 100) / 100);

	private Player player;
	private int itemTask;
	private Chicken chicken;

	private static Plugin plugin = Main.getPlugin(Main.class);

	public ParachuteItem(Player player) {
		this.player = player;
		player.getInventory().addItem(getWeapon().getWeaponItem());
		this.setItemTask(-1);
		this.setChicken(null);
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
		this.doParachute(player);
		player.setCooldown(getMaterial(), cooldown);
	}

	@SuppressWarnings("deprecation")
	private void doParachute(Player player) {
		if (player.isOnGround()) {
			return;
		}
		if (this.getItemTask() != -1) {
			return;
		}
		player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 8, 1);
		Chicken chicken = (Chicken) player.getWorld().spawnEntity(player.getLocation(), EntityType.CHICKEN);
		chicken.addPassenger(player);
		this.setChicken(chicken);
		doGliding(chicken, player);
		return;
	}

	private void doGliding(Chicken chicken, Player player) {
		this.setItemTask(new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Parachute Task Running");
				if (chicken.isOnGround()) {
					chicken.eject();
					getOff();
					return;
				}
				Location loc = player.getEyeLocation();
				if (loc.getPitch() < 50) {
					loc.setPitch(50);
				} else if (loc.getPitch() >= 75) {
					loc.setPitch(75);
				}
				Vector vector = loc.getDirection();
				chicken.setVelocity(vector.multiply(0.6));
			}
		}.runTaskTimer(plugin, 0L, 1L).getTaskId());
	}

	public void getOff() {
		if(this.getItemTask() != -1) {
			Bukkit.getScheduler().cancelTask(this.getItemTask());
			this.setItemTask(-1);
		}
		if(chicken != null) {
			chicken.remove();
			this.setChicken(null);
		}
	}

	public int getItemTask() {
		return itemTask;
	}

	public void setItemTask(int itemTask) {
		this.itemTask = itemTask;
	}

	public Chicken getChicken() {
		return chicken;
	}

	public void setChicken(Chicken chicken) {
		this.chicken = chicken;
	}
	
	public void resetCooldown() {
		player.setCooldown(getMaterial(), 0);
	}

}
