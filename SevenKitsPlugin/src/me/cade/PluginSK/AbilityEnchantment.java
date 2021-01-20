package me.cade.PluginSK;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class AbilityEnchantment {

  public static void makeEnchanted(Entity entity) {

    // Defining the list of Pairs with EnumItemSlot and (NMS) ItemStack
    List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList =
      new ArrayList<>();

    Player p = Bukkit.getServer().getPlayer(entity.getUniqueID());
    
    ItemStack helmet = p.getEquipment().getHelmet();
    helmet.addEnchantment(Enchantment.DURABILITY, 1);
    
    ItemStack chest = p.getEquipment().getChestplate();
    chest.addEnchantment(Enchantment.DURABILITY, 1);
    
    ItemStack legs = p.getEquipment().getLeggings();
    legs.addEnchantment(Enchantment.DURABILITY, 1);
    
    ItemStack boots = p.getEquipment().getBoots();
    boots.addEnchantment(Enchantment.DURABILITY, 1);

    // Adding an ice block to the head
    equipmentList.add(new Pair<>(EnumItemSlot.HEAD,
      CraftItemStack.asNMSCopy(helmet)));
    equipmentList.add(new Pair<>(EnumItemSlot.CHEST,
      CraftItemStack.asNMSCopy(chest)));
    equipmentList.add(new Pair<>(EnumItemSlot.LEGS,
      CraftItemStack.asNMSCopy(legs)));
    equipmentList.add(new Pair<>(EnumItemSlot.FEET,
      CraftItemStack.asNMSCopy(boots)));

    // Creating the packet
    PacketPlayOutEntityEquipment packet =
      new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);

    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
      conn.sendPacket(packet);
    }
  }
  
  public static void removeEnchanted(Entity entity) {
    // Defining the list of Pairs with EnumItemSlot and (NMS) ItemStack
    List<Pair<EnumItemSlot, net.minecraft.server.v1_16_R3.ItemStack>> equipmentList =
      new ArrayList<>();

    Player p = Bukkit.getServer().getPlayer(entity.getUniqueID());
    
    ItemStack helmet = p.getEquipment().getHelmet();
    helmet.removeEnchantment(Enchantment.DURABILITY);
    
    ItemStack chest = p.getEquipment().getChestplate();
    chest.removeEnchantment(Enchantment.DURABILITY);
    
    ItemStack legs = p.getEquipment().getLeggings();
    legs.removeEnchantment(Enchantment.DURABILITY);
    
    ItemStack boots = p.getEquipment().getBoots();
    boots.removeEnchantment(Enchantment.DURABILITY);

    // Adding an ice block to the head
    equipmentList.add(new Pair<>(EnumItemSlot.HEAD,
      CraftItemStack.asNMSCopy(helmet)));
    equipmentList.add(new Pair<>(EnumItemSlot.CHEST,
      CraftItemStack.asNMSCopy(chest)));
    equipmentList.add(new Pair<>(EnumItemSlot.LEGS,
      CraftItemStack.asNMSCopy(legs)));
    equipmentList.add(new Pair<>(EnumItemSlot.FEET,
      CraftItemStack.asNMSCopy(boots)));

    // Creating the packet
    PacketPlayOutEntityEquipment packet =
      new PacketPlayOutEntityEquipment(entity.getId(), equipmentList);

    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
      conn.sendPacket(packet);
    }
  }
  
}
