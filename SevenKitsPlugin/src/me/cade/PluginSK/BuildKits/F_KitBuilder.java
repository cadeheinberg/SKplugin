package me.cade.PluginSK.BuildKits;

public class F_KitBuilder {
  
  public static void buildAllKits() {
    F_Materials.makeMaterials();
    F_Stats.makeStats();
    F0_Noob.makeKit();
    F1_Booster.makeKit();
    F2_Shotty.makeKit();
    F3_Goblin.makeKit();
    F4_Igor.makeKit();
    F5_Heavy.makeKit();
    F6_Zero.makeKit();
  }

}
