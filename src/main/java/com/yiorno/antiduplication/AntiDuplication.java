package com.yiorno.antiduplication;

import org.bukkit.plugin.java.JavaPlugin;

public final class AntiDuplication extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new Event(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
