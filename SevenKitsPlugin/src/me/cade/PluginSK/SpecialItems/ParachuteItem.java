package me.cade.PluginSK.SpecialItems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import me.cade.PluginSK.Main;
import me.cade.PluginSK.Weapon;

public class ParachuteItem extends SpecialItem {

	private static int cooldown = 250;
	public static Material mat = Material.PHANTOM_MEMBRANE;
	private static Weapon weapon = new Weapon(mat, ChatColor.YELLOW + "Parachute", ChatColor.WHITE + "Right Click to open",
			ChatColor.WHITE + "Coolwdown: " + Math.floor((cooldown / 20) * 100) / 100);

	private int itemTask;
	private Chicken chicken;

	public ParachuteItem(Player player) {
		super(player);
		player.getInventory().addItem(getWeapon().getWeaponItem());
		this.setItemTask(-1);
		this.setChicken(null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean doRightClick() {
		if (player.isOnGround()) {
			return false;
		}
		if(super.doRightClick()) {
			this.doParachute(player);
			return true;
		}
		return false;
	}
	
	@Override
	public void doDrop() {
		this.doRightClick();
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
				if (loc.getPitch() < 40) {
					loc.setPitch(40);
				} else if (loc.getPitch() >= 75) {
					loc.setPitch(75);
				}
				Vector vector = loc.getDirection();
				chicken.setVelocity(vector.multiply(0.6));
			}
		}.runTaskTimer(Main.getInstance(), 0L, 1L).getTaskId());
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
	
	@Override
	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public Material getMaterial() {
		return mat;
	}
	
	@Override
	public int getCooldown() {
		return cooldown;
	}

}
