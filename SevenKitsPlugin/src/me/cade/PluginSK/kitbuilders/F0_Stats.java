package me.cade.PluginSK.kitbuilders;

import java.util.ArrayList;

public class F0_Stats {

  private static ArrayList<Integer[]> damageList;

  private static ArrayList<Integer[]> ticksList;

  private static ArrayList<String[]> cooldownList;

  private static ArrayList<Double[]> specialDamageList;

  private static ArrayList<Double[]> powerList;

  private static ArrayList<String[]> stringList;

  private static ArrayList<Integer[]> intList;

  public static void makeStats() {
    damageList = new ArrayList<Integer[]>();
    ticksList = new ArrayList<Integer[]>();
    cooldownList = new ArrayList<String[]>();
    specialDamageList = new ArrayList<Double[]>();
    powerList = new ArrayList<Double[]>();
    stringList = new ArrayList<String[]>();
    intList = new ArrayList<Integer[]>();

    // noob
    // noob
    // noob
    damageList.add(new Integer[] {4, 5, 5, 6});
    ticksList.add(new Integer[] {300, 280, 260, 220});
    cooldownList.add(new String[] {"14s", "13s", "12s", "11s"});
    specialDamageList.add(new Double[] {3.0, 3.0, 4.0, 4.0});
    powerList.add(new Double[] {1.3, 1.3, 1.4, 1.5});

    stringList.add(new String[] {"", "", "", ""});
    intList.add(new Integer[] {0, 0, 0, 0});

    // booster
    // booster
    // booster
    damageList.add(new Integer[] {4, 5, 5, 6});
    ticksList.add(new Integer[] {154, 148, 144, 136});
    cooldownList.add(new String[] {"7.7s", "7.4s", "7.2s", "6.8s"});
    powerList.add(new Double[] {1.4, 1.5, 1.7, 1.8});

    specialDamageList.add(new Double[] {0.0, 0.0, 0.0, 0.0});
    stringList.add(new String[] {"", "", "", ""});
    intList.add(new Integer[] {0, 0, 0, 0});

    // shotty
    // shotty
    // shotty
    damageList.add(new Integer[] {4, 4, 4, 5});
    ticksList.add(new Integer[] {38, 34, 30, 68});
    cooldownList.add(new String[] {"1.9s", "1.7s", "1.5s", "3.4s"});
    specialDamageList.add(new Double[] {0.5, 0.75, 1.0, 1.0});
    powerList.add(new Double[] {-0.6, -0.7, -0.8, -0.7});

    stringList.add(new String[] {"", "", "", ""});
    intList.add(new Integer[] {0, 0, 0, 0});

    // goblin
    // goblin
    // goblin
    damageList.add(new Integer[] {4, 4, 4, 4});
    specialDamageList.add(new Double[] {1.0, 1.0, 1.25, 1.25});
    intList.add(new Integer[] {3, 4, 5, 6});
    
    powerList.add(new Double[] {0.0, 0.0, 0.0, 0.0});
    ticksList.add(new Integer[] {0, 0, 0, 0});
    cooldownList.add(new String[] {"", "", "", ""});
    stringList.add(new String[] {"", "", "", ""});

    // igor
    // igor
    // igor
    damageList.add(new Integer[] {5, 5, 6, 6});
    ticksList.add(new Integer[] {30, 30, 25, 20});
    cooldownList.add(new String[] {"1.5s", "1.5s", "1.25s", "1s"});
    specialDamageList.add(new Double[] {5.0, 6.0, 7.0, 4.0});
    stringList.add(new String[] {"No", "Fire", "Lightning", "Explosive"});
    
    intList.add(new Integer[] {0, 0, 0, 0});
    powerList.add(new Double[] {0.0, 0.0, 0.0, 0.0});

    // heavy
    // heavy
    // heavy
    damageList.add(new Integer[] {5, 5, 6, 6});
    ticksList.add(new Integer[] {200, 280, 160, 150});
    cooldownList.add(new String[] {"10s", "9s", "8s", "7.5s"});
    specialDamageList.add(new Double[] {1.0, 1.5, 1.5, 2.0});
    intList.add(new Integer[] {15, 25, 30, 35});
    
    powerList.add(new Double[] {0.0, 0.0, 0.0, 0.0});
    stringList.add(new String[] {"", "", "", ""});

    // wizard
    // wizard
    // wizard
    damageList.add(new Integer[] {6, 6, 6, 6});
    ticksList.add(new Integer[] {30, 46, 130, 150});
    cooldownList.add(new String[] {"1.5s", "2.3s", "6.5s", "7.5s"});
    specialDamageList.add(new Double[] {6.0, 5.0, 0.0, 4.0});
    
    powerList.add(new Double[] {0.0, 0.0, 0.0, 0.0});
    stringList.add(new String[] {"", "", "", ""});
    intList.add(new Integer[] {0, 0, 0, 0});
  }

  public static Integer[] getDamageList(int index) {
    return damageList.get(index);
  }

  public static Integer[] getTicksList(int index) {
    return ticksList.get(index);
  }

  public static String[] getCooldownList(int index) {
    return cooldownList.get(index);
  }

  public static String[] getStringList(int index) {
    return stringList.get(index);
  }

  public static Double[] getSpecialDamageList(int index) {
    return specialDamageList.get(index);
  }

  public static Double[] getPowerList(int index) {
    return powerList.get(index);
  }

  public static Integer[] getIntList(int index) {
    return intList.get(index);
  }
}
