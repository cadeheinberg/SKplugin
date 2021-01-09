package me.cade.PluginSK.BuildKits;

import org.bukkit.entity.Player;

public class F_KitFilter {
  
  public static void giveKitFromKitID(Player player, int kitID, int kitIndex) {
    if (kitID == F0_Noob.getKitID()) {
      new F0_Noob(player, kitIndex);
    } else if (kitID == F1_Booster.getKitID()) {
      new F1_Booster(player, kitIndex);
    } else if (kitID == F2_Shotty.getKitID()) {
      new F2_Shotty(player, kitIndex);
    } else if (kitID == F3_Goblin.getKitID()) {
      new F3_Goblin(player, kitIndex);
    } else if (kitID == F4_Igor.getKitID()) {
      new F4_Igor(player, kitIndex);
    } else if (kitID == F5_Heavy.getKitID()) {
      new F5_Heavy(player, kitIndex);
    } else if (kitID == F6_Zero.getKitID()) {
      new F6_Zero(player, kitIndex);
    }
  }

}
