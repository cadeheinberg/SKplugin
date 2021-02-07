package me.cade.PluginSK;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeSend {
  
  private static Plugin plugin = Main.getPlugin(Main.class);
  
  public static void sendPlayer(Player player) {
    ByteArrayDataOutput out = ByteStreams.newDataOutput();
    out.writeUTF("Connect");
    out.writeUTF("SD_1");
    player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
  }

}
