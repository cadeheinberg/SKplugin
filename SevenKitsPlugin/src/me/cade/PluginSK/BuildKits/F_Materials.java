package me.cade.PluginSK.BuildKits;

import java.util.ArrayList;
import org.bukkit.Material;

public class F_Materials {

  private static ArrayList <Material> materialList;
  
  public static void makeMaterials() {
    materialList = new ArrayList <Material>();
    materialList.add(Material.IRON_SWORD);
    materialList.add(Material.IRON_AXE);
    materialList.add(Material.IRON_SHOVEL);
    materialList.add(Material.BOW);
    materialList.add(Material.TRIDENT);
    materialList.add(Material.BAMBOO);
    materialList.add(Material.NETHERITE_SWORD);
  }

  public static Material getMaterial(int index) {
    return materialList.get(index);
  }
  
}
