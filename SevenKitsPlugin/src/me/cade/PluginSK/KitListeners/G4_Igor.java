package me.cade.PluginSK.KitListeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftTrident;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import me.cade.PluginSK.AbilityEnchantment;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.BuildKits.F4_Igor;
import me.cade.PluginSK.BuildKits.F_Stats;
import me.cade.PluginSK.Damaging.CreateExplosion;
import me.cade.PluginSK.Damaging.DealDamage;

public class G4_Igor {

  // dropping trident equips them with an explosive tip
  // do an explosion where the trident lands
  // dont remove trident from inventory
  // put a cooldown on throwing the trident again

  public static void doDrop(Player killer) {
    if(killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    G8_Cooldown.startAbilityDuration(killer, 200, 300);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.makeEnchanted(craftPlayer.getHandle());
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 8, 1);
  }
  
  public static void deactivateSpecialAbility(Player killer) {
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    AbilityEnchantment.removeEnchanted(craftPlayer.getHandle());
  }
  
  public static boolean doThrowTrident(Player player, CraftTrident trident) {
    if (player.getCooldown(F4_Igor.getWeapon().getWeaponItem().getType()) > 0) {
      return false;
    }
    player.setCooldown(F4_Igor.getWeapon().getWeaponItem().getType(),
      F_Stats.getTicksList(F4_Igor.getKitID())[0]);
    if (Fighter.fighters.get((player).getUniqueId()).isFighterAbility()) {
      trident.setFireTicks(1000);
    }
    player.getInventory().remove(Material.TRIDENT);
    player.getInventory().addItem(F4_Igor.getWeapon().getWeaponItem());
    return true;
  }
  
  public static void doTridentHitEntity(Player killer, LivingEntity victim, CraftTrident trident) {
    if(trident.getFireTicks() > 0) {
      Location local = victim.getLocation();
      local.setY(local.getY() - 0.5);
      CreateExplosion.doAnExplosion(killer, local, 0.3, 6.5);
      trident.remove();
    } else {
      DealDamage.dealAmount(killer, victim, F_Stats.getDamageList(F4_Igor.getKitID())[0]);
    }
  }
  
  public static void doTridentHitGround(Player killer, Location location, CraftTrident trident) {
    if(trident.getFireTicks() > 0) {
      CreateExplosion.doAnExplosion(killer, location, 0.3, 6.0);
    }
    trident.remove();
  }

}
