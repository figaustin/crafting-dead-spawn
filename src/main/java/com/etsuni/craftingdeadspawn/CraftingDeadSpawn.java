package com.etsuni.craftingdeadspawn;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CraftingDeadSpawn extends JavaPlugin {

    private File customConfigFile;
    private FileConfiguration customConfig;

    protected static CraftingDeadSpawn plugin;

    @Override
    public void onEnable() {
        plugin = this;

        createSpawnConfig();
        this.getCommand("cdsetspawn").setExecutor(new Teleport());
        this.getCommand("randomspawn").setExecutor(new Teleport());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    private void createSpawnConfig() {
        customConfigFile = new File(getDataFolder(), "spawns.yml");
        if(!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("spawns.yml", false);
        }

        customConfig = new YamlConfiguration();

        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void saveCfg() {
        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration getSpawnsConfig() {
        return this.customConfig;
    }

}
