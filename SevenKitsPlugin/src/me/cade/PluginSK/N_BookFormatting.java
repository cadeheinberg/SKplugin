package me.cade.PluginSK;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;

public class N_BookFormatting implements Listener {

  @EventHandler
  public void onCloseBook(PlayerEditBookEvent event) {
    if(Zgen.worldSand(event.getPlayer().getWorld())) {
      return;
    }
    if (!event.isSigning()) {
      return;
    }

    // Get book
    BookMeta book = event.getNewBookMeta();

    // Modify
    book = formatBook(book);

    // Update
    event.setNewBookMeta(book);
  }

  public static BookMeta formatBook(BookMeta book) {
    // Get
    List<String> pages = book.getPages();
    List<String> newPages = new ArrayList<>();

    // Format
    for (String page : pages) {
      newPages.add(ChatColor.translateAlternateColorCodes('&', page));
    }

    book.setPages(newPages);
    return book;
  }

}
