package me.smole;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class OpProtect extends JavaPlugin {

    @Override
    public void onEnable() {
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();

            Bukkit.getServer().getConsoleSender().sendMessage("config.yml has been created!");
        }

        Bukkit.getPluginManager().registerEvents(new onPlayer(this), this);

        getCommand("password").setExecutor(new LoginCommand(this));
        getCommand("opprotect").setExecutor(new MainCMD(this));

        getLogger().info("by Nesmole is enabled!");
    }

    @Override
    public void onDisable() {

        getLogger().info("by Nesmole is disabled!");

    }
}
