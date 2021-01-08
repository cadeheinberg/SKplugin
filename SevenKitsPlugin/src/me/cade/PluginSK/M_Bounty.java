package me.cade.PluginSK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class M_Bounty {

  private static final double bountyTime = 5.0;

  private static Plugin plugin = A_Main.getPlugin(A_Main.class);
  private static BossBar bossBar;
  private static int currentReward;

  private static double bountyTimeLeft;
  private static int countDownTask;
  
  private static Player bountySetOn;
  private static String bountySetter;
  private static boolean bountyOn;

  public static void setUpBountyClass() {
    bountyTimeLeft = bountyTime;
    bossBar =
      Bukkit.createBossBar(ChatColor.RED + "Bounty", BarColor.PURPLE, BarStyle.SOLID);
    currentReward = 0;
    bossBar.setVisible(false);
    bountyOn = false;
  }

  public static boolean setABounty(Player setter, Player player, int reward) {
    if(bountyOn) {
      return false;
    }
    Vars.getFighter(setter).decCakes(reward);
    bountySetter = setter.getName();
    bountyTimeLeft = bountyTime;
    currentReward = reward;
    player.sendTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + setter.getName() + " set a bounty on you!",
      ChatColor.GOLD + "" + ChatColor.BOLD + "Survive for " + bountyTime + " minutes!", 20, 50, 10);
    bountySetOn = player;
    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 8, 1);
    player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
    player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
    player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
    player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS, 1));
    if(Zgen.safeZone(player.getLocation())) {
      player.teleport(A_Main.fightArea);
    }
    K_Borders.checkBountyBorders(player);
    startBountyCountDown();
    return true;
  }

  private static void startBountyCountDown() {
    bossBar.setVisible(true);
    setBountyOn(true);
    for(Player player : Bukkit.getOnlinePlayers()) {
      bossBar.addPlayer(player);
    }
    countDownTask = new BukkitRunnable() {
      @Override
      public void run() {
        bountyTimeLeft = bountyTimeLeft - 0.1;
        if (bountyTimeLeft <= 0.02) {
          doSurvivorReward();
          cancelBountyOnPlayer();
          return;
        }
        String time = ("" + bountyTimeLeft);
        if(time.length() > 3) {
          time = time.substring(0, 3);
        }
        bossBar.setTitle(ChatColor.RED + "" + ChatColor.BOLD + "Bounty: " + ChatColor.WHITE
          + bountySetOn.getName() + ChatColor.RED + " || " + ChatColor.WHITE + "$" + currentReward
          + ChatColor.RED + " || " + ChatColor.WHITE + time + " min");
      }
    }.runTaskTimer(plugin, 0L, 120L).getTaskId();
  }
  
  public static void cancelBountyOnPlayer() {
    Vars.getFighter(bountySetOn).giveKit(true);
    bountySetOn = null;
    setBountyOn(false);
    bossBar.setVisible(false);
    Bukkit.getScheduler().cancelTask(countDownTask);
    K_Borders.cancelBountyBorderTask();
  }

  public static void tellAllBountyOverNoWinner() {
    for(Player player : Bukkit.getOnlinePlayers()) {
      player.sendTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Bounty over, no winner",
        ChatColor.GOLD + "" + ChatColor.BOLD + "Set another with /bountyset PlayerName Reward", 20, 60, 10);
    }
    if(Bukkit.getPlayer(bountySetter) != null) {
      if(!Bukkit.getPlayer(bountySetter).isOnline()) {
        Vars.getFighter(Bukkit.getPlayer(bountySetter)).incCakes(currentReward);
      }
    }
    M_Bounty.cancelBountyOnPlayer();
  }
  
  private static void doSurvivorReward() {
    Vars.getFighter(bountySetOn).incCakes(currentReward);
    for(Player player : Bukkit.getOnlinePlayers()) {
      player.sendTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + bountySetOn.getName() + " has survived the Bounty",
        ChatColor.GOLD + "" + ChatColor.BOLD + "Set another with /bountyset PlayerName Reward", 20, 60, 10);
    }
    M_Bounty.cancelBountyOnPlayer();
  }
  
  public static int doKillerReward(Player killer) {
    for(Player player : Bukkit.getOnlinePlayers()) {
      player.sendTitle(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + killer.getName() + " has won the bounty",
        ChatColor.GOLD + "" + ChatColor.BOLD + "Set another with /bountyset PlayerName Reward", 20, 60, 10);
    }
    cancelBountyOnPlayer();
    return currentReward;
  }
  
  public static int doBountyCheckOnDeath(Player victim, E1_Fighter fVictim) {
    if (M_Bounty.isBountyOn() && victim.equals(M_Bounty.getBountySetOn())) {
      Player killer;
      killer = fVictim.getLastDamagedBy();
      if (killer != null) {
        if (!killer.isOnline()) {
          M_Bounty.tellAllBountyOverNoWinner();
          return 0;
        }
        else if (!(killer.equals(victim))) {
          return M_Bounty.doKillerReward(killer);
        } else {
          M_Bounty.tellAllBountyOverNoWinner();
        }
      } else {
        M_Bounty.tellAllBountyOverNoWinner();
      }
    }
    return 0;
  }

  public static boolean isBountyOn() {
    return bountyOn;
  }

  public static void setBountyOn(boolean bountyOn) {
    M_Bounty.bountyOn = bountyOn;
  }
  
  public static Player getBountySetOn() {
    return bountySetOn;
  }
  
  public static BossBar getBossBar() {
    return bossBar;
  }

  public static String getBountySetter() {
    return bountySetter;
  }

  public static void setBountySetter(String bountySetter) {
    M_Bounty.bountySetter = bountySetter;
  }


}
