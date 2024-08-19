package com.puddingkc.listeners;

import com.puddingkc.FishAnything;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class FishingListener implements Listener {

    private final FishAnything plugin;
    private final Random random = new Random();

    public FishingListener(FishAnything plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();

        if (event.getState() == PlayerFishEvent.State.REEL_IN) {
            if (random.nextBoolean()) {
                Material randomItem = Material.values()[random.nextInt(Material.values().length)];
                if (!isItemBlacklisted(randomItem)) {
                    ItemStack itemStack = new ItemStack(randomItem);
                    Item droppedItem = player.getWorld().dropItem(event.getHook().getLocation(), itemStack);

                    droppedItem.setPickupDelay(0);
                    Vector playerLocation = player.getLocation().toVector();
                    Vector itemLocation = droppedItem.getLocation().toVector();
                    applyVelocityWithParabola(itemLocation, playerLocation, droppedItem);

                    event.getHook().setHookedEntity(droppedItem);
                }
            } else {
                EntityType randomEntity = EntityType.values()[random.nextInt(EntityType.values().length)];
                if (!isEntityBlacklisted(randomEntity)) {
                    try {
                        Entity entity = player.getWorld().spawnEntity(event.getHook().getLocation(), randomEntity);

                        Vector playerLocation = player.getLocation().toVector();
                        Vector entityLocation = entity.getLocation().toVector();
                        applyVelocityWithParabola(entityLocation, playerLocation, entity);

                        event.getHook().setHookedEntity(entity);
                    } catch (Exception ignored) {

                    }
                }
            }

            if (event.getCaught() != null) {
                event.getCaught().remove();
            }
        }
    }

    private void applyVelocityWithParabola(Vector origin, Vector target, Entity entity) {
        Vector direction = target.subtract(origin);
        double distance = Math.sqrt(Math.pow(direction.getX(), 2) + Math.pow(direction.getZ(), 2));
        double yBoost = Math.min(distance * 0.55, 3.0);
        double speedMultiplier = Math.min(distance * 0.11, 2.5);
        direction.setY(yBoost);
        entity.setVelocity(direction.normalize().multiply(speedMultiplier));
    }

    private boolean isItemBlacklisted(Material item) {
        return plugin.getItemBlacklist().contains(item);
    }

    private boolean isEntityBlacklisted(EntityType entity) {
        return plugin.getEntityBlacklist().contains(entity);
    }

}
