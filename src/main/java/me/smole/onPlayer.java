package me.smole;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

public class onPlayer implements Listener {
    public OpProtect plugin;
    public onPlayer(OpProtect plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin (PlayerJoinEvent e) {
        FileConfiguration config = plugin.getConfig();
        Player player = e.getPlayer();

        if (config.getBoolean("opprotect.check_op_or_permission")) {
            if (player.isOp()) {
                LoginCommand.jp.add(player.getName());
                player.sendMessage(config.getString("opprotect.message.enter_password").replace("&", "§"));
            }
        } else {
            if (player.hasPermission(config.getString("opprotect.check_permission"))) {
                LoginCommand.jp.add(player.getName());
                player.sendMessage(config.getString("opprotect.message.enter_password").replace("&", "§"));
            }
        }
    }

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent e) {
        FileConfiguration config = plugin.getConfig();
        Player player = e.getPlayer();
        if (LoginCommand.jp.contains(player.getName()) && !e.getMessage().startsWith("/password")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(config.getString("opprotect.message.enter_password").replace("&", "§"));
        }
    }

    @EventHandler
    public void onChat (AsyncPlayerChatEvent e) {
        FileConfiguration config = plugin.getConfig();
        if (LoginCommand.jp.contains(e.getPlayer().getName())) {
            e.getPlayer().sendMessage(config.getString("opprotect.message.enter_password").replace("&", "§"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove (PlayerMoveEvent e) {
        FileConfiguration config = plugin.getConfig();
        if (LoginCommand.jp.contains(e.getPlayer().getName())) {
            e.getPlayer().sendMessage(config.getString("opprotect.message.enter_password").replace("&", "§"));
            e.setTo(e.getFrom());
        }
    }

    @EventHandler
    public void onBlockPlace (BlockPlaceEvent e) {
        FileConfiguration config = plugin.getConfig();
        if (LoginCommand.jp.contains(e.getPlayer().getName())) {
            e.getPlayer().sendMessage(config.getString("opprotect.message.enter_password").replace("&", "§"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop (PlayerDropItemEvent e) {
        FileConfiguration config = plugin.getConfig();
        if (LoginCommand.jp.contains(e.getPlayer().getName())) {
            e.getPlayer().sendMessage(config.getString("opprotect.message.enter_password").replace("&", "§"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract (PlayerInteractEvent e) {
        FileConfiguration config = plugin.getConfig();
        if (LoginCommand.jp.contains(e.getPlayer().getName())) {
            e.getPlayer().sendMessage(config.getString("opprotect.message.enter_password").replace("&", "§"));
            e.setCancelled(true);
        }
    }

}
