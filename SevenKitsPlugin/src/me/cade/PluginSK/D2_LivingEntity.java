package me.cade.PluginSK;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class D2_LivingEntity {

  private String name;
  private Location location;
  private EntityType type;
  private LivingEntity entity;
  
  public D2_LivingEntity(EntityType type, String name, Location location, int yaw) {
    this.name = name;
    this.location = location;
    this.location.setYaw(yaw);
    this.type = type;
    this.entity = (LivingEntity) A_Main.kitpvp.spawnEntity(location, type);
    this.entity.setSilent(true);
    this.entity.setAI(false);
    this.entity.setCustomName(name);
    this.entity.setCustomNameVisible(true);
    this.entity.setRemoveWhenFarAway(false);
  }
  
  public String getName() {
    return this.name;
  }
  
  public EntityType getType() {
    return this.type;
  }
  
  public LivingEntity getEntity() {
    return entity;
  }
  
}
