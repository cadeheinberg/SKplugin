package me.cade.PluginSK.KitListeners;

import org.bukkit.entity.Player;
import me.cade.PluginSK.BuildKits.*;

public class G_KitFilter {
  
  public static void deactivateSpecialFromKitID(Player player, int kitID, int kitIndex) {
    if(player == null) {
      return;
    }
    if(!player.isOnline()) {
      return;
    }
    if (kitID == F0_Noob.getKitID()) {

    } else if (kitID == F1_Beserker.getKitID()) {

    } else if (kitID == F2_Scorch.getKitID()) {
      G2_Scorch.deActivateSpecial(player);
    } else if (kitID == F3_Goblin.getKitID()) {
      G3_Goblin.deActivateSpecial(player);
    } else if (kitID == F4_Igor.getKitID()) {

    } else if (kitID == F5_Heavy.getKitID()) {

    } else if (kitID == F6_Zero.getKitID()) {
      G6_Zero.deActivateSpecial(player);
    }
  }

}
