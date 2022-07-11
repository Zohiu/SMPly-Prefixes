package io.github.zohiu.smplyprefixes;

import org.bukkit.plugin.java.JavaPlugin;

public final class SMPlyPrefixes extends JavaPlugin {

    public static SMPlyPrefixes instance;

    @Override
    public void onEnable() {
        instance = this;
        this.getCommand("prefix").setExecutor(new PrefixCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static SMPlyPrefixes getInstance() {
        return instance;
    }
}
