package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class X_InventoryClick implements Listener {

  @EventHandler
  public void inventoryClick(InventoryClickEvent e) {
    
    if(Zgen.worldSand(e.getWhoClicked().getWorld())){
      return;
    }

    Player player = (Player) e.getWhoClicked();

    if (e.getSlotType() == SlotType.ARMOR) {
      if(Vars.getFighter((Player) e.getWhoClicked()).isBuildMode()) {
        return;
      }
      if(e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
        return;
      }
      e.setCancelled(true);
      return;
    }
    if (e.getSlotType() ==SlotType.QUICKBAR) {
      if(Vars.getFighter((Player) e.getWhoClicked()).isBuildMode()) {
        return;
      }
      if(e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
        return;
      }
      e.setCancelled(true);
      return;
    }
    if (e.getSlotType() == SlotType.OUTSIDE) {
      if(e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
        return;
      }
      e.setCancelled(true);
      return;
    }
    if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
      if(e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
        return;
      }
      InventoryType invenType = e.getInventory().getType();
      if (invenType != InventoryType.CHEST) {
        return;
      } else if (Vars.getFighter(player).isBuildMode()) {
        return;
      }
      e.setCancelled(true);
      if (!(Zgen.inPlayGround(player.getLocation()))) {
        Vars.getFighter(player).giveKit(true);
      }
      player.closeInventory();
      player.sendMessage(ChatColor.RED + "Don't try to sneak click items");
      return;
    }

    if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.RIGHT) {
    } else {
      player.closeInventory();
      return;
    }

    Inventory clicked = e.getClickedInventory();

    int index = -1;
    int slot = e.getSlot();

    if (slot == 19) {
      index = 0;
    } else if (slot == 21) {
      index = 1;
    } else if (slot == 23) {
      index = 2;
    } else if (slot == 25) {
      index = 3;
    }

    //////////////////////////////////////////////
    /////// NOOB NOOB NOOB NOOB NOOB
    /////// NOOB NOOB NOOB NOOB NOOB
    //////////////////////////////////////////////
    if (clicked.equals(F1_Noob.getKitMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 19) {
        Vars.getFighter(player).giveKit(F1_Noob.getKitID(), 0, true);
        player.closeInventory();
        return;
      }
      if (F1_Noob.giveThisKit(player, index)) {
        Vars.getFighter(player).giveKit(F1_Noob.getKitID(), index, true);
        return;
      }
    } else if (clicked.equals(F1_Noob.getBuyMenu())) {
      if (slot == 22) {
        int numKit = Vars.getFighter(player).getUnlocked()[0];
        if (numKit == -1) {
          index = 0;
        } else {
          index = numKit + 1;
        }
        if (F1_Noob.buyThisKit(player, index)) {
          Vars.getFighter(player).giveKit(F1_Noob.getKitID(), index, true);
        }
      }
      return;
    }
    //////////////////////////////////////////////
    /////// BOOSTER BOOSTER BOOSTER BOOSTER
    /////// BOOSTER BOOSTER BOOSTER BOOSTER
    //////////////////////////////////////////////
    else if (clicked.equals(F2_Booster.getKitMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 19) {
        Vars.getFighter(player).giveKit(F2_Booster.getKitID(), 0, true);
        player.closeInventory();
        return;
      }
      if (F2_Booster.giveThisKit(player, index)) {
        Vars.getFighter(player).giveKit(F2_Booster.getKitID(), index, true);
        return;
      }
    } else if (clicked.equals(F2_Booster.getBuyMenu())) {
      if (slot == 22) {
        int numKit = Vars.getFighter(player).getUnlocked()[1];
        if (numKit == -1) {
          index = 0;
        } else {
          index = numKit + 1;
        }
        if (F2_Booster.buyThisKit(player, index)) {
          Vars.getFighter(player).giveKit(F2_Booster.getKitID(), index, true);
        } else {
        }
      }
    }
    //////////////////////////////////////////////
    /////// SHOTTY SHOTTY SHOTTY SHOTTY
    /////// SHOTTY SHOTTY SHOTTY SHOTTY
    //////////////////////////////////////////////
    else if (clicked.equals(F3_Shotty.getKitMenu())) {
      if (index == -1) {
        return;
      }
      if (F3_Shotty.giveThisKit(player, index)) {
        Vars.getFighter(player).giveKit(F3_Shotty.getKitID(), index, true);
        return;
      }
    } else if (clicked.equals(F3_Shotty.getBuyMenu())) {
      if (slot == 22) {
        int numKit = Vars.getFighter(player).getUnlocked()[2];
        if (numKit == -1) {
          index = 0;
        } else {
          index = numKit + 1;
        }
        if (F3_Shotty.buyThisKit(player, index)) {
          Vars.getFighter(player).giveKit(F3_Shotty.getKitID(), index, true);
        } else {
        }
      }
    }
    //////////////////////////////////////////////
    /////// GOBLIN GOBLIN GOBLIN GOBLIN
    /////// GOBLIN GOBLIN GOBLIN GOBLIN
    //////////////////////////////////////////////
    else if (clicked.equals(F4_Goblin.getKitMenu())) {
      if (index == -1) {
        return;
      }
      if (F4_Goblin.giveThisKit(player, index)) {
        Vars.getFighter(player).giveKit(F4_Goblin.getKitID(), index, true);
        return;
      }
    } else if (clicked.equals(F4_Goblin.getBuyMenu())) {
      if (slot == 22) {
        int numKit = Vars.getFighter(player).getUnlocked()[3];
        if (numKit == -1) {
          index = 0;
        } else {
          index = numKit + 1;
        }
        if (F4_Goblin.buyThisKit(player, index)) {
          Vars.getFighter(player).giveKit(F4_Goblin.getKitID(), index, true);
        } else {
        }
      }
    }
    //////////////////////////////////////////////
    /////// IGOR IGOR IGOR IGOR
    /////// IGOR IGOR IGOR IGOR
    //////////////////////////////////////////////
    else if (clicked.equals(F5_Igor.getKitMenu())) {
      if (index == -1) {
        return;
      }
      if (F5_Igor.giveThisKit(player, index)) {
        Vars.getFighter(player).giveKit(F5_Igor.getKitID(), index, true);
        return;
      }
    } else if (clicked.equals(F5_Igor.getBuyMenu())) {
      if (slot == 22) {
        int numKit = Vars.getFighter(player).getUnlocked()[4];
        if (numKit == -1) {
          index = 0;
        } else {
          index = numKit + 1;
        }
        if (F5_Igor.buyThisKit(player, index)) {
          Vars.getFighter(player).giveKit(F5_Igor.getKitID(), index, true);
        } else {
        }
      }
    }
    //////////////////////////////////////////////
    /////// HEAVY HEAVY HEAVY HEAVY
    /////// HEAVY HEAVY HEAVY HEAVY
    //////////////////////////////////////////////
    else if (clicked.equals(F6_Heavy.getKitMenu())) {
      if (index == -1) {
        return;
      }
      if (F6_Heavy.giveThisKit(player, index)) {
        Vars.getFighter(player).giveKit(F6_Heavy.getKitID(), index, true);
        return;
      }
    } else if (clicked.equals(F6_Heavy.getBuyMenu())) {
      if (slot == 22) {
        int numKit = Vars.getFighter(player).getUnlocked()[5];
        if (numKit == -1) {
          index = 0;
        } else {
          index = numKit + 1;
        }
        if (F6_Heavy.buyThisKit(player, index)) {
          Vars.getFighter(player).giveKit(F6_Heavy.getKitID(), index, true);
        } else {
        }
      }
    }
    //////////////////////////////////////////////
    /////// WIZARD WIZARD WIZARD WIZARD
    /////// WIZARD WIZARD WIZARD WIZARD
    //////////////////////////////////////////////
    else if (clicked.equals(F7_Wizard.getKitMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 21) {
        if (F7_Wizard.giveThisKit(player)) {
          Vars.getFighter(player).giveKit(F7_Wizard.getKitID(), index, true);
          return;
        }
      } else if (slot == 23) {
        F7_Wizard.openSpellMenu(player);
      }
    } else if (clicked.equals(F7_Wizard.getSpellMenu())) {
      if (index == -1) {
        return;
      }
      if (F7_Wizard.tryToUseThisSpell(player, index)) {
        player.closeInventory();
        player.sendMessage(ChatColor.AQUA + "You already own this Spell!");
      }
    } else if (clicked.equals(F7_Wizard.getBuyWandMenu())) {
      if (slot == 22) {
        if (F7_Wizard.tryToBuyWand(player)) {
          Vars.getFighter(player).giveKit(F7_Wizard.getKitID(), index, true);
        }
      }
    } else if (clicked.equals(F7_Wizard.getSpellBuyMenu())) {
      if (slot == 22) {
        int numKit = Vars.getFighter(player).getUnlocked()[6];
        if (numKit == -1) {
          index = 0;
        } else {
          index = numKit + 1;
        }
        if (F7_Wizard.tryToBuySpell(player, index)) {
          int numOfSpells = Vars.getFighter(player).getUnlocked()[6];
          if (Vars.getFighter(player).getWizardKit() == null) {
            return;
          }
          Vars.getFighter(player).getWizardKit().setNumberOfSpells(numOfSpells);
        }
      }
    }
    //////////////////////////////////////////////
    /////// STREAK STREAK STREAK STREAK
    /////// STREAK STREAK STREAK STREAK
    //////////////////////////////////////////////
    else if (clicked.equals(D3_Menus.getStreakMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 19) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getKillStreakAttribute() > 0) {
          pFight.setKillStreakAttribute(0);
          pFight.removeKillStreakAttributes();
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You don't have any " + ChatColor.WHITE + "killstreaks"
            + ChatColor.RED + " set to use this!");
        }
      } else if (slot == 21) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 5) {
          pFight.removeKillStreakAttributes();
          pFight.setKillStreakAttribute(1);
          pFight.applyKillStreakAttribute();
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "5"
            + ChatColor.RED + " to use this!");
        }
      } else if (slot == 23) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 8) {
          pFight.removeKillStreakAttributes();
          pFight.setKillStreakAttribute(2);
          pFight.applyKillStreakAttribute();
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "8"
            + ChatColor.RED + " to use this!");
        }
      } else if (slot == 25) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 12) {
          pFight.removeKillStreakAttributes();
          pFight.setKillStreakAttribute(3);
          pFight.applyKillStreakAttribute();
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "12"
            + ChatColor.RED + " to use this!");
        }
      }
    }
    //////////////////////////////////////////////
    /////// ARMOR ARMOR ARMOR ARMOR
    /////// ARMOR ARMOR ARMOR ARMOR
    //////////////////////////////////////////////
    else if (clicked.equals(D3_Menus.getArmorMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 19) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getProtectionArmorAttribute() > 0) {
          pFight.setProtectionArmorAttribute(0);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You dont have an " + ChatColor.WHITE + "armor buff"
            + ChatColor.RED + " on to use this!");
        }
      } else if (slot == 21) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 8) {
          pFight.setProtectionArmorAttribute(1);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "8"
            + ChatColor.RED + " to use this!");
        }
      } else if (slot == 23) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 15) {
          pFight.setProtectionArmorAttribute(2);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "15"
            + ChatColor.RED + " to use this!");
        }
      } else if (slot == 25) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 22) {
          pFight.setProtectionArmorAttribute(3);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "22"
            + ChatColor.RED + " to use this!");
        }
      }
    }
    //////////////////////////////////////////////
    /////// LEVEL LEVEL LEVEL LEVEL
    /////// LEVEL LEVEL LEVEL LEVEL
    //////////////////////////////////////////////
    else if (clicked.equals(D3_Menus.getLevelMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 21) {
        player.closeInventory();
        E1_Fighter pFight = Vars.getFighter(player);
        player.sendMessage(
          ChatColor.YELLOW + "You are level " + ChatColor.WHITE + pFight.getPlayerLevel()
            + ChatColor.YELLOW + ", prestige " + ChatColor.WHITE + pFight.getPrestige());
        player.sendMessage(ChatColor.YELLOW + "Exp / Exp till next level: " + ChatColor.WHITE
          + pFight.getExp() + "/" + E2_Experience.getExpNeeded(pFight.getPlayerLevel()));
      } else if (slot == 23) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPrestige() >= 3) {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.AQUA + "You are already max prestige!");
          return;
        }
        if (pFight.getPlayerLevel() >= 28) {
          pFight.incPrestige();
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "28"
            + ChatColor.RED + " to use this!");
        }
      }
    }
    //////////////////////////////////////////////
    /////// SKILL SKILL SKILL SKILL
    /////// SKILL SKILL SKILL SKILL
    //////////////////////////////////////////////
    else if (clicked.equals(D3_Menus.getAgilityMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 19) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getSpeedAttribute() > 0) {
          player.removePotionEffect(PotionEffectType.SPEED);
          pFight.setSpeedAttribute(0);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You don't have an " + ChatColor.WHITE + "agility buff"
            + ChatColor.RED + " on to use this!");
        }
      } else if (slot == 21) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 6) {
          player.removePotionEffect(PotionEffectType.SPEED);
          player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 0));
          pFight.setSpeedAttribute(1);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "6"
            + ChatColor.RED + " to use this!");
        }
      } else if (slot == 23) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 11) {
          player.removePotionEffect(PotionEffectType.SPEED);
          player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
          pFight.setSpeedAttribute(2);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "11"
            + ChatColor.RED + " to use this!");
        }
      } else if (slot == 25) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 17) {
          player.removePotionEffect(PotionEffectType.SPEED);
          player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 2));
          pFight.setSpeedAttribute(3);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "17"
            + ChatColor.RED + " to use this!");
        }
      }
    }
    //////////////////////////////////////////////
    /////// ITEMS ITEMS ITEMS ITEMS
    /////// ITEMS ITEMS ITEMS ITEMS
    //////////////////////////////////////////////
    else if (clicked.equals(D3_Menus.getSpecialItemMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 19) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getSpecialItemAttribute() > 0) {
          pFight.setSpecialItemAttribute(0);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You don't have a " + ChatColor.WHITE + "special item"
            + ChatColor.RED + " selected to use this!");
        }
      } else if (slot == 21) {
        E1_Fighter pFight = Vars.getFighter(player);
        player.closeInventory();
        if (pFight.getPlayerLevel() >= 10 || pFight.getPrestige() > 1) {
          if (pFight.getSpecialItemAttribute() != 0) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You already have a " + ChatColor.WHITE
              + "Special Item" + ChatColor.RED + " equiped");
            return;
          }
          if (pFight.getCakes() >= 200) {
            pFight.decCakes(200);
          } else {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You need " + ChatColor.WHITE + "200" + ChatColor.RED
              + " cakes to use this!");
            return;
          }
          pFight.setSpecialItemAttribute(1);
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(ChatColor.RED + "You need to be level " + ChatColor.WHITE + "10"
            + ChatColor.RED + " to use this!");
          return;
        }
      } else if (slot == 23) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 1 && pFight.getPrestige() >= 2) {
          if (pFight.getSpecialItemAttribute() != 0) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You already have a " + ChatColor.WHITE
              + "Special Item" + ChatColor.RED + " equiped");
            return;
          }
          if (pFight.getCakes() >= 500) {
            pFight.decCakes(500);
          } else {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You need " + ChatColor.WHITE + "500" + ChatColor.RED
              + " cakes to use this!");
            return;
          }
          pFight.setSpecialItemAttribute(2);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(
            ChatColor.RED + "You need to be level " + ChatColor.WHITE + "1" + ChatColor.RED
              + ", prestige " + ChatColor.WHITE + "2" + ChatColor.RED + " to use this!");
        }
      } else if (slot == 25) {
        E1_Fighter pFight = Vars.getFighter(player);
        if (pFight.getPlayerLevel() >= 28 && pFight.getPrestige() >= 3) {
          if (pFight.getSpecialItemAttribute() != 0) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You already have a " + ChatColor.WHITE
              + "Special Item" + ChatColor.RED + " equiped");
            return;
          }
          if (pFight.getCakes() >= 2000) {
            pFight.decCakes(2000);
          } else {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "You need " + ChatColor.WHITE + "2000"
              + ChatColor.RED + " cakes to use this!");
            return;
          }
          pFight.setSpecialItemAttribute(3);
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 8, 1);
          pFight.giveKit(true);
        } else {
          player.closeInventory();
          player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 8, 1);
          player.sendMessage(
            ChatColor.RED + "You need to be level " + ChatColor.WHITE + "28" + ChatColor.RED
              + ", prestige " + ChatColor.WHITE + "3" + ChatColor.RED + " to use this!");
        }
      }
    }
    //////////////////////////////////////////////
    /////// DAILY DAILY DAILY DAILY
    /////// DAILY DAILY DAILY DAILY
    //////////////////////////////////////////////
    // else if (clicked.equals(D3_Menus.getDailyRewardMenu())) {
    // if (index == -1) {
    // return;
    // }
    // if (slot == 19) {
    // player.closeInventory();
    // player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Daily Cookies: "
    // + ChatColor.WHITE + "" + ChatColor.BOLD + "cookies to add");
    // } else if (slot == 21) {
    // player.closeInventory();
    // player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Vote for our server!: ");
    // player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "1. " + ChatColor.RESET + ""
    // + ChatColor.GREEN + "(100 cookies)" + ChatColor.YELLOW
    // + "https://www.planetminecraft.com/server/seven-kits-pvp/vote/");
    // } else if (slot == 23) {
    // player.closeInventory();
    // player.sendMessage(
    // ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Amount of basic chests here: "
    // + ChatColor.WHITE + "" + ChatColor.BOLD + "open 1 if greater than 0");
    // } else if (slot == 25) {
    // player.closeInventory();
    // player.sendMessage(
    // ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Amount of legendary chests here: "
    // + ChatColor.WHITE + "" + ChatColor.BOLD + "open 1 if greater than 0");
    // }
    // }
    //////////////////////////////////////////////
    /////// COSMETIC COSMETIC COSMETIC COSMETIC
    /////// COSMETIC COSMETIC COSMETIC COSMETIC
    //////////////////////////////////////////////
    else if (clicked.equals(D3_Menus.getCosmeticMenu())) {
      if (index == -1) {
        return;
      }
      if (slot == 19) {
        player.openInventory(D3_Menus.getChangeArmorColorMenu());
      }
    }
    //////////////////////////////////////////////
    /////// COLOR COLOR COLOR COLOR
    /////// COLOR COLOR COLOR COLOR
    //////////////////////////////////////////////
    else if (clicked.equals(D3_Menus.getChangeArmorColorMenu())) {
      player.closeInventory();
      E1_Fighter pFight = Vars.getFighter(player);
      if (e.getSlot() == 44) {
        pFight.setCustomColorIndex(-1);
      } else {
        pFight.setCustomColorIndex(e.getSlot());
      }
      pFight.giveKit(true);
    }
    //////////////////////////////////////////////
    /////// WIZARD WIZARD WIZARD WIZARD
    /////// WIZARD WIZARD WIZARD WIZARD
    //////////////////////////////////////////////
    else if (Vars.getFighter(player).getKitID() == 7)

    {
      if (clicked.equals(Vars.getFighter(player).getWizardKit().getChangeSpellMenu())) {
        int amount = Vars.getFighter(player).getWizardKit().getNumSpells();
        if (slot == 19) {
          Vars.getFighter(player).setKitIndex(0);
          player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
          player.closeInventory();
        } else if (slot == 21) {
          if (amount < 1) {
            return;
          }
          Vars.getFighter(player).setKitIndex(1);
          player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
          player.closeInventory();
        } else if (slot == 23) {
          if (amount < 2) {
            return;
          }
          Vars.getFighter(player).setKitIndex(2);
          player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
          player.closeInventory();
        } else if (slot == 25) {
          if (amount < 3) {
            return;
          }
          Vars.getFighter(player).setKitIndex(3);
          player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 8, 1);
          player.closeInventory();
        }
      }
    }
  }
}
