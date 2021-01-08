package me.cade.PluginSK.kitlisteners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import me.cade.PluginSK.kitbuilders.F0_Materials;
import me.cade.PluginSK.kitbuilders.F0_Stats;
import me.cade.PluginSK.kitbuilders.F2_Booster;

public class G_SandHearing {

  public static boolean doSandKitListener(Player player, ItemStack item) {
    if (item.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Booster Axe")) {
      int weaponId = 0;
      if (item.getType() == F0_Materials.getMaterialFromKitIDAndIndex(1, 0)) {
        weaponId = 0;
      } else if (item.getType() == F0_Materials.getMaterialFromKitIDAndIndex(1, 1)) {
        weaponId = 1;
      } else if (item.getType() == F0_Materials.getMaterialFromKitIDAndIndex(1, 2)) {
        weaponId = 2;
      } else if (item.getType() == F0_Materials.getMaterialFromKitIDAndIndex(1, 3)) {
        weaponId = 3;
      }
      if (!G2_Booster.doBooster(player, weaponId)) {
        return true;
      }else {
        //did booster
        for (int i = 0 ; i < 4; i ++) {
          if(i == weaponId) {
            continue;
          }
          player.setCooldown(F0_Materials.getMaterialFromKitIDAndIndex(1, i), F0_Stats.getTicksList(F2_Booster.getKitID() - 1)[weaponId]);
        }
      }
    }
    return false;
  }

}
