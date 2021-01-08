package me.cadeheinberg.SevenKitsPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import me.cadeheinberg.SevenKitsPlugin.cakes.C_CakeSpawner;

public class A_Main extends JavaPlugin {

  public Plugin plugin;

  public static World kitpvp;
  public static World sandbox;
  public static World sandboxNether;
  public static World among;

  public static Location spawn;
  public static Location sandBoxSpawn;
  public static Location fightArea;
  public static Location mineSpawn;
  public static Location soccerSpawn;
  public static Location ballSpawn;
  public static Location amongSpawn;

  public static LivingEntity ballStand;

  public static Scoreboard noCollision;

  @Override
  public void onEnable() {
    this.plugin = this;
    loadConfig();
    setLocations();
    C_CakeSpawner.setUpCakeAreas();
    B_Database.startConnection();
    F0_Materials.makeMaterials();
    F0_Prices.makePrices();
    F0_Stats.makeStats();
    F1_Noob.makeNoob();
    F2_Booster.makeBooster();
    F3_Shotty.makeShotty();
    F4_Goblin.makeGoblin();
    F5_Igor.makeIgor();
    F6_Heavy.makeHeavy();
    F7_Wizard.makeWizard();
    I1_Parachute.makeParachute();
    I2_TNT.makeTNT();
    I3_PearlGun.makeGun();
    I4_TruthSword.makeSword();
    D3_Menus.makeShopMenus();
    D3_Menus.makeCosmeticMenu();
    J_BuilderMode.makeBuilderItems();
    Vars.createVars();
    E2_Experience.makeExpNeeded();
    E3_Defaults.makeDefaults();
    C_CakeSpawner.spawnCakes();
    K_Borders.startCheckingBorders();
    L_CarePackages.setUpCarePackageClass();
    M_Bounty.setUpBountyClass();
    D_NpcSpawner.removeAllNpcs();
    D_NpcSpawner.spawnAllNpcs();
    H1_CombatTracker.makeTracker();
    J0_FIghterMode.setUpClass();
    J1_SoccerMode.setUpClass();
    spawnSoccerBall(soccerSpawn);
    J1_SoccerMode.checkSoccerGoals();
    registerListeners();
    J2_RoyaleMode.setUpClass();
    Z_LeaderBoard.getSigns();
    Z_LeaderBoard.updateSignsRunnable();
  }

