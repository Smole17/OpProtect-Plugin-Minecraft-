package me.smole;

import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

import java.io.File;

public class MainCMD implements CommandExecutor {
    private static OpProtect plugin;
    public MainCMD (OpProtect plugin) {
        MainCMD.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"CLICK ME\",\"bold\":true,\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://vk.com/smole17\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"\",{\"text\":\"CLICK CLICK CLICK\",\"color\":\"gray\"}]}}");
        PacketPlayOutChat chat = new PacketPlayOutChat(comp);
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(chat);

        if (player.hasPermission("opprotect.cmd")) {
            if (args.length != 1) {
                player.sendMessage("§cOp§fProtect §71.0");
                player.sendMessage("§fAuthor:");
                connection.sendPacket(chat);
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                File config = new File(plugin.getDataFolder() + File.separator + "config.yml");
                if (!config.exists()) {
                    plugin.getConfig().options().copyDefaults(true);
                    plugin.saveDefaultConfig();

                    Bukkit.getServer().getConsoleSender().sendMessage("config.yml has been created!");
                }

                plugin.reloadConfig();
                player.sendMessage(plugin.getConfig().getString("opprotect.message.reloaded").replace("&", "§"));
            }

            return true;
        }

        return true;
    }
}
