package com.puddingkc.commands;

import com.puddingkc.FishAnything;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class ReloadConfig implements CommandExecutor {

    private final FishAnything plugin;
    public ReloadConfig(FishAnything plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("fishanything.reload")) {
            plugin.loadConfig();
            sender.sendMessage(ChatColor.GREEN + "配置文件重载完成");
            return true;
        }

        return false;
    }
}
