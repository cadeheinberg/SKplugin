package me.cade.PluginSK;

import org.bukkit.entity.Player;
import net.minecraft.server.v1_16_R3.DataWatcher;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.reflect.FieldUtils;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;

public class Glowing {
  
  public static HashMap<UUID, UUID> glowMap = new HashMap<UUID, UUID>();

  @SuppressWarnings("unchecked")
  public static void setGlowing(Player glowingPlayer, Player sendPacketPlayer, boolean glow) {
    glowMap.put(sendPacketPlayer.getUniqueId(), glowingPlayer.getUniqueId());
      try {
          EntityPlayer entityPlayer = ((CraftPlayer) glowingPlayer).getHandle();

          DataWatcher dataWatcher = entityPlayer.getDataWatcher();

          entityPlayer.glowing = glow; // For the update method in EntityPlayer to prevent switching back.

          // The map that stores the DataWatcherItems is private within the DataWatcher Object.
          // We need to use Reflection to access it from Apache Commons and change it.
          Map<Integer, DataWatcher.Item<?>> map = (Map<Integer, DataWatcher.Item<?>>) FieldUtils.readDeclaredField(dataWatcher, "d", true);

          // Get the 0th index for the BitMask value. http://wiki.vg/Entities#Entity
          @SuppressWarnings("rawtypes")
          DataWatcher.Item item = map.get(0);
          byte initialBitMask = (Byte) item.b(); // Gets the initial bitmask/byte value so we don't overwrite anything.
          byte bitMaskIndex = (byte) 0x40; // The index as specified in wiki.vg/Entities
          if (glow) {
              item.a((byte) (initialBitMask | 1 << bitMaskIndex));
          } else {
              item.a((byte) (initialBitMask & ~(1 << bitMaskIndex))); // Inverts the specified bit from the index.
          }

          PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(glowingPlayer.getEntityId(), dataWatcher, true);

          ((CraftPlayer) sendPacketPlayer).getHandle().playerConnection.sendPacket(metadataPacket);
      } catch (Exception e) { // Catch statement necessary for FieldUtils.readDeclaredField()
          
      }
  }
  
}
