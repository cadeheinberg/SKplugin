package me.cade.PluginSK.KitListeners;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R3.PlayerConnection;



public class G6_Zero {

  // drop to go invisible

  public static void doDrop(Player killer) {
    if (killer.getExp() < 1) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    activateSpecial(killer, 300, 500);
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
