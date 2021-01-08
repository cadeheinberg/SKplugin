package me.cade.PluginSK.kitbuilders;

import java.util.ArrayList;

public class F0_Prices {
  
  private static ArrayList <Integer[]> priceList;
  
  public static void makePrices() {
    priceList = new ArrayList <Integer[]>();
    priceList.add(new Integer[] { 0, 5000, 8000, 12000 });
    priceList.add(new Integer[] {0, 5000, 8000, 12000 });
    priceList.add(new Integer[] {5000, 8000, 12000, 15000 });
    priceList.add(new Integer[] {5000, 8000, 12000, 15000 });
    priceList.add(new Integer[] {7000, 9000, 15000, 20000 });
    priceList.add(new Integer[] {7000, 9000, 15000, 20000 });
    priceList.add(new Integer[] {25000, 10000, 10000, 10000});
  }

  public static Integer[] getPriceList(int number) {
    return priceList.get(number);
  }


}
