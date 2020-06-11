package me.smole;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginCommand implements CommandExecutor {
    private static OpProtect plugin;
    public LoginCommand (OpProtect plugin) {
        LoginCommand.plugin = plugin;
    }
    public static HashMap<Player, Integer> limit = new HashMap<>();
    public static ArrayList<String> jp = new ArrayList<>();
    public static ArrayList<String> auth = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();
        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(config.getString("opprotect.message.error_usage").replace("&", "§"));

        } else {
            if (auth.contains(player.getName())) {
                sender.sendMessage(config.getString("opprotect.message.already_login").replace("&", "§"));
            } else {
                if (args[0].equals(config.getString("opprotect.password"))) {
                    player.sendMessage(config.getString("opprotect.message.successful_login").replace("&", "§"));
                    limit.put(player, 0);
                    jp.remove(player.getName());
                    auth.add(player.getName());
                } else {
                    if (limit.containsKey(player)) {
                        limit.put(player, limit.get(player) + 1);
                    } else {
                        limit.put(player, 1);
                    }

                    if (limit.get(player) == config.getInt("opprotect.attempt_limit")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString("opprotect.punish").replace("&", "§").replace("{player}", player.getName()));
                        limit.put(player, 0);

                        return true;
                    }

                    player.sendMessage(config.getString("opprotect.message.wrong_password").replace("&", "§"));

                    return true;
                }

                return true;
            }

            return true;
        }

        return true;
    }

}
