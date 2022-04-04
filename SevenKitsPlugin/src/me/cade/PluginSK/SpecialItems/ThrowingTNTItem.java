package me.cade.PluginSK.SpecialItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import me.cade.PluginSK.Main;
import me.cade.PluginSK.Weapon;

public class ThrowingTNTItem extends SpecialItem {

	private static int cooldown = 250;
	private static Material mat = Material.COAL;
	private static Weapon weapon = new Weapon(mat, ChatColor.YELLOW + "Grenade",
			ChatColor.WHITE + "Right Click to throw TNT",
			ChatColor.WHITE + "Coolwdown: " + Math.floor((cooldown / 20) * 100) / 100);

	public ThrowingTNTItem(Player player) {
		super(player);
		player.getInventory().addItem(getWeapon().getWeaponItem());
	}

	@Override
	public boolean doRightClick() {
		if(super.doRightClick()) {
			this.doThrowingTNT();
			return true;
		}
		return false;
	}
	
	@Override
	public void doDrop() {
		this.doRightClick();
	}

	private void doThrowingTNT() {
		Entity tnt = player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.PRIMED_TNT);
		TNTPrimed fuse = (TNTPrimed) tnt;
		tnt.setCustomName(this.player.getName());
		tnt.setCustomNameVisible(false);
		tnt.setMetadata("thrower", new FixedMetadataValue(Main.getInstance(), this.player.getName()));
		fuse.setFuseTicks(15);
		Vector currentDirection4 = player.getLocation().getDirection().normalize();
		currentDirection4 = currentDirection4.multiply(new Vector(1, 1, 1));
		tnt.setVelocity(currentDirection4);
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
