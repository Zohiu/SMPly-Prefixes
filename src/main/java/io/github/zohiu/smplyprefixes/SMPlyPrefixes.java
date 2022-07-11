package io.github.zohiu.smplyprefixes;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class SMPlyPrefixes extends JavaPlugin {

    public static SMPlyPrefixes instance;

    @Override
    public void onEnable() {
        instance = this;
        this.getCommand("prefix").setExecutor(new PrefixCommand());

        this.getLogger().log(Level.INFO, ChatColor.GREEN + "Enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, ChatColor.RED + "Disabled.");
    }

    public static SMPlyPrefixes getInstance() {
        return instance;
    }
}