  private void registerListeners() {
    Bukkit.getServer().getPluginManager().registerEvents(new E_PlayerJoin(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new D4_NpcListener(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new G_KitListener(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new X_InventoryClick(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new Z_PlayerChat(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new I_ItemListener(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new H_EntityDamage(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new Z_PermissionBuilding(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new L_CarePackages(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new J_BuilderMode(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new Z_SignClickListener(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new J1_SoccerMode(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new N_BookFormatting(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new D5_KitClosers(), this);
  }

  private void loadConfig() {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }

  @Override
  public void onDisable() {
    if (M_Bounty.isBountyOn()) {
      M_Bounty.cancelBountyOnPlayer();
    }
    for (Player player : Bukkit.getOnlinePlayers()) {
      Vars.getFighter(player).fighterQuitServer();
      E_PlayerJoin.placeInventoryBack(player, Vars.getFighter(player));
      player.kickPlayer(ChatColor.BLUE + "Server Reload, Rejoin in" + ChatColor.WHITE + " 5-10 "
        + ChatColor.BLUE + "Seconds");
    }
    B_Database.closeConnection();
    Vars.clearFighters();
    C_CakeSpawner.shutDownCakes();
    L_CarePackages.closeDownCarePackageClass();
  }

  public void setLocations() {
    Bukkit.getServer().createWorld(new WorldCreator("kitpvp"));
    kitpvp = Bukkit.getServer().getWorld("kitpvp");
    Bukkit.getServer().createWorld(new WorldCreator("hungerworld"));
    among = Bukkit.getServer().getWorld("hungerworld");
    spawn = new Location(kitpvp, -1052.5, 197.5, -131.5);
    amongSpawn = new Location(among, 0, 116.5, 0);
    sandbox = Bukkit.getServer().getWorld("world");
    sandboxNether = Bukkit.getServer().getWorld("world_nether");
    sandBoxSpawn = new Location(sandbox, -1054, 85.5, -133);
    fightArea = new Location(kitpvp, -1052.5, 66.5, -131.5);
    mineSpawn = new Location(kitpvp, -1109.8, 196.5, -416.8);
    soccerSpawn = new Location(kitpvp, -1010.5, 194.5, -112.5);
    soccerSpawn.setYaw(90);
    ballSpawn = new Location(kitpvp, -1010.5, 209.5, -112.5);
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      if (label.equals("pvotedinccakes")) {
        if (Bukkit.getPlayer(args[0]) != null) {
          Player player = Bukkit.getPlayer(args[0]);
          Vars.getFighter(player).incCakes(175);
          player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Thanks for votng!");
          player.sendMessage(ChatColor.GREEN + "175 " + C_CakeSpawner.getCakeName()
            + " has been added to your balance");
        } else {
          B_Database.incOfflineStat(args[0], B_Database.column[8], 175);
        }
      }
      return false;
    }
    Player player = (Player) sender;
    if (label.equals("spawn")) {
      if (player.getWorld() == A_Main.kitpvp) {
        if (player.getCooldown(H1_CombatTracker.getTrackerMaterial()) > 0) {
          player.sendMessage(ChatColor.RED + "Can't" + ChatColor.AQUA + "" + ChatColor.BOLD
            + " /spawn" + ChatColor.RED + ". You have a" + ChatColor.AQUA + "" + ChatColor.BOLD
            + " PVP Cooldown " + ChatColor.RED + "on");
          return false;
        }
        if (M_Bounty.isBountyOn()) {
          if (player.equals(M_Bounty.getBountySetOn())) {
            player.sendMessage(ChatColor.RED + "Can't" + ChatColor.AQUA + "" + ChatColor.BOLD
              + " /spawn" + ChatColor.RED + ". You have a" + ChatColor.AQUA + "" + ChatColor.BOLD
              + " Bounty " + ChatColor.RED + "on");
            return false;
          }
        }
        player.teleport(spawn);
        E1_Fighter pFight = Vars.getFighter(player);
        pFight.wentToSpawn();
        return true;
      } else if (player.getWorld() == A_Main.sandbox) {
        player.teleport(A_Main.sandBoxSpawn);
        return true;
      } else if (player.getWorld() == A_Main.sandboxNether) {
        player.sendMessage(ChatColor.RED + "Can't use the " + ChatColor.AQUA + "" + ChatColor.BOLD
          + " /spawn " + ChatColor.RED + "command in the Nether");
        return true;
      }
    } else if (label.equals("hub")) {
      if (player.getWorld() == A_Main.kitpvp) {
        if (player.getCooldown(H1_CombatTracker.getTrackerMaterial()) > 0) {
          player.sendMessage(ChatColor.RED + "Can't" + ChatColor.AQUA + "" + ChatColor.BOLD
            + " /hub" + ChatColor.RED + ". You have a" + ChatColor.AQUA + "" + ChatColor.BOLD
            + " PVP Cooldown " + ChatColor.RED + "on");
          return false;
        }
        player.teleport(spawn);
        E1_Fighter pFight = Vars.getFighter(player);
        pFight.wentToSpawn();
        return true;
      } else if (player.getWorld() == A_Main.sandbox) {
        player.teleport(A_Main.spawn);
        return true;
      } else if (player.getWorld() == A_Main.sandboxNether) {
        player.sendMessage(ChatColor.RED + "Can't use the " + ChatColor.AQUA + "" + ChatColor.BOLD
          + " /hub " + ChatColor.RED + "command in the Nether");
        return true;
      }
    } else if (label.equals("discord")) {
      player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Discord: ");
      player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "--> " + ChatColor.YELLOW
        + "https://discord.gg/tYwWuNR");
      return true;
    } else if (label.equals("fw")) {
      if (player.hasPermission("seven.vip")) {
        if (Zgen.worldSand(player.getWorld())) {
          player.sendMessage(ChatColor.RED + "You must be in " + ChatColor.AQUA + ""
            + ChatColor.BOLD + " Kit PVP " + ChatColor.RED + "to use this");
          return false;
        }
        if (Zgen.safeZone(player.getLocation())) {
          if (Zgen.soccerZone(player.getLocation())) {
            player.sendMessage(ChatColor.RED + "You must be in " + ChatColor.AQUA + ""
              + ChatColor.BOLD + " Spawn Area " + ChatColor.RED + "to use this");
            return false;
          }
          Firework firework = kitpvp.spawn(player.getLocation(), Firework.class);
          FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();
          data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.YELLOW)
            .with(Type.BALL_LARGE).withFlicker().build());
          data.setPower(1);
          firework.setFireworkMeta(data);
        } else {
          player.sendMessage(ChatColor.RED + "You must be in " + ChatColor.AQUA + ""
            + ChatColor.BOLD + " Spawn Area " + ChatColor.RED + "to use this");
        }
      } else {
        player.sendMessage(ChatColor.RED + "You are not a" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " VIP " + ChatColor.RED + "member");
      }
    } else if (label.equals("shout")) {
      if (player.hasPermission("seven.vip")) {
        // can tell message to every player on the server
      } else {
        player.sendMessage(ChatColor.RED + "You are not a" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " VIP " + ChatColor.RED + "member");
      }
    } else if (label.equals("paymoney")) {
      Player giveCake = Bukkit.getPlayer(args[0]);
      int cakeAmount = Integer.parseInt(args[1].toString());
      if (cakeAmount < 1) {
        player.sendMessage(ChatColor.RED + "Payments must be at least " + ChatColor.GREEN + ""
          + ChatColor.BOLD + "1 dollar " + ChatColor.RED + "for that!");
        return false;
      }
      if (Vars.getFighter(player).getCakes() < cakeAmount) {
        player.sendMessage(ChatColor.RED + "You do not have enough " + ChatColor.GREEN + ""
          + ChatColor.BOLD + " Money " + ChatColor.RED + "for that!");
        return false;
      } else {
        Vars.getFighter(player).decCakes(cakeAmount);
        Vars.getFighter(giveCake).incCakes(cakeAmount);
      }
    } else if (label.equals("paycakes")) {
      Player giveTokens = Bukkit.getPlayer(args[0]);
      int tokensAmount = Integer.parseInt(args[1].toString());
      if (tokensAmount < 1) {
        player.sendMessage(ChatColor.RED + "Payments must be at least " + ChatColor.GREEN + ""
          + ChatColor.BOLD + "1 Cake " + ChatColor.RED + "for that!");
        return false;
      }
      if (Vars.getFighter(player).getTokens() < tokensAmount) {
        player.sendMessage(ChatColor.RED + "You do not have enough " + ChatColor.GREEN + ""
          + ChatColor.BOLD + " Cakes " + ChatColor.RED + "for that!");
        return false;
      } else {
        Vars.getFighter(player).decTokens(tokensAmount);
        Vars.getFighter(giveTokens).incTokens(tokensAmount);
      }
    } else if (label.equals("buycakes")) {
      int tokensAmount = Integer.parseInt(args[0].toString());
      Vars.getFighter(player).buyTokens(tokensAmount);
    }else if (label.equals("sellcakes")) {
      int tokensAmount = Integer.parseInt(args[0].toString());
      Vars.getFighter(player).sellTokens(tokensAmount);
    } else if (label.equals("bountyset")) {
      if (!(player.hasPermission("seven.vip"))) {
        player.sendMessage(ChatColor.RED + "You are not a" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " VIP " + ChatColor.RED + "member");
        return false;
      }
      Player setBounty = Bukkit.getPlayer(args[0]);
      if (!Vars.getFighter(setBounty).isFightMode()) {
        player.sendMessage(ChatColor.RED + "Player is not in" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " Fight Mode" + ChatColor.RED + ". Can't set Bounty");
        return false;
      }
      int reward = Integer.parseInt(args[1]);
      if (reward < 50) {
        player
          .sendMessage(ChatColor.RED + "Bounty must be at least 50 " + C_CakeSpawner.getCakeName());
        return false;
      }
      if (Vars.getFighter(player).getCakes() < reward) {
        player.sendMessage(ChatColor.RED + "You do not have enough money!");
        return false;
      }
      if (!(M_Bounty.setABounty(player, setBounty, reward))) {
        player.sendMessage(ChatColor.RED + "Please wait for current Bounty to end!");
        return false;
      }
    } else if (label.equals("addexp")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      int cakeAmount = Integer.parseInt(args[1].toString());
      Vars.getFighter(giveCake).incExp(cakeAmount);
    } else if (label.equals("whataremycommands")) {
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "------Player Command List------");
        player.sendMessage(ChatColor.YELLOW + "   /spawn" + ChatColor.WHITE + " - takes you to world spawn");
        player.sendMessage(ChatColor.YELLOW + "   /hub" + ChatColor.WHITE + " - takes you to kitpvp spawn");
        player.sendMessage(ChatColor.YELLOW + "   /buycakes [amount]" + ChatColor.WHITE + " - buys cake amount at cash value");
        player.sendMessage(ChatColor.YELLOW + "   /sellcakes [amount]" + ChatColor.WHITE + " - sells cake amount at cash value");
        player.sendMessage(ChatColor.YELLOW + "   /paycakes [name] [amount]" + ChatColor.WHITE + " - pay another player in cakes");
        player.sendMessage(ChatColor.YELLOW + "   /paymoney [name] [amount]" + ChatColor.WHITE + " - pay another player in cash");
        player.sendMessage(ChatColor.AQUA + "   /fw" + ChatColor.WHITE + " - vip, launch fireworks");
        player.sendMessage(ChatColor.AQUA + "   /bountyset [name] [amount]" + ChatColor.WHITE + " - vip, set cash bounties");
        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "------End of Command List------");
    } else if (label.equals("addakill")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      Vars.getFighter(giveCake).incKills();
      return true;
    } else if (label.equals("addadeath")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      int cakeAmount = Integer.parseInt(args[1].toString());
      Vars.getFighter(giveCake).setDeaths(cakeAmount);
      Vars.getFighter(giveCake).updateWholeScoreboard();
      return true;
    } else if (label.equals("setcake")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      int cakeAmount = Integer.parseInt(args[1].toString());
      Vars.getFighter(giveCake).setCakes(cakeAmount);
      Vars.getFighter(giveCake).getScoreBoardObject().updateCookies();
    } else if (label.equals("justspawnallnpcs")) {
      if (!player.isOp()) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      D_NpcSpawner.spawnAllNpcs();
      spawnSoccerBall(ballSpawn);
      return true;
    } else if (label.equals("justdespawnallnpcs")) {
      if (!player.isOp()) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      D_NpcSpawner.removeAllNpcs();
      return true;
    } else if (label.equals("givekit")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      int kitID = Integer.parseInt(args[1].toString());
      int kitIndex = Integer.parseInt(args[2].toString());
      E1_Fighter pFight = Vars.getFighter(giveCake);
      pFight.giveKit(kitID, kitIndex, true);
    } else if (label.equals("givekittoall")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      int kitID = Integer.parseInt(args[0].toString());
      int kitIndex = Integer.parseInt(args[1].toString());
      for (Player giveTo : Bukkit.getOnlinePlayers()) {
        if (player.equals(giveTo)) {
          continue;
        }
        Vars.getFighter(giveTo).giveKit(kitID, kitIndex, true);
      }
    } else if (label.equals("resetplayer")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      E1_Fighter pFight = Vars.getFighter(giveCake);
      pFight.setDefaults();
      pFight.giveKit(true);
      pFight.uploadFighter();
      pFight.getScoreBoardObject().updateAll();
    } else if (label.equals("resetplayerbutkd")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      Player giveCake = Bukkit.getPlayer(args[0]);
      E1_Fighter pFight = Vars.getFighter(giveCake);
      pFight.setDefaultsKeepKD();
      pFight.giveKit(true);
      pFight.uploadFighter();
      pFight.getScoreBoardObject().updateAll();
    } else if (label.equals("resetallplayers")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      int key = Integer.parseInt(args[0].toString());
      if (key != 12894253) {
        player.sendMessage(ChatColor.RED + "Incorrect" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " passcode" + ChatColor.RED + ". Try again or check code");
        return false;
      }
      B_Database.resetAllPlayers();
      for (Player resetP : Bukkit.getOnlinePlayers()) {
        Vars.getFighter(resetP).downloadFighter();
        Vars.getFighter(resetP).updateWholeScoreboard();;
      }
      return true;
    } else if (label.equals("resetallkillsdeaths")) {
      if (!(player.isOp())) {
        player.sendMessage(ChatColor.RED + "You are not an" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " operator " + ChatColor.RED + "on this server");
        return false;
      }
      int key = Integer.parseInt(args[0].toString());
      if (key != 12894253) {
        player.sendMessage(ChatColor.RED + "Incorrect" + ChatColor.AQUA + "" + ChatColor.BOLD
          + " passcode" + ChatColor.RED + ". Try again or check code");
        return false;
      }
      B_Database.resetAllPlayersKillsDeaths();
      for (Player resetP : Bukkit.getOnlinePlayers()) {
        Vars.getFighter(resetP).getScoreBoardObject().updateKills();
        Vars.getFighter(resetP).getScoreBoardObject().updateDeaths();
      }
    }
    return true;
  }

  public static void spawnSoccerBall(Location location) {
    Panda theBall = (Panda) A_Main.kitpvp.spawnEntity(location, EntityType.PANDA);
    theBall.setAdult();
    theBall.setMainGene(Panda.Gene.LAZY);
    theBall.setHiddenGene(Panda.Gene.LAZY);
    ballStand = theBall;
    ballStand.setSilent(true);
    ballStand.setAI(true);
    ballStand.setCustomName(ChatColor.BLUE + "" + ChatColor.BOLD + J1_SoccerMode.getBluePoints()
      + ChatColor.WHITE + "" + ChatColor.BOLD + ":" + ChatColor.RED + "" + ChatColor.BOLD
      + J1_SoccerMode.getRedPoints());
    ballStand.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 999999999, 99999999));
    ballStand.setCustomNameVisible(true);
    ballStand.setRemoveWhenFarAway(false);
    for (Entity ent : ballStand.getNearbyEntities(60, 60, 60)) {
      if (ent.getType() != EntityType.PANDA) {
        return;
      }
      if (Zgen.soccerZone(ent.getLocation())) {
        ent.remove();
      }
    }
  }
}
