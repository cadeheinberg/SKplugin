package me.cade.PluginSK.NPCS;

import java.text.NumberFormat;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.Main;
import me.cade.PluginSK.BuildKits.*;

public class D_SpawnKitSelectors {

	private static D1_ArmorStand[] kits;
	
	private static FighterKit[] fKits = Fighter.getFKits();

	private static Location[] locations = {
			new Location(Main.hub, -1043.5, 195, -106.5),
			new Location(Main.hub, -1045.5, 195, -103.5),
			new Location(Main.hub, -1048.5, 195, -101.5),
			new Location(Main.hub, -1052.5, 195, -100.5),
			new Location(Main.hub, -1056.5, 195, -101.5),
			new Location(Main.hub, -1059.5, 195, -103.5),
			new Location(Main.hub, -1061.5, 195, -106.5)
	};

	@SuppressWarnings("deprecation")
	public static void spawnKitSelectors() {
		ChatColor y = ChatColor.YELLOW;
		ChatColor b = ChatColor.BOLD;
		String p = y + "" + b + "";
		kits = new D1_ArmorStand[Fighter.getNumberOfKits()];
		for (int i = 0; i < Fighter.getNumberOfKits(); i++) {
			kits[i] = new D1_ArmorStand(p + fKits[i].getKitName(), locations[i], 180, false, false);
			kits[i].equipColoredArmor(fKits[i].getArmorColor());
			kits[i].getStand().setItemInHand(fKits[i].getWeapons()[0].getWeaponItem());
		}
		spawnKitSelectorStats();
	}

	public static void spawnKitSelectorStats() {
		ChatColor y = ChatColor.AQUA;
		for (int i = 0; i < Fighter.getNumberOfKits(); i++) {
			locations[i].setY(locations[i].getY() + .50);
			new D1_ArmorStand(y + "  Q: " + fKits[i].getKitDrop() + ""
			+ "  ", locations[i], 180, false, false);
		}

		for (int i = 0; i < 7; i++) {
			locations[i].setY(locations[i].getY() - .25);
			new D1_ArmorStand(y + "  RC: " + fKits[i].getKitRightClick() + "  ", locations[i], 180, false, false);
		}
		spawnKitSelectorPrices();
	}

	public static void spawnKitSelectorPrices() {
		NumberFormat myFormat = NumberFormat.getInstance(); 
		myFormat.setGroupingUsed(true);
		String[] kitPrice = new String[Fighter.getNumberOfKits()];
		ChatColor y = ChatColor.GREEN;
		for (int i = 0; i < Fighter.getNumberOfKits(); i++) {
//			if(F_Stats.getPrice(i) == 0) {
//				kitPrice[i] = y + "  Free  ";
//			}else {
//				kitPrice[i] = y + "  Price: $" + myFormat.format(F_Stats.getPrice(i)) + "  ";
//			}
			kitPrice[i] = y + "  Unlocked  ";
		}

		for (int i = 0; i < Fighter.getNumberOfKits(); i++) {
			locations[i].setY(locations[i].getY() + .50);
			new D1_ArmorStand(kitPrice[i], locations[i], 180, false, false);
		}

	}
	
	public static Location getLocationOfSelector(int kitID) {
		return locations[kitID];
	}

}
