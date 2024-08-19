package com.puddingkc.listeners;

import com.puddingkc.FishAnything;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class GiveItemListener implements Listener {

    private final FishAnything plugin;
    public GiveItemListener(FishAnything plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getAutoGive()) {
            Player player = event.getPlayer();
            checkAndGiveTestFishingRod(player);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (plugin.getAutoGive()) {
            Player player = event.getPlayer();
            checkAndGiveTestFishingRod(player);
        }
    }

    private void checkAndGiveTestFishingRod(Player player) {
        boolean hasFishingRod = false;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.FISHING_ROD) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && plugin.getFishingName().equals(meta.getDisplayName())) {
                    hasFishingRod = true;
                    break;
                }
            }
        }

        if (!hasFishingRod) {
            ItemStack FishingRod = new ItemStack(Material.FISHING_ROD);
            ItemMeta meta = FishingRod.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(plugin.getFishingName());
                FishingRod.setItemMeta(meta);
            }
            player.getInventory().addItem(FishingRod);
            player.sendMessage("你获得了一把 '" + plugin.getFishingName() + "' 钓鱼竿");
        }
    }

}
