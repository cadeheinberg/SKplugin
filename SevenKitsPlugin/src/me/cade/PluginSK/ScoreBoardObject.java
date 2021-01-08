package me.cade.PluginSK;

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
import me.cade.PluginSK.cakes.C_CakeSpawner;

public class ScoreBoardObject {

  private Player player;
  private E1_Fighter pFight;
  
  private Scoreboard theBoard;

  private Team cookies;
  private Team tokens;
  private Team kills;
  private Team ratio;
  private Team killStreak;
  private Team level;
  private Team exp;
  private Team noColliders;
  private Team stock;
  private Team nextStock;
  private Team footer;
  
  @SuppressWarnings("deprecation")
  public ScoreBoardObject(Player player) {
    this.setPlayer(player);
    this.pFight = Vars.getFighter(player);

    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective objective = board.registerNewObjective("hi", "hello");
    Objective h = board.registerNewObjective("showhealth", Criterias.HEALTH);
    h.setDisplayName(ChatColor.DARK_RED + "❤");
    h.setDisplaySlot(DisplaySlot.BELOW_NAME);
    objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "----KIT PVP---- ");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    Score b = objective.getScore("");
    b.setScore(14);

    cookies = board.registerNewTeam("Cookies");
    cookies.setPrefix(ChatColor.AQUA + "Money: ");
    cookies.setSuffix(ChatColor.WHITE + "$" + pFight.getCakes());
    cookies.addEntry(ChatColor.AQUA + "");
    objective.getScore(ChatColor.AQUA + "").setScore(13);
    
    tokens = board.registerNewTeam("Tokens");
    tokens.setPrefix(ChatColor.AQUA + "Cakes: ");
    tokens.setSuffix(ChatColor.WHITE + "" + pFight.getTokens());
    tokens.addEntry(ChatColor.GRAY + "");
    objective.getScore(ChatColor.GRAY + "").setScore(12);

    Score b1 = objective.getScore(" ");
    b1.setScore(11);

    kills = board.registerNewTeam("Kills");
    kills.setPrefix(ChatColor.LIGHT_PURPLE + "K/D: ");
    kills.setSuffix(ChatColor.WHITE + "" + pFight.getKills() + " / " + pFight.getDeaths());
    kills.addEntry(ChatColor.YELLOW + "");
    objective.getScore(ChatColor.YELLOW + "").setScore(10);

    ratio = board.registerNewTeam("Ratio");
    ratio.setPrefix(ChatColor.LIGHT_PURPLE + "Ratio: ");
    ratio.setSuffix(ChatColor.WHITE + "" + getRatio());
    ratio.addEntry(ChatColor.DARK_AQUA + "");
    objective.getScore(ChatColor.DARK_AQUA + "").setScore(9);

    killStreak = board.registerNewTeam("Killstreak");
    killStreak.setPrefix(ChatColor.LIGHT_PURPLE + "Killstreak: ");
    killStreak.setSuffix(ChatColor.WHITE + "" + pFight.getKillStreak());
    killStreak.addEntry(ChatColor.DARK_BLUE + "");
    objective.getScore(ChatColor.DARK_BLUE + "").setScore(8);

    Score b3 = objective.getScore("   ");
    b3.setScore(7);

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
    
    Score b5 = objective.getScore("     ");
    b5.setScore(1);
    
    footer = board.registerNewTeam("Foot");
    footer.setPrefix("");
    footer.setSuffix(ChatColor.GOLD + "" + ChatColor.BOLD + "---SEVEN KITS---");
    footer.addEntry(ChatColor.BLACK + "");
    objective.getScore(ChatColor.BLACK + "").setScore(0);
    
    noColliders = board.registerNewTeam(player.getName());
    noColliders.addPlayer(player);
    noColliders.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
    
    theBoard = board;
    player.setScoreboard(board);
  }

  public void updateCookies() {
    cookies.setSuffix(ChatColor.WHITE + "$" + pFight.getCakes());
  }
  
  public void updateTokens() {
    tokens.setSuffix(ChatColor.WHITE + "" + pFight.getTokens());
  }

  public void updateKills() {
    kills.setSuffix(ChatColor.WHITE + "" + pFight.getKills() + " / " + pFight.getDeaths());
  }

  public void updateDeaths() {
    kills.setSuffix(ChatColor.WHITE + "" + pFight.getKills() + " / " + pFight.getDeaths());
  }

  public void updateRatio() {
    ratio.setSuffix(ChatColor.WHITE + "" + getRatio());
  }

  public void updateKillstreak() {
    killStreak.setSuffix(ChatColor.WHITE + "" + pFight.getKillStreak());
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
    updateKills();
    updateDeaths();
    updateRatio();
    updateKillstreak();
    updateLevel();
    updateExp();
    updateStock();
    updateStockTime();
  }

  public String getRatio() {
    double ratioNum = 0;
    if (pFight.getDeaths() == 0) {
      ratioNum = pFight.getKills();
    } else if (pFight.getKills() == 0) {
      ratioNum = 0;
    } else {
      ratioNum = (double) pFight.getKills() / (double) pFight.getDeaths();
    }
    String ratioCut = Double.toString(ratioNum);
    if (ratioCut.length() > 4) {
      ratioCut = ratioCut.substring(0, 4);
    }
    return ratioCut;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

}

