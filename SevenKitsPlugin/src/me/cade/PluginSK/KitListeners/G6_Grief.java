package me.cade.PluginSK.KitListeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.mojang.datafixers.util.Pair;

import me.cade.PluginSK.AbilityEnchantment;
import me.cade.PluginSK.Fighter;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class G6_Grief {
  
  // Every damage you do gives you health. Like a vampire
  
  public static void doDrop(Player killer) {
	 if (killer.getCooldown(Material.BIRCH_FENCE) > 0 || killer.getCooldown(Material.JUNGLE_FENCE) > 0) {
      killer.sendMessage(ChatColor.RED + "Wait for Special Ability to recharge");
      killer.playSound(killer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 8, 1);
      return;
    }
    activateSpecial(killer, 300, 50);
  }
  
  private static void activateSpecial(Player killer, int durationTicks, int rechargeTicks) {
	  G8_Cooldown.startAbilityDuration(killer, durationTicks, rechargeTicks);
	CraftPlayer craftPlayer = (CraftPlayer) killer;
    killer.setInvisible(true);
    makeInvisible(craftPlayer.getHandle());
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

  public static void doStealHealth(Player killer, Fighter fKiller, Player victim) {
    if(fKiller.isAbilityActive()) {
      double combined = killer.getHealth() + 1.5;
      if(combined > 20) {
        killer.setHealth(20);
      }else {
        killer.setHealth(combined);
      }
      killer.playSound(killer.getLocation(), Sound.ENTITY_ENDERMAN_HURT, 16, 1);
      Firework firework = killer.getWorld().spawn(killer.getLocation(), Firework.class);
      FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
      data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.YELLOW)
        .with(Type.BALL_LARGE).withFlicker().build());
      data.setPower(1);
      firework.setFireworkMeta(data);
    }
  }

}
