package me.cade.PluginSK.KitListeners;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;
import com.mojang.datafixers.util.Pair;
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.BuildKits.F2_Scorch;
import me.cade.PluginSK.BuildKits.F_Stats;
import me.cade.PluginSK.Damaging.DealDamage;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class G2_Scorch {

  // right clicking shoots snowballs like a shotgun and has recoil
  // the snowballs normally just damage
  // if you drop the shovel, it makes snowballs do fire damage

  public static void doRightClick(Player player) {
    if (player.getCooldown(F2_Scorch.getWeapon().getWeaponItem().getType()) > 0) {
      return;
    }
    shootSnowballs(player);
    if (Fighter.fighters.get(player.getUniqueId()).isFighterAbility()) {
      launchPlayer(player, -0.05);
    } else {
      player.setCooldown(F2_Scorch.getWeapon().getWeaponItem().getType(),
        F_Stats.getTicksList(F2_Scorch.getKitID())[0]);
      launchPlayer(player, -0.5);
    }
  }

  public static void doDrop(Player killer) {
    if (killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    activateSpecial(killer, 100, 1000);
  }

  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
    G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    makeLavaHead(craftPlayer.getHandle());
    killer.sendMessage(ChatColor.AQUA + "Special Ability Activated");
    killer.playSound(killer.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 8, 1);
  }
  
  public static void deActivateSpecial(Player killer) {
    CraftPlayer craftPlayer = (CraftPlayer) killer;
    makeNormalHead(craftPlayer.getHandle());
  }

  public static void doSnowballHitEntity(Player killer, LivingEntity victim, Snowball snowball) {
    if (snowball.getFireTicks() > 0) {
      victim.setFireTicks(1200);
    }
    DealDamage.dealAmount(killer, victim,
      F_Stats.getProjectileDamageList(F2_Scorch.getKitID())[Fighter.fighters
        .get(killer.getUniqueId()).getKitIndex()]);
  }

  public static void doSnowballHitGround(Player killer, Location location, Snowball snowball) {
    if (snowball.getFireTicks() > 0) {

    }
  }

  public static void launchPlayer(Player player, Double power) {
    Vector currentDirection = player.getLocation().getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(power, power, power));
    player.setVelocity(currentDirection);
  }

  public static void shootSnowballs(Player player) {
    player.playSound(player.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 8, 1);

    Snowball ball = player.launchProjectile(Snowball.class);
    Vector currentDirection = player.getLocation().getDirection().normalize();
    currentDirection = currentDirection.multiply(new Vector(2.3, 2, 2));
    ball.setVelocity(currentDirection);

    Snowball ball2 = player.launchProjectile(Snowball.class);
    Vector currentDirection2 = player.getLocation().getDirection().normalize();
    currentDirection2 = currentDirection2.multiply(new Vector(2, 2, 2));
    ball2.setVelocity(currentDirection2);

    Snowball ball3 = player.launchProjectile(Snowball.class);
    Vector currentDirection3 = player.getLocation().getDirection().normalize();
    currentDirection3 = currentDirection3.multiply(new Vector(1.7, 2, 2));
    ball3.setVelocity(currentDirection3);

    Snowball ball4 = player.launchProjectile(Snowball.class);
    Vector currentDirection4 = player.getLocation().getDirection().normalize();
    currentDirection4 = currentDirection4.multiply(new Vector(2, 2.5, 2));
    ball4.setVelocity(currentDirection4);

    Snowball ball5 = player.launchProjectile(Snowball.class);
    Vector currentDirection5 = player.getLocation().getDirection().normalize();
    currentDirection5 = currentDirection5.multiply(new Vector(2, 1.5, 2));
    ball5.setVelocity(currentDirection5);

    if (Fighter.fighters.get(player.getUniqueId()).isFighterAbility()) {
      ball.setFireTicks(1000);
      ball2.setFireTicks(1000);
      ball3.setFireTicks(1000);
      ball4.setFireTicks(1000);
      ball5.setFireTicks(1000);
    }

  }
  
  private static void makeLavaHead(Entity entity) {

    // Defining the list of Pairs with EnumItemSlot and (NMS) ItemStack
    List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList =
      new ArrayList<>();

    // Adding an ice block to the head
    equipmentList.add(new Pair<>(EnumItemSlot.HEAD,
      CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.MAGMA_BLOCK))));

    // Creating the packet
    PacketPlayOutEntityEquipment packet =
      new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);

    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
      conn.sendPacket(packet);
    }
  }
  
  private static void makeNormalHead(Entity entity) {

    // Defining the list of Pairs with EnumItemSlot and (NMS) ItemStack
    List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList =
      new ArrayList<>();

    Player p = Bukkit.getServer().getPlayer(entity.getUniqueID());
    // Adding an ice block to the head
    equipmentList.add(new Pair<>(EnumItemSlot.HEAD,
      CraftItemStack.asNMSCopy(p.getEquipment().getHelmet())));
    
    // Creating the packet
    PacketPlayOutEntityEquipment packet =
      new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);

    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
      conn.sendPacket(packet);
    }
  }

}
