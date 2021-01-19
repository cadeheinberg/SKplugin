package me.cade.PluginSK;

import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_16_R3.MobEffect;
import net.minecraft.server.v1_16_R3.MobEffectList;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_16_R3.PlayerConnection;

public class PlayerGlow {

  public static void setPlayerGlowOn(Player glowingPlayer, Player[] viewingPlayers) {
    
    CraftPlayer cPlayer = (CraftPlayer) glowingPlayer;
    PacketPlayOutEntityEffect packet = new PacketPlayOutEntityEffect(cPlayer.getEntityId(),
      new MobEffect(MobEffectList.fromId(24), 120, 1));
    
    for (Player player : viewingPlayers) {
      PlayerConnection conn = ((CraftPlayer) player).getHandle().playerConnection;
      conn.sendPacket(packet);
      
    }
  }

}
