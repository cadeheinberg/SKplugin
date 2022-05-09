package me.cade.PluginSK.PlayerJoin;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.SpecialItems.JetPackItem;

public class DoubleJumpListener implements Listener {

	@EventHandler
	public void onDoubleJump(PlayerToggleFlightEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
			return;
		}
		e.setCancelled(true);
		e.getPlayer().setFlying(false);
		JetPackItem jPack = Fighter.getFighterFKit(e.getPlayer()).getJetPackItem();
		if(jPack != null) {
			jPack.doDoubleJump();
		}
	}


}
