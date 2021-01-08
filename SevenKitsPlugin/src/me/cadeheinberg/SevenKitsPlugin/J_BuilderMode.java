package me.cadeheinberg.SevenKitsPlugin;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class J_BuilderMode implements Listener {

  private static Weapon pic;
  private static Weapon axe;
  private static Weapon shovel;
  private static ArrayList<String> inMode;


  public static void makeBuilderItems() {
    inMode = new ArrayList<String>();
    pic = new Weapon(Material.STONE_PICKAXE, "Builder Pickaxe", "No damage");
    axe = new Weapon(Material.STONE_AXE, "Builder Axe", "No damage");
    shovel = new Weapon(Material.WOODEN_SHOVEL, "Builder Shovel", "No damage");

    pic.applyWeaponUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
    axe.applyWeaponUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
    shovel.applyWeaponUnsafeEnchantment(Enchantment.DIG_SPEED, 2);

    pic.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
      new AttributeModifier("GENERIC_ATTACK_DAMAGE", -1, AttributeModifier.Operation.ADD_NUMBER));
    axe.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
      new AttributeModifier("GENERIC_ATTACK_DAMAGE", -1, AttributeModifier.Operation.ADD_NUMBER));
    shovel.addNewAttribute(Attribute.GENERIC_ATTACK_DAMAGE,
      new AttributeModifier("GENERIC_ATTACK_DAMAGE", -1, AttributeModifier.Operation.ADD_NUMBER));
  }

  public static void giveBuilderItems(Player player) {
    player.getInventory().clear();
    if(Vars.getFighter(player).getVipInventory() == null) {
      player.getInventory().clear();
    }else {
      player.getInventory().setContents(Vars.getFighter(player).getVipInventory());
    }
    player.setLevel(Vars.getFighter(player).getSandboxLevel());
    player.setExp(Vars.getFighter(player).getSandboxExp());
  }

  @EventHandler
  public void onCraft(CraftItemEvent e) {
    if(Zgen.worldSand(e.getWhoClicked().getWorld())) {
      return;
    }
    if (Vars.getFighter(((Player) e.getWhoClicked())).isBuildMode()) {
      Material cType = e.getRecipe().getResult().getType();
      if (cType == Material.TNT || cType == Material.PISTON || cType == Material.STICKY_PISTON) {
        e.setCancelled(true);
        return;
      } else {
        return;
      }
    }
    e.setCancelled(true);
  }

  @EventHandler
  public void onEnderChestOpen(PlayerInteractEvent e) {
    if(Zgen.worldSand(e.getPlayer().getWorld())) {
      return;
    }
    if (e.getHand() == EquipmentSlot.OFF_HAND) {
      return; // off hand packet, ignore.
    }
    if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }
    if (e.getClickedBlock() == null) {
      return;
    }
    if (!(Zgen.safeZone(e.getPlayer().getLocation()))) {
      return;
    }
    if (!(Zgen.inPlayGround(e.getClickedBlock().getLocation()))) {
      return;
    }
    if (Vars.getFighter(e.getPlayer()).isBuildMode()) {
      return;
    } else {
      e.setCancelled(true);
      e.getPlayer().sendMessage(ChatColor.RED + "You must be in" + ChatColor.AQUA + ""
        + ChatColor.BOLD + " Sandbox Mode " + ChatColor.RED + "to use this!");
    }
  }
  
  public static void investAllTokens(Player player) {
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }
    if(!Vars.getFighter(player).isBuildMode()) {
      player.sendMessage(ChatColor.RED + "You have to be in" + ChatColor.AQUA + ""
        + ChatColor.BOLD + " Survival Mode " + ChatColor.RED + "to transfer cakes");
    }
    if (player.getInventory() == null) {
      player.sendMessage(ChatColor.RED + "You don't have any" + ChatColor.GREEN + ""
        + ChatColor.BOLD + " Cakes " + ChatColor.RED + "to transfer");
      return;
    }
    if (player.getInventory().getContents() == null) {
      player.sendMessage(ChatColor.RED + "You don't have any" + ChatColor.GREEN + ""
        + ChatColor.BOLD + " Cakes " + ChatColor.RED + "to transfer");
      return;
    }
    int amountOf = 0;
    for (ItemStack inventoryStack : player.getInventory().getContents()) {
      if(inventoryStack == null) {
        continue;
      }
      if(inventoryStack.getType() == null) {
        continue;
      }
      if (inventoryStack.getType() == C_CakeSpawner.gatCakeMaterial()) {
        amountOf++;
      }
    }
    if (amountOf <= 0) {
      player.sendMessage(ChatColor.RED + "You don't have any" + ChatColor.GREEN + ""
        + ChatColor.BOLD + " Cakes " + ChatColor.RED + "to transfer");
      return;
    }
    player.getInventory().remove(C_CakeSpawner.gatCakeMaterial());
    Vars.getFighter(player).incTokens(amountOf);
    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Transfered: " + amountOf + " "
      + "cakes to balance");
    return;
  }

  public static ArrayList<String> getInMode() {
    return inMode;
  }

  public static void setInMode(ArrayList<String> inMode) {
    J_BuilderMode.inMode = inMode;
  }

}
