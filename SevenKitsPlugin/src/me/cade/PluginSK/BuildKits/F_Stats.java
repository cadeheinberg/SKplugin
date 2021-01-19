package me.cade.PluginSK.BuildKits;

import java.util.ArrayList;

public class F_Stats {

  private static ArrayList<Integer[]> damageList;

  private static ArrayList<Integer[]> ticksList;

  private static ArrayList<Double[]> projectileDamageList;

  public static void makeStats() {
    damageList = new ArrayList<Integer[]>();
    ticksList = new ArrayList<Integer[]>();
    projectileDamageList = new ArrayList<Double[]>();

    // noob
    // noob
    // noob
    damageList.add(new Integer[] {4, 5, 5, 6});
    ticksList.add(null);
    projectileDamageList.add(null);

    // booster
    // booster
    // booster
    damageList.add(new Integer[] {4, 5, 5, 6});
    ticksList.add(null);
    projectileDamageList.add(null);

    // shotty
    // shotty
    // shotty
    damageList.add(new Integer[] {4, 4, 4, 5});
    ticksList.add(new Integer[] {38, 34, 30, 68});
    projectileDamageList.add(new Double[] {0.5, 0.75, 1.0, 1.0});

    // goblin
    // goblin
    // goblin
    damageList.add(new Integer[] {4, 5, 6, 6});
    ticksList.add(new Integer[] {38, 34, 30, 68});
    projectileDamageList.add(new Double[] {1.0, 1.0, 1.25, 1.25});

    // igor
    // igor
    // igor
    damageList.add(new Integer[] {5, 5, 6, 6});
    ticksList.add(new Integer[] {30, 30, 25, 20});
    projectileDamageList.add(new Double[] {5.0, 6.0, 7.0, 4.0});

    // heavy
    // heavy
    // heavy
    damageList.add(new Integer[] {5, 5, 6, 6});
    ticksList.add(new Integer[] {5, 5, 5, 5});
    projectileDamageList.add(new Double[] {1.0, 1.5, 1.5, 2.0});

    // wizard
    // wizard
    // wizard
    damageList.add(new Integer[] {4, 5, 5, 6});
    ticksList.add(null);
    projectileDamageList.add(null);
  }

  public static Integer[] getDamageList(int index) {
    return damageList.get(index);
  }

  public static Integer[] getTicksList(int index) {
    return ticksList.get(index);
  }

  public static Double[] getProjectileDamageList(int index) {
    return projectileDamageList.get(index);
  }

}
