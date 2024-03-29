package me.cade.PluginSK.SpecialItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import me.cade.PluginSK.Weapon;

public class SpecialItem {

	protected Player player;

	public SpecialItem(Player player) {
		this.player = player;
	}

	public boolean doRightClick() {
		return this.checkAndSetCooldown();
	}
	
	public boolean checkAndSetCooldown() {
		if (player.getCooldown(this.getMaterial()) > 0) {
			player.sendMessage(ChatColor.RED + "Item needs to recharge");
			return false;
		}
		player.setCooldown(getMaterial(), this.getCooldown());
		return true;
	}

	public void doDrop() {
		// see override methods
	}

	public void resetCooldown() {
		player.setCooldown(getMaterial(), 0);
	}

	public Player getPlayer() {
		return this.player;
	}

	public Weapon getWeapon() {
		// see override methods
		return null;
	}

	public Material getMaterial() {
		// see override methods
		return null;
	}

	public int getCooldown() {
		// see override methods
		return -1;
	}

	public void doDoubleJump() {
		// TODO Auto-generated method stub
		
	}

}
