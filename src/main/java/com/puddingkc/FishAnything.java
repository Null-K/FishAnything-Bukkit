package com.puddingkc;

import com.puddingkc.commands.ReloadConfig;
import com.puddingkc.listeners.FishingListener;
import com.puddingkc.listeners.GiveItemListener;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FishAnything extends JavaPlugin {

    private final Set<Material> itemBlacklist = new HashSet<>();
    private final Set<EntityType> entityBlacklist = new HashSet<>();
    private String fishingName;
    private Boolean autoGive;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(new FishingListener(this), this);
        getServer().getPluginManager().registerEvents(new GiveItemListener(this), this);
        Objects.requireNonNull(getCommand("fishanything")).setExecutor(new ReloadConfig(this));

        getLogger().info("插件启用成功，作者QQ:3116078709");
        getLogger().info("灵感来源：哔哩哔哩 马夫鱼33");
    }

    public void loadConfig() {
        reloadConfig();

        itemBlacklist.clear();
        entityBlacklist.clear();

        fishingName = getConfig().getString("fishing-rod-name","神奇钓竿");
        autoGive = getConfig().getBoolean("auto-give",true);

        List<String> configList = getConfig().getStringList("item-blacklist");
        for (String name : configList) {
            try {
                Material material = Material.valueOf(name.toUpperCase());
                itemBlacklist.add(material);
            } catch (IllegalArgumentException e) {
                getLogger().warning("物品 '" + name + "' 不存在");
            }
        }

        configList = getConfig().getStringList("entity-blacklist");
        for (String name : configList) {
            try {
                EntityType entityType = EntityType.valueOf(name.toUpperCase());
                entityBlacklist.add(entityType);
            } catch (IllegalArgumentException e) {
                getLogger().warning("实体 '" + name + "' 不存在");
            }
        }
    }

    public Set<Material> getItemBlacklist() {
        return itemBlacklist;
    }

    public Set<EntityType> getEntityBlacklist() {
        return entityBlacklist;
    }

    public String getFishingName() {
        return fishingName;
    }

    public Boolean getAutoGive() {
        return autoGive;
    }

}
