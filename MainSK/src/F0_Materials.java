package me.cadeheinberg.SevenKitsPlugin;

import java.util.ArrayList;
import org.bukkit.Material;

public class F0_Materials {

  private static ArrayList <Material[]> materialList;
  
  public static void makeMaterials() {
    materialList = new ArrayList <Material[]>();
    materialList.add(new Material[] {Material.GOLDEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD,
      Material.DIAMOND_SWORD});
    materialList.add(new Material[] {Material.GOLDEN_AXE, Material.STONE_AXE, Material.IRON_AXE,
      Material.DIAMOND_AXE});
    materialList.add(new Material[] {Material.GOLDEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL,
      Material.DIAMOND_SHOVEL});
    materialList.add(new Material[] {Material.BOW, Material.BOW, Material.BOW,
      Material.BOW});
    materialList.add(new Material[] {Material.TRIDENT, Material.TRIDENT, Material.TRIDENT,
      Material.TRIDENT});
    materialList.add(new Material[] {Material.GOLDEN_HOE, Material.STONE_HOE, Material.IRON_HOE,
      Material.DIAMOND_HOE});
    materialList.add(new Material[] {Material.BLAZE_ROD, Material.BLAZE_ROD, Material.BLAZE_ROD,
      Material.BLAZE_ROD});
  }

  public static Material[] getMaterialList(int number) {
    return materialList.get(number);
  }
  
  public static Material getMaterialFromKitIDAndIndex(int ID, int index) {
    return materialList.get(ID)[index];
  }
  
}
