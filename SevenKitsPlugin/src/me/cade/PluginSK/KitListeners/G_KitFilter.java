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
      G0_Noob.deActivateSpecial(player);
    } else if (kitID == F1_Beserker.getKitID()) {
      G1_Beserker.deActivateSpecial(player);
    } else if (kitID == F2_Scorch.getKitID()) {
      G2_Scorch.deActivateSpecial(player);
    } else if (kitID == F3_Goblin.getKitID()) {
      G3_Goblin.deActivateSpecial(player);
    } else if (kitID == F4_Igor.getKitID()) {
      G4_Igor.deactivateSpecialAbility(player);
    } else if (kitID == F5_Sumo.getKitID()) {
      G5_Sumo.deActivateSpecial(player);
    } else if (kitID == F6_Grief.getKitID()) {
      G6_Grief.deActivateSpecial(player);
    } 
  }

}
