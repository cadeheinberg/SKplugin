package me.cade.PluginSK.KitListeners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import com.mojang.datafixers.util.Pair;
import me.cade.PluginSK.SafeZone;
import me.cade.PluginSK.BuildKits.F5_Wizard;
import me.cade.PluginSK.BuildKits.F_Stats;
import me.cade.PluginSK.Damaging.DealDamage;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R3.PlayerConnection;



public class G5_Wizard {

  // drop to go use cloak

  public static void doDrop(Player killer) {
    if (killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    activateSpecial(killer, 300, 500);
  }
  
  public static void doRightClick(Player player) {
    if (player.getCooldown(F5_Wizard.getWeapon().getWeaponItem().getType()) > 0) {
      return;
    }
    doManaSpell(player);
    player.setCooldown(F5_Wizard.getWeapon().getWeaponItem().getType(),
      F_Stats.getTicksList(F5_Wizard.getKitID())[0]);
  }
  
  private static void doManaSpell(Player killer) {
    Location origin = killer.getEyeLocation();
    Vector direction = killer.getLocation().getDirection();
    double dX = direction.getX();
    double dY = direction.getY();
    double dZ = direction.getZ();
    // range
    ArrayList<Integer> hitList = new ArrayList<Integer>();
    for (int j = 1; j < 20; j++) {
      origin = origin.add(dX * j, dY * j, dZ * j);
      killer.getWorld().spawnParticle(Particle.FALLING_LAVA, origin.getX(), origin.getY(),
        origin.getZ(), 5);
      Collection<org.bukkit.entity.Entity> entityList = killer.getWorld().getNearbyEntities(origin, 0.75, 0.75, 0.75);
      for (org.bukkit.entity.Entity entity : entityList) {
        if (entity instanceof LivingEntity) {
          if(SafeZone.safeZone(entity.getLocation())) {
            return;
          }
          if(hitList.contains(((LivingEntity) entity).getEntityId())) {
            continue;
          }
          hitList.add(((LivingEntity) entity).getEntityId());
          DealDamage.dealAmount(killer, (LivingEntity) entity,
            (F_Stats.getProjectileDamageList(F5_Wizard.getKitID())[0]));
        }
      }
      origin = origin.subtract(dX * j, dY * j, dZ * j);
    }
  }

  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    killer.setInvisible(true);
    makeInvisible(craftPlayer.getHandle());
    G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_GHAST_SCREAM, 8, 1);
  }
  
  public static void deActivateSpecial(Player killer) {
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    killer.setInvisible(false);
    makeVisible(craftPlayer.getHandle());
  }

  private static void makeInvisible(Entity entity) {

    // Defining the list of Pairs with EnumItemSlot and (NMS) ItemStack
    List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList =
      new ArrayList<>();

    // Adding an ice block to the head
    equipmentList.add(new Pair<>(EnumItemSlot.HEAD,
      CraftItemStack.asNMSCopy(null)));
    equipmentList.add(new Pair<>(EnumItemSlot.CHEST,
      CraftItemStack.asNMSCopy(null)));
    equipmentList.add(new Pair<>(EnumItemSlot.LEGS,
      CraftItemStack.asNMSCopy(null)));
    equipmentList.add(new Pair<>(EnumItemSlot.FEET,
      CraftItemStack.asNMSCopy(null)));

    // Creating the packet
    PacketPlayOutEntityEquipment packet =
      new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);

    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
      conn.sendPacket(packet);
    }
  }
  
  private static void makeVisible(Entity entity) {

    // Defining the list of Pairs with EnumItemSlot and (NMS) ItemStack
    List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList =
      new ArrayList<>();

    Player p = Bukkit.getServer().getPlayer(entity.getUniqueID());
    // Adding an ice block to the head
    equipmentList.add(new Pair<>(EnumItemSlot.HEAD,
      CraftItemStack.asNMSCopy(p.getEquipment().getHelmet())));
    equipmentList.add(new Pair<>(EnumItemSlot.CHEST,
      CraftItemStack.asNMSCopy(p.getEquipment().getChestplate())));
    equipmentList.add(new Pair<>(EnumItemSlot.LEGS,
      CraftItemStack.asNMSCopy(p.getEquipment().getLeggings())));
    equipmentList.add(new Pair<>(EnumItemSlot.FEET,
      CraftItemStack.asNMSCopy(p.getEquipment().getBoots())));

    // Creating the packet
    PacketPlayOutEntityEquipment packet =
      new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);

    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
      conn.sendPacket(packet);
    }
  }


}
