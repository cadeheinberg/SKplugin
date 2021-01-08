package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;
import me.cadeheinberg.SevenKitsPlugin.cakes.C_CakeSpawner;

public class SandScoreBoardObject {

  private Player player;
  private E1_Fighter pFight;

  private Scoreboard theBoard;
  
  private Team cakes;
  private Team level;
  private Team exp;
  private Team stock;
  private Team noColliders;
  private Team tokens;
  private Team nextStock;
  private Team footer;
  
  @SuppressWarnings("deprecation")
  public SandScoreBoardObject(Player player) {
    this.setPlayer(player);
    this.pFight = Vars.getFighter(player);

    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective objective = board.registerNewObjective("hi", "hello");
    Objective h = board.registerNewObjective("showhealth", Criterias.HEALTH);
    h.setDisplayName(ChatColor.DARK_RED + "❤");
    h.setDisplaySlot(DisplaySlot.BELOW_NAME);
    objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "----SURVIVAL----  ");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    Score b = objective.getScore("");
    b.setScore(10);

    cakes = board.registerNewTeam("Cookies");
    cakes.setPrefix(ChatColor.AQUA + "Money: ");
    cakes.setSuffix(ChatColor.WHITE + "$" + pFight.getCakes());
    cakes.addEntry(ChatColor.AQUA + "");
    objective.getScore(ChatColor.AQUA + "").setScore(9);
    
    tokens = board.registerNewTeam("Tokens");
    tokens.setPrefix(ChatColor.AQUA + "Cakes: ");
    tokens.setSuffix(ChatColor.WHITE + "" + pFight.getTokens());
    tokens.addEntry(ChatColor.GRAY + "");
    objective.getScore(ChatColor.GRAY + "").setScore(8);

    Score b1 = objective.getScore(" ");
    b1.setScore(7);

    level = board.registerNewTeam("Level");
    level.setPrefix(ChatColor.YELLOW + "Level: ");
    level.setSuffix(ChatColor.WHITE + "" + pFight.getPlayerLevel());
    level.addEntry(ChatColor.DARK_GRAY + "");
    objective.getScore(ChatColor.DARK_GRAY + "").setScore(6);

    exp = board.registerNewTeam("Exp");
    exp.setPrefix(ChatColor.YELLOW + "Exp: ");
    exp.setSuffix(ChatColor.WHITE + "" + pFight.getExp() + "/"
      + E2_Experience.getExpNeeded(pFight.getPlayerLevel()));
    exp.addEntry(ChatColor.DARK_GREEN + "");
    objective.getScore(ChatColor.DARK_GREEN + "").setScore(5);

    Score b4 = objective.getScore("    ");
    b4.setScore(4);

    stock = board.registerNewTeam("Stock");
    stock.setPrefix(ChatColor.GREEN + "Cake Value: ");
    stock.setSuffix(ChatColor.WHITE + "" + "$" + C_CakeSpawner.getCakePrice());
    stock.addEntry(ChatColor.DARK_PURPLE + "");
    objective.getScore(ChatColor.DARK_PURPLE + "").setScore(3);

    nextStock = board.registerNewTeam("Time");
    nextStock.setPrefix(ChatColor.GREEN + "New price: ");
    nextStock.setSuffix(ChatColor.WHITE + "" + C_CakeSpawner.getNextStockString() + " min");
    nextStock.addEntry(ChatColor.RED + "");
    objective.getScore(ChatColor.RED + "").setScore(2);
    
    noColliders = board.registerNewTeam(player.getName());
    noColliders.addPlayer(player);
    noColliders.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
    
    Score b5 = objective.getScore("     ");
    b5.setScore(1);
    
    footer = board.registerNewTeam("Foot");
    footer.setPrefix("");
    footer.setSuffix(ChatColor.GOLD + "" + ChatColor.BOLD + "---SEVEN KITS---");
    footer.addEntry(ChatColor.BLACK + "");
    objective.getScore(ChatColor.BLACK + "").setScore(0);

    theBoard = board;
    player.setScoreboard(board);
  }

  public void updateCookies() {
    cakes.setSuffix(ChatColor.WHITE + "$" + pFight.getCakes());
  }
  
  public void updateTokens() {
    tokens.setSuffix(ChatColor.WHITE + "" + pFight.getTokens());
  }

  public void updateLevel() {
    level.setSuffix(ChatColor.WHITE + "" + pFight.getPlayerLevel());
  }

  public void updateExp() {
    exp.setSuffix(ChatColor.WHITE + "" + pFight.getExp() + "/"
      + E2_Experience.getExpNeeded(pFight.getPlayerLevel()));
  }
  
  public void updateStock() {
    stock.setSuffix(ChatColor.WHITE + "" + "$" + C_CakeSpawner.getCakePrice());
  }
  
  public void updateStockTime() {
    nextStock.setSuffix(ChatColor.WHITE + "" + C_CakeSpawner.getNextStockString() + " min");
  }
  
  public void reApplyBoard() {
    player.setScoreboard(theBoard);
  }

  
  public void updateAll() {
    updateCookies();
    updateLevel();
    updateExp();
    updateTokens();
    updateStock();
    updateStockTime();
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
  
}
