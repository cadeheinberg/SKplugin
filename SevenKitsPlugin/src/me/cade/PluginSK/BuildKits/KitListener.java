package me.cade.PluginSK.BuildKits;

import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftTrident;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import me.cade.PluginSK.*;
import me.cade.PluginSK.SpecialItems.CombatTracker;
import me.cade.PluginSK.SpecialItems.IceCageItem;
import me.cade.PluginSK.SpecialItems.ParachuteItem;
import me.cade.PluginSK.SpecialItems.ThrowingTNTItem;

public class KitListener implements Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if (SafeZone.safeZone(e.getPlayer().getLocation())) {
			return;
		}
		if (e.getHand() == EquipmentSlot.OFF_HAND) {
			return; // off hand packet, ignore.
		}
		if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		Player player = e.getPlayer();
		if (Fighter.get(player).getFKit().doRightClickIfRightMaterial(e.getItem().getType())) {
			return;
		}
		if (e.getItem().getType() == ParachuteItem.getMaterial()) {
			Fighter.get(player).getParachuteItem().doRightClick();
			return;
		} else if (e.getItem().getType() == ThrowingTNTItem.getMaterial()) {
			Fighter.get(player).getThrowingTNTItem().doRightClick();
			return;
		} else if (e.getItem().getType() == IceCageItem.getMaterial()) {
			Fighter.get(player).getIceCageItem().doRightClick();
			return;
		} else if (e.getItem().getType() == CombatTracker.getTrackerMaterial()) {
			Fighter.get(player).getCombatTracker().doRightClick();
			return;
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
			return;
		}
		e.setCancelled(true);
		if (SafeZone.safeZone(e.getPlayer().getLocation())) {
			return;
		}
		Fighter.get(e.getPlayer()).getFKit().doDrop();

	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (!(e.getEntity().getShooter() instanceof Player)) {
			return;
		}
		Player shooter = (Player) e.getEntity().getShooter();
		// Hit a block
		if (e.getHitBlock() != null) {
			if (SafeZone.safeZone(e.getHitBlock().getLocation())) {
				return;
			}
			if (e.getEntityType() == EntityType.SNOWBALL) {
				((F2) Fighter.get(shooter).getFKit()).doSnowballHitGround(e.getHitBlock().getLocation(),
						(Snowball) e.getEntity());
			}
			if (e.getEntityType() == EntityType.ARROW) {
				// check if shooter is heavy or goblin
				e.getEntity().remove();
			}
			if (e.getEntityType() == EntityType.TRIDENT) {
				((F4) Fighter.get(shooter).getFKit()).doTridentHitGround(e.getHitBlock().getLocation(),
						(CraftTrident) e.getEntity());
			}
		}

		// Hit an entity
		if (e.getHitEntity() != null) {
			if (SafeZone.safeZone(e.getHitEntity().getLocation())) {
				return;
			}
			if (!(e.getHitEntity() instanceof LivingEntity)) {
				return;
			}
			if (e.getEntityType() == EntityType.SNOWBALL) {
				((F2) Fighter.get(shooter).getFKit()).doSnowballHitEntity((LivingEntity) e.getHitEntity(),
						(Snowball) e.getEntity());
			}
			if (e.getEntityType() == EntityType.ARROW) {
				if (Fighter.fighters.get(shooter.getUniqueId()).getKitID() == 5) {
					// death machine

				} else {
					// goblin probably
					((F3) Fighter.get(shooter).getFKit()).doArrorwHitEntity(shooter, (Arrow) e.getEntity());
				}
			}
			if (e.getEntityType() == EntityType.TRIDENT) {
				((F4) Fighter.get(shooter).getFKit()).doTridentHitEntity((LivingEntity) e.getHitEntity(),
						(CraftTrident) e.getEntity());
			}
		}
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		if (SafeZone.safeZone(e.getLocation())) {
			e.setCancelled(true);
			return;
		}
		if (e.getEntityType() == EntityType.TRIDENT) {
			if (e.getEntity().getShooter() instanceof Player) {
				((F4) Fighter.get((Player) e.getEntity().getShooter()).getFKit()).doThrowTrident((CraftTrident) e.getEntity());
			}
		}
	}

	@EventHandler
	public void onBowShot(EntityShootBowEvent e) {
		if (SafeZone.safeZone(e.getEntity().getLocation())) {
			return;
		}
		if (e.getEntity() instanceof Player) {
			if (e.getProjectile().getType() == EntityType.ARROW) {
				if (!((F3) Fighter.get((Player) e.getEntity()).getFKit()).doArrowShoot((Arrow) e.getProjectile(), e.getForce())) {
					e.setCancelled(true);
				}
			}
		}
	}

}
