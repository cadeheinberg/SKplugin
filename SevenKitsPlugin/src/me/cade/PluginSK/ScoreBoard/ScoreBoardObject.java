package me.cade.PluginSK.ScoreBoard;

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
import me.cade.PluginSK.Fighter;
import me.cade.PluginSK.Money.A_CakeManager;

public class ScoreBoardObject {

  private Player player;
  private Fighter fighter;
  
  private Scoreboard theBoard;

  private Team cookies;
  private Team kills;
  private Team ratio;
  private Team killStreak;
  private Team level;
  private Team exp;
  private Team noColliders;
  private Team footer;
  
  @SuppressWarnings("deprecation")
  public ScoreBoardObject(Player player) {
    this.player = player;
    this.fighter = Fighter.get(player);

    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective objective = board.registerNewObjective("hi", "hello");
    Objective h = board.registerNewObjective("showhealth", Criterias.HEALTH);
    h.setDisplayName(ChatColor.DARK_RED + "❤");
    h.setDisplaySlot(DisplaySlot.BELOW_NAME);
    objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "----KIT PVP---- ");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    Score b = objective.getScore("");
    b.setScore(10);

    cookies = board.registerNewTeam(A_CakeManager.currencyNameSingular);
    cookies.setPrefix(ChatColor.AQUA + A_CakeManager.currencyNameSingular + ": ");
    cookies.setSuffix(ChatColor.WHITE + "$" + fighter.getCakes());
    cookies.addEntry(ChatColor.AQUA + "");
    objective.getScore(ChatColor.AQUA + "").setScore(9);    

    Score b1 = objective.getScore(" ");
    b1.setScore(8);

    kills = board.registerNewTeam("Kills");
    kills.setPrefix(ChatColor.LIGHT_PURPLE + "K/D: ");
    kills.setSuffix(ChatColor.WHITE + "" + fighter.getKills() + " / " + fighter.getDeaths());
    kills.addEntry(ChatColor.YELLOW + "");
    objective.getScore(ChatColor.YELLOW + "").setScore(7);

    ratio = board.registerNewTeam("Ratio");
    ratio.setPrefix(ChatColor.LIGHT_PURPLE + "Ratio: ");
    ratio.setSuffix(ChatColor.WHITE + "" + getRatio());
    ratio.addEntry(ChatColor.DARK_AQUA + "");
    objective.getScore(ChatColor.DARK_AQUA + "").setScore(6);

    killStreak = board.registerNewTeam("Killstreak");
    killStreak.setPrefix(ChatColor.LIGHT_PURPLE + "Killstreak: ");
    killStreak.setSuffix(ChatColor.WHITE + "" + fighter.getKillStreak());
    killStreak.addEntry(ChatColor.DARK_BLUE + "");
    objective.getScore(ChatColor.DARK_BLUE + "").setScore(5);

    Score b3 = objective.getScore("   ");
    b3.setScore(4);

    level = board.registerNewTeam("Level");
    level.setPrefix(ChatColor.YELLOW + "Level: ");
    level.setSuffix(ChatColor.WHITE + "" + fighter.getPlayerLevel());
    level.addEntry(ChatColor.DARK_GRAY + "");
    objective.getScore(ChatColor.DARK_GRAY + "").setScore(3);

    exp = board.registerNewTeam("Exp");
    exp.setPrefix(ChatColor.YELLOW + "Exp: ");
    exp.setSuffix(ChatColor.WHITE + "" + fighter.getExp() + "/"
      + Experience.getExpNeeded(fighter.getPlayerLevel()));
    exp.addEntry(ChatColor.DARK_GREEN + "");
    objective.getScore(ChatColor.DARK_GREEN + "").setScore(2);

    Score b4 = objective.getScore("    ");
    b4.setScore(1);
    
    footer = board.registerNewTeam("Foot");
    footer.setPrefix("");
    footer.setSuffix(ChatColor.GOLD + "" + ChatColor.BOLD + "---SEVEN KITS---");
    footer.addEntry(ChatColor.BLACK + "");
    objective.getScore(ChatColor.BLACK + "").setScore(0);
    
    noColliders = board.registerNewTeam(player.getName());
    noColliders.addPlayer(player);
    noColliders.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
    
    this.theBoard = board;
    player.setScoreboard(board);
  }

  public void updateCookies() {
    cookies.setSuffix(ChatColor.WHITE + "$" + fighter.getCakes());
  }

  public void updateKills() {
    kills.setSuffix(ChatColor.WHITE + "" + fighter.getKills() + " / " + fighter.getDeaths());
  }

  public void updateDeaths() {
    kills.setSuffix(ChatColor.WHITE + "" + fighter.getKills() + " / " + fighter.getDeaths());
  }

  public void updateRatio() {
    ratio.setSuffix(ChatColor.WHITE + "" + getRatio());
  }

  public void updateKillstreak() {
    killStreak.setSuffix(ChatColor.WHITE + "" + fighter.getKillStreak());
  }

  public void updateLevel() {
    level.setSuffix(ChatColor.WHITE + "" + fighter.getPlayerLevel());
  }

  public void updateExp() {
    exp.setSuffix(ChatColor.WHITE + "" + fighter.getExp() + "/"
      + Experience.getExpNeeded(fighter.getPlayerLevel()));
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
  }

  public String getRatio() {
    double ratioNum = 0;
    if (fighter.getDeaths() == 0) {
      ratioNum = fighter.getKills();
    } else if (fighter.getKills() == 0) {
      ratioNum = 0;
    } else {
      ratioNum = (double) fighter.getKills() / (double) fighter.getDeaths();
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
  
  public void unhideScoreBoard() {
	  player.setScoreboard(theBoard);
  }
  
  @SuppressWarnings("deprecation")
public void hideScoreBoard() {
//	  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
//		  public void run() {
//		  player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
//		  }
//	  }, 20L);
	  Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
	  noColliders = board.registerNewTeam(player.getName());
	  noColliders.addPlayer(player);
	  noColliders.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
	  player.setScoreboard(board);
  }


}

